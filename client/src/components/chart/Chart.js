import React, { Component } from 'react';
import { findDOMNode } from 'react-dom';
import 'components/chart/Chart.scss';
import moment from 'moment';
import { update, destroy } from 'components/chart/d3Chart';

class Chart extends Component {

  componentDidMount() {
    if (this.props.historyData) {
      this.updateChart();
    } else if (this.props.pair.id) {
      this.requestTradeHistory();
    }
  }

  componentDidUpdate() {
    if (this.props.historyData) {
      this.updateChart();
    } else if (this.props.pair.id) {
      this.requestTradeHistory();
    }
  }

  componentWillUnmount() {
    const el = findDOMNode(this);
    destroy(el);
  }

  updateChart() {
    const { currentPrice, historyData, pair } = this.props;
    const chartData = {
      currentPrice,
      historyData,
      pair,
    };
    const el = findDOMNode(this);
    update(el, chartData);
  }

  requestTradeHistory() {
    const to = moment().valueOf();
    const from = moment().valueOf() - 1000 * 60 * 60 * 24 * 3;
    const resolution = 1000 * 60 * 60 * 3;
    this.props.requestTradeHistory(this.props.pair.id, resolution, from, to);
  }

  render() {
    if (!this.props.pair.id) {
      return <div className="chart-select-pair">Select a pair</div>;
    }

    if (!this.props.historyData) {
      return <div className="chart-loading">loading</div>;
    }

    return (
      <div className="chart">
      </div>
    );
  }

}

Chart.propTypes = {
  historyData: React.PropTypes.array,
  pair: React.PropTypes.object.isRequired,
  currentPrice: React.PropTypes.number,
  requestTradeHistory: React.PropTypes.func.isRequired,
};

export default Chart;
