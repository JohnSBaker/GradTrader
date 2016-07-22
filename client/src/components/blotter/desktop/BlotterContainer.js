import { connect } from 'react-redux';
import Blotter from 'components/blotter/desktop/Blotter';
import { getBlotterTrades } from 'reducers/trades';
import { requestPreviousTrades, subscribeTrade, unsubscribeTrade } from 'actions/trades';

const mapStateToProps = (state) => ({
  trades: getBlotterTrades(state),
});

export default connect(mapStateToProps, {
  requestPreviousTrades,
  subscribeTrade,
  unsubscribeTrade,
})(Blotter);
