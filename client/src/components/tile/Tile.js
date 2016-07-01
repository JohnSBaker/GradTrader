import React from 'react';
import Pair from './PairContainer';
import './Tile.scss';

const Tile = ({ pair }) => (
  <div className="tile">
    <Pair pair={pair} />
  </div>
);

Tile.propTypes = {
  pair: React.PropTypes.string.isRequired,
};

export default Tile;
