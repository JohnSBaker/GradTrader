import d3 from 'd3';
import fc from 'd3fc';
import formatPrice from 'utils/priceFormatter';

function chart() {
  return (selection) => {
    // initial generation
    const data = selection.datum();
    const xDomain = fc.util.extent().fields(['date']).pad(0.1)(data.historyData);
    const yDomain = fc.util.extent().fields(['high', 'low'])
    .include([data.currentPrice])
    .pad(0.1)(data.historyData);

    const yScale = d3.scale.linear().domain(yDomain);
    const xScale = fc.scale.dateTime().domain(xDomain);

    const candlestick = fc.series.candlestick()
      .xScale(xScale)
      .yScale(yScale);

    const chartMain = fc.chart.cartesian(fc.scale.dateTime(), d3.scale.linear())
    .chartLabel(data.pair.id)
    .xDomain(xDomain)
    .yDomain(yDomain)
    .margin({ left: 0, right: 70, bottom: 20, top: 0 })
    .decorate((s) => {
      s.select('.y-axis').selectAll('text').text(d => formatPrice(d, data.pair.decimals));
    });

    const closeLine = fc.annotation.line()
      .orient('horizontal')
      .value(data.currentPrice)
      .label(formatPrice(data.currentPrice, data.pair.decimals));

    const multi = fc.series.multi()
    .series([candlestick, closeLine])
    .mapping((series) => {
      switch (series) {
        case candlestick:
          return data.historyData;
        case closeLine:
          return [data.currentPrice];
        default:
          return data;
      }
    });
    chartMain.plotArea(multi);

    selection
      .layout({
        flex: 1,
      });

    selection.layout();
    selection.call(chartMain);
  };
}

export const update = (el, data) => {
  d3.select(el).datum(data).call(chart());
};

export const destroy = (el) => {
  d3.select(el).select('svg').remove();
};
