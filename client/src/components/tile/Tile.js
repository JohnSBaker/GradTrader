import React from 'react';
import PriceTicker from '../priceTicker/PriceTickerContainer';
import './Tile.scss';

const dropdownArrowCode = '9662';

const Tile = () => (
  <div className="tile">
    <div className="pair">
      <span>GBP</span>
      |
      <span>USD</span>
      {String.fromCharCode(dropdownArrowCode)}
    </div>
    <div className="price-tickers">
      <PriceTicker />
      <PriceTicker />
    </div>
    <div className="purchase-buttons">
      <button>BUY</button>
      <button>SELL</button>
    </div>
    <div className="quantity-selector">
      <input />
      <button>GBP</button>
    </div>
  </div>
);

export default Tile;
