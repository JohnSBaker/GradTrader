import { connect } from 'react-redux';
import Chart from './Chart';
import { getSelectedTilePair } from '../../reducers/selectedTile';
import { getFormattedPrice } from '../../reducers/prices';

const mapStateToProps = (state) => {
  const pair = getSelectedTilePair(state);
  const currentPrice = getFormattedPrice(state.prices, pair, 'ask');
  return {
    pair,
    currentPrice,
  };
};

export default connect(
  mapStateToProps
)(Chart);
