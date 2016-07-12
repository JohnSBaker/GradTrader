import React from 'react';
import classnames from 'classnames';
import './PriceTicker.scss';
import NegativeArrowIcon from '../../svg/NegativeArrow.svg';
import PositiveArrowIcon from '../../svg/PositiveArrow.svg';

const PriceTicker = ({ price, delta, stacked }) => {
  if (!price) {
    return <div className="loading">Loading</div>;
  }

  const stringPrice = price.toString();
  const main = stringPrice.slice(-3, -1);
  const pip = stringPrice.slice(-1);
  const start = stringPrice.slice(0, -3);

  let deltaIcon;
  // if delta is 1 then positive
  // if -1 then negative
  // else display nothing
  if (delta === 1) {
    deltaIcon = <NegativeArrowIcon />;
  } else if (delta === -1) {
    deltaIcon = <PositiveArrowIcon />;
  } else {
    deltaIcon = <div className="delta"></div>;
  }

  const className = classnames('price-ticker', {
    'price-ticker-stacked': stacked,
  });

  return (
    <div className={className}>
      <div>
        <div className="start">
          {start}
        </div>
        <div className="main">
          {main}
        </div>
      </div>
      <div className="pip-delta">
        {deltaIcon}
        <div className="pip">{pip}</div>
      </div>
    </div>
  );
};

PriceTicker.propTypes = {
  price: React.PropTypes.number,
  delta: React.PropTypes.number,
  stacked: React.PropTypes.bool,
};

PriceTicker.defaultProps = {
  stacked: true,
};

export default PriceTicker;
