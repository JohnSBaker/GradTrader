import { connect } from 'react-redux';
import Chart from 'components/chart/Chart';
import { getSelectedTilePairOrDefault } from 'reducers/selectedTile';
import { getMidPrice } from 'reducers/prices';
import { requestPriceHistory } from 'actions/history';
import { setResolution, setInterval } from 'actions/chart';
import { getPriceHistory } from 'reducers/history';
import { getResolutionOrDefault, getIntervalOrDefault } from 'reducers/chart';

const mapStateToProps = (state) => {
  const pair = getSelectedTilePairOrDefault(state);
  const resolution = getResolutionOrDefault(state, pair.id);
  const interval = getIntervalOrDefault(state, pair.id);
  const currentPrice = getMidPrice(state, pair.id);
  const priceHistory = getPriceHistory(state, pair.id, resolution);
  return {
    pair,
    resolution,
    interval,
    currentPrice,
    priceHistory,
  };
};

export default connect(mapStateToProps, {
  requestPriceHistory,
  setResolution,
  setInterval,
})(Chart);
