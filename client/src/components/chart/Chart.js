import React from 'react';
import './Chart.scss';

const Chart = (props) => (
  <div className="chart">
    chart will go here
    <div>
      {props.pair.id}
    </div>
    <div>
      {props.currentPrice.price}
    </div>
  </div>
);

Chart.propTypes = {
  pair: React.PropTypes.object.isRequired,
  currentPrice: React.PropTypes.object.isRequired,
};

export default Chart;
