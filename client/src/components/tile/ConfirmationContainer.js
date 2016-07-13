import { connect } from 'react-redux';
import Confirmation from './Confirmation';
import { getQuote } from '../../reducers/quotes';
import { confirmTrade } from '../../actions/trades';
import { clearQuote } from '../../actions/quotes';

const mapStateToProps = ({ quotes }, { pair }) => ({
  quote: getQuote(quotes, pair.id),
  pair,
});

export default connect(
  mapStateToProps, {
    confirmTrade,
    clearQuote,
  },
)(Confirmation);
