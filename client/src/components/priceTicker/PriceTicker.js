import React from 'react';
import './PriceTicker.scss';

const PriceTicker = (props) => {
  const stringPrice = props.price.toString();
  const main = stringPrice.slice(-3, -1);
  const pip = stringPrice.slice(-1);
  const start = stringPrice.slice(0, -3);

  let deltaIcon;
  if (props.delta > 0) {
    deltaIcon = <div className="delta negative">&#x25B2;</div>;
  } else if (props.delta < 0) {
    deltaIcon = <div className="delta positive">&#x25BC;</div>;
  } else {
    deltaIcon = <div className="delta neutral">-</div>;
  }

  return (
    <div className="price-ticker">
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
  price: React.PropTypes.number.isRequired,
  delta: React.PropTypes.number.isRequired,
};

export default PriceTicker;
