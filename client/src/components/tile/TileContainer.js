import { connect } from 'react-redux';
import Tile from './Tile';
import { getQuote } from '../../reducers/quotes';
import { subscribePrice, unsubscribePrice } from '../../actions/prices';

const mapStateToProps = ({ quotes }, { pair }) => ({
  quote: getQuote(quotes, pair.id),
  pair,
});

export default connect(mapStateToProps, {
  subscribePrice,
  unsubscribePrice,
})(Tile);
