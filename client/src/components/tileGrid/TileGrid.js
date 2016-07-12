import React from 'react';
import Tile from '../tile/TileContainer';
import './TileGrid.scss';

class TileGrid extends React.Component {

  componentDidMount() {
    this.props.getValidPairs();
  }

  render() {
    const tileElements = this.props.tiles.map((pair, index) => (
      <div className="tile-grid-tile" key={index}>
        <Tile pairId={pair.id} />
      </div>
    ));

    return (
      <div className="tile-grid">
        {tileElements}
      </div>
    );
  }
}

TileGrid.propTypes = {
  tiles: React.PropTypes.array.isRequired,
  getValidPairs: React.PropTypes.func.isRequired,
};

export default TileGrid;
