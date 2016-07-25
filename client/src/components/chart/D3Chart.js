import d3 from 'd3';
import fc from 'd3fc';
import moment from 'moment';
import formatPrice from 'utils/priceFormatter';

const minXDomain = 1000 * 60 * 60 * 12; // 12h
const maxXDomain = 1000 * 60 * 60 * 24 * 365 * 5; // 10yh

class D3Chart {
  constructor(element, pair, dataCallback) {
    this.selection = d3.select(element);
    this.pair = pair;
    this.dataCallback = dataCallback;
  }

  initialise(from, to) {
    this.xScale = fc.scale
      .dateTime()
      .domain([from, to]);

    this.padXDomain();

    this.yScale = d3.scale
      .linear()
      .nice(10);
  }

  constrainXDomain() {
    const { xScale } = this;
    const [from, to] = xScale.domain();
    const maxTo = moment()
      .endOf('day')
      .valueOf();
    if (to > maxTo) {
      xScale.domain([new Date(from - (to - maxTo)), new Date(maxTo)]);
    }
  }

  padXDomain() {
    const { xScale } = this;
    const [from, to] = xScale.domain();
    const pad = (to - from) * 0.05; // 5% pad
    xScale.domain([new Date(from.getTime() - pad), new Date(to.getTime() + pad)]);
  }

  createXDomain(from, to) {
    let fromTime = from.getTime();
    let toTime = to.getTime();
    const maxTo = moment()
      .endOf('day')
      .valueOf();
    if (toTime > maxTo) {
      fromTime = fromTime - (toTime - maxTo);
      toTime = maxTo;
    }
    return [new Date(fromTime), new Date(toTime)];
  }

  update(data) {
    const { priceHistory, currentPrice } = data;

    const yExtent = fc.util
      .extent()
      .fields(['high', 'low'])
      .pad(0.05);

    const yDomain = yExtent.include([currentPrice])(priceHistory);
    this.yScale.domain(yDomain);

    this.selection
      .datum(data);

    this.render();
  }

  render() {
    this.selection
      .call((selection) => this.chart(selection));
  }

  chart(selection) {
    const data = selection.datum();
    const { xScale, yScale, pair } = this;

    const candlestick = fc.series.candlestick()
      .xScale(xScale)
      .yScale(yScale);


    const chartMain = fc.chart.cartesian(xScale, yScale)
      .xTicks(10)
      .yTicks(10)
      .chartLabel(`${pair.id.substr(0, 3)} | ${pair.id.substr(3, 3)}`)
      .margin({ left: 0, right: 70, bottom: 20, top: 0 })
      .yTickFormat((datum) => formatPrice(datum, pair.decimals));

    const closeLine = fc.annotation.line()
      .xScale(xScale)
      .yScale(yScale)
      .orient('horizontal')
      .value(data.currentPrice)
      .label(formatPrice(data.currentPrice, pair.decimals));

    const gridLine = fc.annotation.gridline()
      .xTicks(0);

    const multi = fc.series.multi()
      .series([gridLine, candlestick, closeLine])
      .mapping((series) => (series === candlestick ? data.priceHistory : [data.currentPrice]));

    chartMain.plotArea(multi);

    selection
      .layout({
        flex: 1,
      })
      .call(chartMain);

    const xDomain = xScale.domain();
    const scaleExtentMin = (xDomain[1] - xDomain[0]) / maxXDomain;
    const scaleExtentMax = (xDomain[1] - xDomain[0]) / minXDomain;

    const zoom = d3.behavior.zoom()
      .on('zoom', () => {
        this.zoomed();
      })
      .scaleExtent([scaleExtentMin, scaleExtentMax])
      .x(xScale);

    selection.call(zoom);
  }

  zoomed() {
    this.constrainXDomain();
    const [from, to] = this.xScale.domain();
    this.dataCallback(from, to);
    this.padXDomain();
  }

  destroy() {
    this.selection.select('svg').remove();
  }
}

export default D3Chart;
