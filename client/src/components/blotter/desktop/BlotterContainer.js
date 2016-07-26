import { connect } from 'react-redux';
import Blotter from 'components/blotter/desktop/Blotter';
import { getBlotterTrades, getBlotterSort } from 'reducers/trades';
import { requestPreviousTrades, subscribeTrade, unsubscribeTrade, sortBlotter } from 'actions/trades';

const mapStateToProps = (state) => ({
  trades: getBlotterTrades(state),
  blotterSort: getBlotterSort(state),
});

export default connect(mapStateToProps, {
  requestPreviousTrades,
  subscribeTrade,
  unsubscribeTrade,
  sortBlotter,
})(Blotter);
