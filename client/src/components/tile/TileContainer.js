import { connect } from 'react-redux';
import Tile from 'components/tile/Tile';
import { getQuote } from 'reducers/quotes';
import { getPair } from 'reducers/pairs';
import { getValidPairs } from 'actions/pairs';
import { subscribePrice, unsubscribePrice } from 'actions/prices';

const mapStateToProps = (state, { pairId }) => ({
  quote: getQuote(state, pairId),
  pair: getPair(state, pairId),
});

export default connect(mapStateToProps, {
  subscribePrice,
  unsubscribePrice,
  getValidPairs,
})(Tile);
