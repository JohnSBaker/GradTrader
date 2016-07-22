import { connect } from 'react-redux';
import PriceTicker from 'components/priceTicker/PriceTicker';
import { getFormattedPrice } from 'reducers/prices';

const mapStateToProps = (state, { pair, type }) => getFormattedPrice(state, pair, type);

export default connect(mapStateToProps)(PriceTicker);
