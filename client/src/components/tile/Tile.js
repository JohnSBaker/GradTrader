import React, { Component, PropTypes } from 'react';
import Confirmation from './ConfirmationContainer';
import Pair from './PairContainer';
import './Tile.scss';

class Tile extends Component {

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
};

export default Tile;
