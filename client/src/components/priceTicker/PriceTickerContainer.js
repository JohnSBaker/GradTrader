import { connect } from 'react-redux';
import PriceTicker from './PriceTicker';

const mapStateToProps = () => ({
  price: 1.42689,
  delta: 1,
});

export default connect(mapStateToProps)(PriceTicker);
