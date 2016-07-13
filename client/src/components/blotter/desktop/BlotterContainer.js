import { connect } from 'react-redux';
import Blotter from './Blotter';
import { getSortedTrades } from '../../../reducers/trades';
import { requestPreviousTrades } from '../../../actions/trades';

const mapStateToProps = ({ trades }) => ({
  trades: getSortedTrades(trades),
});

export default connect(mapStateToProps, {
  requestPreviousTrades,
})(Blotter);
