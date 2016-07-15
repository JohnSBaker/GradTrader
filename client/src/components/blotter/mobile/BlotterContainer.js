import { connect } from 'react-redux';
import Blotter from './Blotter';
import { getSortedTrades } from 'reducers/trades';
import { requestPreviousTrades, subscribeTrade, unsubscribeTrade } from '../../../actions/trades';

const mapStateToProps = (state, { pairId }) => ({
  trades: getSortedTrades(state, pairId),
});

export default connect(mapStateToProps, {
  requestPreviousTrades,
  subscribeTrade,
  unsubscribeTrade,
})(Blotter);
