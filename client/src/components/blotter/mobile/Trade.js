import React from 'react';
import moment from 'moment';
import classnames from 'classnames';
import './Trade.scss';

const directions = {
  Buy: 'bought',
  Sell: 'sold',
};

const Trade = ({ trade, showCurrency, showBox }) => {
  const direction = directions[trade.direction];
  const base = trade.pair.substr(0, 3);
  const counter = trade.pair.substr(3, 3);
  const time = moment(trade.timestamp).fromNow();
  const className = classnames('trade', {
    'trade-box': showBox,
  });
  return (
    <div className={className}>
      <div className="trade-first">{direction} {trade.quantity} {base} @ {trade.rate}</div>
      <div className="trade-last">
        {showCurrency && <div className="trade-currency">{base} | {counter}</div>}
        <div className="trade-time">{time}</div>
      </div>
    </div>);
};

Trade.propTypes = {
  trade: React.PropTypes.object.isRequired,
  showCurrency: React.PropTypes.bool,
  showBox: React.PropTypes.bool,
};

Trade.defaultProps = {
  showCurrency: true,
  showBox: true,
};

export default Trade;
