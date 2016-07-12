import { connect } from 'react-redux';
import Chart from './Chart';
import { getSelectedTilePair } from '../../reducers/selectedTile';
import { getPrice } from '../../reducers/prices';

const mapStateToProps = (state) => {
  const pair = getSelectedTilePair(state);
  const currentPrice = getPrice(state.prices, pair, 'ask');
  return {
    pair,
    currentPrice,
  };
};

export default connect(
  mapStateToProps
)(Chart);
