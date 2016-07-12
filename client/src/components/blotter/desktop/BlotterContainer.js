import { connect } from 'react-redux';
import Blotter from './Blotter';
import { getSortedTrades } from '../../../reducers/trades';

const mapStateToProps = ({ trades }) => ({
  trades: getSortedTrades(trades),
});

export default connect(mapStateToProps)(Blotter);
