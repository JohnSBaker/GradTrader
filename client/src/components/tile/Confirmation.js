import React, { Component } from 'react';
import 'components/tile/Confirmation.scss';

class Confirmation extends Component {

  constructor(props) {
    super(props);

    this.state = {
      time: 5,
    };

    this.timer = setInterval(() => {
      const time = this.state.time - 1;
      if (time > 0) {
        this.setState({
          time,
        });
      } else {
        clearInterval(this.timer);
        this.clearQuote();
      }
    }, 1000);
  }

  clearIntervals = () => {
    clearInterval(this.timer);
  }

  clearQuote = () => {
    const { clearQuote, pair } = this.props;
    this.clearIntervals();
    clearQuote(pair.id);
  }

  confirmTrade = () => {
    const { confirmTrade, pair } = this.props;
    this.clearIntervals();
    confirmTrade(pair.id);
  }

  render() {
    const { pair, quote } = this.props;
    const { time } = this.state;
    const baseCurrency = pair.id.substr(0, 3);
    return (
      <div className="confirmation">
        <div>
          {quote.purchaseType}
        </div>
        <div className="quantity">{`${quote.quantity} ${baseCurrency}`}</div>
        <div>
          <span>@</span>
          <span>{quote.formattedPrice}</span>
        </div>
        <div className="confirmation-spinner-container">
          <div className="confirmation-countdown">{time}</div>
          <div className="confirmation-spinner">
          </div>
        </div>
        <div className="confirmation-buttons">
          <button
            className="confirmation-button"
            onClick={this.confirmTrade}
          >
            CONFIRM
          </button>
          <button
            className="confirmation-button"
            onClick={this.clearQuote}
          >
            CANCEL
          </button>
        </div>
      </div>
    );
  }
}

Confirmation.propTypes = {
  quote: React.PropTypes.object.isRequired,
  pair: React.PropTypes.object.isRequired,
  confirmTrade: React.PropTypes.func.isRequired,
  clearQuote: React.PropTypes.func.isRequired,
};

export default Confirmation;
