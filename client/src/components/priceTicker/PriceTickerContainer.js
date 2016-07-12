import { connect } from 'react-redux';
import PriceTicker from './PriceTicker';
import { getFormattedPrice } from '../../reducers/prices';

const mapStateToProps = (state, { pair, type }) => getFormattedPrice(state.prices, pair, type);

export default connect(mapStateToProps)(PriceTicker);
