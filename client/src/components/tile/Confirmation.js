import React, { Component } from 'react';
import './Confirmation.scss';

class Confirmation extends Component {

  constructor(props) {
    super(props);

    this.time = 5;
    this.state = {
      time: this.time,
    };

    this.timer = setInterval(() => {
      this.time--;
      this.setState({
        time: this.time,
      });
    }, 1000);

    this.timeout = setTimeout(() => {
      clearInterval(this.timer);
      this.clearQuote();
    }, 5000);
  }

  clearIntervals = () => {
    clearInterval(this.timer);
    clearInterval(this.timeout);
  }

  clearQuote = () => {
    this.clearIntervals();
    this.props.clearQuote(this.props.pair.id);
  }

  confirmTrade = () => {
    this.clearIntervals();
    this.props.confirmTrade(this.props.pair.id);
  }

  render() {
    const baseCurrency = this.props.pair.id.substr(0, 3);
    return (
      <div className="confirmation">
        <div>
          {this.props.quote.purchaseType}
        </div>
        <div className="quantity">{`${this.props.quote.quantity} ${baseCurrency}`}</div>
        <div>
          <span>@</span>
          <span>{this.props.quote.price}</span>
        </div>
        <div className="spinner-countdown">
          <div className="spinner-container">
            <div className="spinner">
            </div>
          </div>
          <div className="countdown">{this.state.time}</div>
        </div>
        <div className="confirmation-buttons">
          <button onClick={this.confirmTrade}>CONFIRM</button>
          <button onClick={this.clearQuote}>CANCEL</button>
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
