import React, { PropTypes } from 'react';
import Tile from 'components/tile/TileContainer';
import Blotter from 'components/blotter/mobile/BlotterContainer';
import 'routes/mobile/pair/Pair.scss';

const Pair = ({ params }) => (
  <div className="routes-pair">
    <div className="routes-pair-tile">
      <Tile pairId={params.pairId} />
    </div>
    <div className="routes-pair-blotter">
      <Blotter pairId={params.pairId} showBox={false} />
    </div>
  </div>
);

Pair.propTypes = {
  params: PropTypes.object,
};

export default Pair;
