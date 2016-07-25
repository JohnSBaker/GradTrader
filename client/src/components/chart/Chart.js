import React, { Component } from 'react';
import debounce from 'debounce';
import 'components/chart/Chart.scss';
import D3Chart from 'components/chart/D3Chart';

const resolutions = [
  1000 * 60 * 60 * 1, // 1h
  1000 * 60 * 60 * 6, // 6h
  1000 * 60 * 60 * 12, // 12h
  1000 * 60 * 60 * 24, // 1d
  1000 * 60 * 60 * 24 * 7, // 7d
  1000 * 60 * 60 * 24 * 14, // 14d
  1000 * 60 * 60 * 24 * 28, // 28d
  1000 * 60 * 60 * 24 * 56, // 56d
  1000 * 60 * 60 * 24 * 365, // 1y
];
const idealCandleSticks = 20;

class Chart extends Component {
  constructor(props) {
    super(props);
    this.requestPriceHistory = debounce(this.requestPriceHistory, 1000);
  }

  componentDidMount() {
    this.requestPriceHistory();
  }

  componentWillReceiveProps(nextProps) {
    if (this.props.pair.id !== nextProps.pair.id ||
      this.props.interval !== nextProps.interval ||
      this.props.resolution !== nextProps.resolution) {
      this.requestPriceHistory(nextProps);
    }
  }

  shouldComponentUpdate(nextProps) {
    const shouldComponentUpdate = this.props.pair.id !== nextProps.pair.id;

    if (!shouldComponentUpdate) {
      if (this.props.priceHistory !== nextProps.priceHistory ||
        this.props.currentPrice !== nextProps.currentPrice) {
        this.updateChart(nextProps);
      }
    }

    return shouldComponentUpdate;
  }

  componentWillUnmount() {
    if (this.d3Chart) {
      this.d3Chart.destroy();
      this.d3Chart = null;
    }
  }

  setChartElement(element) {
    if (this.d3Chart) {
      this.d3Chart.destroy();
      this.d3Chart = null;
    }
    if (element) {
      const { pair, interval } = this.props;
      this.d3Chart = new D3Chart(element, pair, (start, end) => this.handleChartData(start, end));
      this.d3Chart.initialise(new Date(interval.from), new Date(interval.to));
    }
  }

  handleChartData(from, to) {
    const { setInterval, setResolution, pair, resolution } = this.props;
    const fromTime = from.getTime();
    const toTime = to.getTime();

    setInterval(pair.id, fromTime, toTime);

    const idealResolution = (to - from) / idealCandleSticks;
    const closestResolution = resolutions
      .map((targetResolution) => ({
        resolution: targetResolution,
        delta: Math.abs(targetResolution - idealResolution),
      }))
      .sort((a, b) => a.delta - b.delta)[0].resolution;
    if (resolution !== closestResolution) {
      setResolution(pair.id, closestResolution);
    }
  }

  updateChart({ currentPrice, priceHistory } = this.props) {
    if (this.d3Chart && priceHistory) {
      this.d3Chart.update({
        currentPrice,
        priceHistory,
      });
    }
  }

  requestPriceHistory({ pair, resolution, interval } = this.props) {
    if (pair && pair.id) {
      const { requestPriceHistory } = this.props;
      requestPriceHistory(pair.id, resolution, interval.from, interval.to);
    }
  }

  render() {
    const { pair } = this.props;
    if (!pair.id) {
      return <div className="chart-select-pair">Select a pair</div>;
    }

    return (
      <div className="chart" ref={element => this.setChartElement(element)}>
      </div>
    );
  }
}

Chart.propTypes = {
  pair: React.PropTypes.object.isRequired,
  resolution: React.PropTypes.number.isRequired,
  interval: React.PropTypes.object.isRequired,
  currentPrice: React.PropTypes.number,
  priceHistory: React.PropTypes.array,
  requestPriceHistory: React.PropTypes.func.isRequired,
  setResolution: React.PropTypes.func.isRequired,
  setInterval: React.PropTypes.func.isRequired,
};

export default Chart;
