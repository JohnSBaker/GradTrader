import React from 'react';
import classnames from 'classnames';
import 'components/priceTicker/PriceTicker.scss';
import NegativeArrowIcon from 'svg/NegativeArrow.svg';
import PositiveArrowIcon from 'svg/PositiveArrow.svg';

const PriceTicker = ({ price, delta, stacked }) => {
  if (!price) {
    return <div className="price-ticker-loading">Loading</div>;
  }
  const main = price.slice(-3, -1);
  const pip = price.slice(-1);
  const start = price.slice(0, -3);

  let deltaIcon;
  // if delta is 1 then positive
  // if -1 then negative
  // else display nothing
  if (delta === 1) {
    deltaIcon = <PositiveArrowIcon />;
  } else if (delta === -1) {
    deltaIcon = <NegativeArrowIcon />;
  } else {
    deltaIcon = <div className="price-ticker-delta"></div>;
  }

  const className = classnames('price-ticker', {
    'price-ticker-stacked': stacked,
  });
  const startClassName = classnames('price-ticker-start', {
    'price-ticker-start-stacked': stacked,
  });

  return (
    <div className={className}>
      <div className={startClassName}>
        {start}
      </div>
      <div className="price-ticker-main-pip-delta">
        <div className="price-ticker-main">
          {main}
        </div>
        <div className="price-ticker-pip-delta">
          {deltaIcon}
          <div className="price-ticker-pip">{pip}</div>
        </div>
      </div>
    </div>
  );
};

PriceTicker.propTypes = {
  price: React.PropTypes.string,
  delta: React.PropTypes.number,
  stacked: React.PropTypes.bool,
};

PriceTicker.defaultProps = {
  stacked: true,
};

export default PriceTicker;
