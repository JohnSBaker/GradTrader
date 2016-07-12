import React, { Component } from 'react';
import PriceTicker from '../priceTicker/PriceTickerContainer';
import './Pair.scss';
import CurrencyPairDropdownIcon from '../../svg/CurrencyPairDropdown.svg';

class Pair extends Component {

  amountInputRef(node) {
    this.amountInput = node;
  }

  render() {
    const { pair, buyRequest, sellRequest, selectTile, isSelected } = this.props;
    const baseCurrency = pair.id.substr(0, 3);
    const counterCurrency = pair.id.substr(3);

    const tileClass = `pair${isSelected ? ' selected' : ''}`;

    return (
      <div
        className={tileClass}
        onClick={() => selectTile(pair.id)}
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
          <button
            onClick={() => buyRequest(pair.id, this.amountInput.value)}
          >BUY</button>
          <button
            onClick={() => sellRequest(pair.id, this.amountInput.value)}
          >SELL</button>
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
  pair: React.PropTypes.object.isRequired,
  isSelected: React.PropTypes.bool.isRequired,
  selectTile: React.PropTypes.func.isRequired,
  buyRequest: React.PropTypes.func.isRequired,
  sellRequest: React.PropTypes.func.isRequired,
};

export default Pair;
