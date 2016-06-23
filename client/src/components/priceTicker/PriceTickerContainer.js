import { connect } from 'react-redux';
import PriceTicker from './PriceTicker';

const mapStateToProps = () => ({
  price: Number((Math.random() * 100).toFixed(4)),
  delta: Math.random() - 0.5,
});

export default connect(mapStateToProps)(PriceTicker);
