import { connect } from 'react-redux';
import Confirmation from './Confirmation';
import { getFormattedQuote } from '../../reducers/quotes';
import { confirmTrade } from '../../actions/trades';
import { clearQuote } from '../../actions/quotes';

const mapStateToProps = (state, { pair }) => ({
  quote: getFormattedQuote(state, pair.id),
  pair,
});

export default connect(
  mapStateToProps, {
    confirmTrade,
    clearQuote,
  },
)(Confirmation);
