import { connect } from 'react-redux';
import PriceTicker from './PriceTicker';
import { getFormattedPrice } from '../../reducers/prices';

const mapStateToProps = (state, { pair, type }) => {
  const { price, delta } = getFormattedPrice(state.prices, pair, type);
  return {
    price,
    delta,
  };
};

export default connect(mapStateToProps)(PriceTicker);
