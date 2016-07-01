import React, { Component } from 'react';
import PriceTicker from '../priceTicker/PriceTickerContainer';
import './Pair.scss';
import CurrencyPairDropdownIcon from '../../svg/CurrencyPairDropdown.svg';

class Pair extends Component {

  amountInputRef(node) {
    this.amountInput = node;
  }

  render() {
    const { pair, tileSelected, selectedTile } = this.props;
    const baseCurrency = pair.substr(0, 3);
    const counterCurrency = pair.substr(3);

    const tileClass = `pair ${selectedTile === pair ? 'selected' : ''}`;

    return (
      <div
        className={tileClass}
        onClick={() => tileSelected(pair)}
      >
        <div className="pair-dropdown">
          <span>{baseCurrency}</span>
          |
          <span>{counterCurrency}</span>
          <CurrencyPairDropdownIcon />
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
          <input ref={(node) => this.amountInputRef(node)} defaultValue="100000" />
          <label>{baseCurrency}</label>
        </div>
      </div>
    );
  }
}

Pair.propTypes = {
  pair: React.PropTypes.string.isRequired,
  selectedTile: React.PropTypes.string,
  tileSelected: React.PropTypes.func.isRequired,
};

export default Pair;
