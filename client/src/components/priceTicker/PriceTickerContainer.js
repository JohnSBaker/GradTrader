import { connect } from 'react-redux';
import PriceTicker from './PriceTicker';

const mapStateToProps = () => ({
  price: Number((Math.random() * 10).toFixed(4)),
  delta: Math.random() > 0.5 ? 1 : -1,
});

export default connect(mapStateToProps)(PriceTicker);
