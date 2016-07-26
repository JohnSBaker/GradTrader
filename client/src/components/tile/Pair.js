import React, { Component } from 'react';
import classnames from 'classnames';
import PriceTicker from 'components/priceTicker/PriceTickerContainer';
import 'components/tile/Pair.scss';
import CurrencyPairDropdownIcon from 'svg/CurrencyPairDropdown.svg';

class Pair extends Component {

  constructor(props) {
    super(props);
    this.state = {
      quantity: 100000,
    };
  }

  onBuyClick(event, pairId, quantity) {
    const { buyRequest } = this.props;
    buyRequest(pairId, quantity);
    event.preventDefault();
  }

  onSellClick(event, pairId, quantity) {
    const { sellRequest } = this.props;
    sellRequest(pairId, quantity);
    event.preventDefault();
  }

  handleQuantityChange(event) {
    this.setState({ quantity: event.target.value });
  }

  handleClick(event) {
    const { pair, selectTile, isSelectable } = this.props;
    if (isSelectable && !event.defaultPrevented) {
      selectTile(pair.id);
    }
  }

  render() {
    const { pair, isSelected, isSelectable } = this.props;
    const { quantity } = this.state;
    const baseCurrency = pair.id.substr(0, 3);
    const counterCurrency = pair.id.substr(3, 3);

    const tileClass = classnames('pair', {
      'pair-selected': isSelected,
      'pair-selectable': isSelectable,
    });

    return (
      <div
        className={tileClass}
        onClick={(event) => this.handleClick(event)}
      >
        <div className="pair-dropdown">
          <span>{baseCurrency}</span>
          |
          <span>{counterCurrency}</span>
          <CurrencyPairDropdownIcon />
        </div>
        <div className="pair-price-tickers">
          <PriceTicker pair={pair} type="bid" />
          <PriceTicker pair={pair} type="ask" />
        </div>
        <div className="pair-purchase-buttons">
          <button
            className="pair-purchase-button"
            onClick={(event) => this.onSellClick(event, pair.id, quantity)}
          >SELL
          </button>
          <button
            className="pair-purchase-button"
            onClick={(event) => this.onBuyClick(event, pair.id, quantity)}
          >BUY
          </button>
        </div>
        <div className="pair-quantity">
          <input
            className="pair-quantity-value"
            onChange={(event) => this.handleQuantityChange(event)}
            value={quantity}
          />
          <label className="pair-quantity-label">
            {baseCurrency}</label>
        </div>
      </div>
    );
  }
}

Pair.propTypes = {
  pair: React.PropTypes.object.isRequired,
  isSelected: React.PropTypes.bool.isRequired,
  isSelectable: React.PropTypes.bool,
  selectTile: React.PropTypes.func.isRequired,
  buyRequest: React.PropTypes.func.isRequired,
  sellRequest: React.PropTypes.func.isRequired,
};

Pair.defaultProps = {
  isSelectable: true,
};

export default Pair;
