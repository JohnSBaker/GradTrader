import React from 'react';
import Tile from '../tile/Tile';
import './TileGrid.scss';

const TileGrid = ({ tiles }) => {
  const tileElements = tiles ? tiles.map((tile) => (<Tile key={tile.id} />)) : null;

  return (
    <div className="tile-grid">
    {tileElements}
    </div>
  );
};

TileGrid.propTypes = {
  tiles: React.PropTypes.array,
};

export default TileGrid;
