import React, { Component, PropTypes } from 'react';
import Tile from '../tile/Tile';
import './TileGrid.scss';

class TileGrid extends Component {

  componentDidMount() {
    this.props.getValidPairs();
  }

  render() {
    const tileElements = this.props.tiles ?
      this.props.tiles.map((tile) => (<Tile key={tile.id} pair={tile.pair} />)) : null;

    return (
      <div className="tile-grid">
      {tileElements}
      </div>
    );
  }
}

TileGrid.propTypes = {
  tiles: React.PropTypes.array,
  getValidPairs: PropTypes.func.isRequired,
};

export default TileGrid;
