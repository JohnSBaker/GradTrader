import React, { Component, PropTypes } from 'react';
import Tile from '../tile/TileContainer';
import './TileGrid.scss';

class TileGrid extends Component {

  componentDidMount() {
    this.props.getValidPairs();
  }

  render() {
    const tileElements = this.props.tiles.map((pair, index) => (
      <Tile key={index} pair={pair} />
    ));

    return (
      <div className="tile-grid">
      {tileElements}
      </div>
    );
  }
}

TileGrid.propTypes = {
  tiles: PropTypes.array.isRequired,
  getValidPairs: PropTypes.func.isRequired,
};

export default TileGrid;
