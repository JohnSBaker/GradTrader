import { connect } from 'react-redux';
import Confirmation from './Confirmation';
import { getQuote } from '../../reducers/quotes';
import { confirmTrade, cancelTrade } from '../../actions';

const mapStateToProps = ({ quotes }, { pair }) => ({
  quote: getQuote(quotes, pair.id),
  pair,
});

export default connect(
  mapStateToProps, {
    confirmTrade,
    cancelTrade,
  },
)(Confirmation);
