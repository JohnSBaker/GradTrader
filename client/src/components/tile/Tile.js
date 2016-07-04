import React from 'react';
import Confirmation from './ConfirmationContainer';
import Pair from './PairContainer';
import './Tile.scss';

const Tile = ({ quote, pair }) => {
  const child = quote ? <Confirmation pair={pair} /> : <Pair pair={pair} />;

  return (
    <div className="tile">
      {child}
    </div>
  );
};

Tile.propTypes = {
  pair: React.PropTypes.object.isRequired,
  quote: React.PropTypes.object,
};

export default Tile;
