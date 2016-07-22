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

  handleQuantityChange(event) {
    this.setState({ quantity: event.target.value });
  }

  handleClick() {
    const { pair, selectTile, isSelectable } = this.props;
    if (isSelectable) {
      selectTile(pair.id);
    }
  }

  render() {
    const { pair, buyRequest, sellRequest, isSelected, isSelectable } = this.props;
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
        onClick={() => this.handleClick()}
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
            onClick={() => sellRequest(pair.id, quantity)}
          >SELL
          </button>
          <button
            className="pair-purchase-button"
            onClick={() => buyRequest(pair.id, quantity)}
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
