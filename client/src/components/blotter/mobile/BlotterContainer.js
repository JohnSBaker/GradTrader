import { connect } from 'react-redux';
import Blotter from './Blotter';
import { getSortedTrades } from 'reducers/trades';

const mapStateToProps = ({ trades }, { pairId }) => ({
  trades: getSortedTrades(trades, pairId),
});

export default connect(mapStateToProps)(Blotter);
