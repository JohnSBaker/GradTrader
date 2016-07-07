import React, { Component, PropTypes } from 'react';
import Confirmation from './ConfirmationContainer';
import Pair from './PairContainer';
import './Tile.scss';

class Tile extends Component {

  componentDidMount() {
    this.props.subscribePrice(this.props.pair.id);
  }

  componentWillUnmount() {
    this.props.unsubscribePrice(this.props.pair.id);
  }

  render() {
    const child = this.props.quote ?
      <Confirmation pair={this.props.pair} /> :
      <Pair pair={this.props.pair} />;

    return (
      <div className="tile">
        {child}
      </div>
    );
  }
}

Tile.propTypes = {
  quote: PropTypes.object,
  pair: PropTypes.object.isRequired,
  subscribePrice: PropTypes.func.isRequired,
  unsubscribePrice: PropTypes.func.isRequired,
};

export default Tile;
