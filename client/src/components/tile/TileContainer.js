import { connect } from 'react-redux';
import Tile from './Tile';
import { getQuote } from '../../reducers/quotes';

const mapStateToProps = ({ quotes }, { pair }) => ({
  quote: getQuote(quotes, pair.name),
  pair,
});

export default connect(mapStateToProps)(Tile);
