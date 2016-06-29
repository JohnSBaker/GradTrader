import React from 'react';
import PriceTicker from '../priceTicker/PriceTickerContainer';
import './Tile.scss';

const dropdownArrowCode = '9662';

const Tile = ({ pair }) => {
  const baseCurrency = pair.substr(0, 3);
  const counterCurrency = pair.substr(3);

  return (
    <div className="tile">
      <div className="pair">
        <span>{baseCurrency}</span>
        |
        <span>{counterCurrency}</span>
        {String.fromCharCode(dropdownArrowCode)}
      </div>
      <div className="price-tickers">
        <PriceTicker pair={pair} type="bid" />
        <PriceTicker pair={pair} type="ask" />
      </div>
      <div className="purchase-buttons">
        <button>BUY</button>
        <button>SELL</button>
      </div>
      <div className="quantity-selector">
        <input />
        <button>{baseCurrency}</button>
      </div>
    </div>
  );
};

Tile.propTypes = {
  pair: React.PropTypes.string.isRequired,
};

export default Tile;
