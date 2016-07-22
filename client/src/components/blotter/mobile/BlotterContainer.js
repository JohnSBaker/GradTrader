import { connect } from 'react-redux';
import Blotter from 'components/blotter/mobile/Blotter';
import { getBlotterTrades } from 'reducers/trades';
import { requestPreviousTrades, subscribeTrade, unsubscribeTrade } from 'actions/trades';

const mapStateToProps = (state, { pairId }) => ({
  trades: getBlotterTrades(state, pairId),
});

export default connect(mapStateToProps, {
  requestPreviousTrades,
  subscribeTrade,
  unsubscribeTrade,
})(Blotter);
