import { connect } from 'react-redux';
import PriceTicker from './PriceTicker';
import { getPrice } from '../../reducers/prices';

const mapStateToProps = (state, { pair, type }) => {
  const { price, delta } = getPrice(state.prices, pair, type);
  return {
    price,
    delta,
  };
};

export default connect(mapStateToProps)(PriceTicker);
