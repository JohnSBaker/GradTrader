import { connect } from 'react-redux';
import Tile from './Tile';
import { getQuote } from 'reducers/quotes';
import { getPair } from 'reducers/pairs';
import { getValidPairs } from 'actions/pairs';
import { subscribePrice, unsubscribePrice } from 'actions/prices';

const mapStateToProps = ({ quotes, pairs }, { pairId }) => ({
  quote: getQuote(quotes, pairId),
  pair: getPair(pairs, pairId),
});

export default connect(mapStateToProps, {
  subscribePrice,
  unsubscribePrice,
  getValidPairs,
})(Tile);
