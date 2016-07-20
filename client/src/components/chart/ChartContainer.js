import { connect } from 'react-redux';
import Chart from './Chart';
import { getSelectedTilePair } from 'reducers/selectedTile';
import { getMidPrice } from 'reducers/prices';
import { requestTradeHistory } from 'actions/history';
import { getTradeHistory } from 'reducers/history';

const mapStateToProps = (state) => {
  const pair = getSelectedTilePair(state);
  return {
    pair,
    currentPrice: getMidPrice(state, pair.id),
    historyData: getTradeHistory(state, pair.id),
  };
};

export default connect(mapStateToProps, {
  requestTradeHistory,
})(Chart);
