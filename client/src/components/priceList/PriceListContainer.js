import { connect } from 'react-redux';
import PriceList from './PriceList';
import { getValidPairs } from 'actions/pairs';
import { subscribePrice, unsubscribePrice } from 'actions/prices';

const mapStateToProps = ({ pairs }) => ({
  pairs,
});

export default connect(mapStateToProps, {
  getValidPairs,
  subscribePrice,
  unsubscribePrice,
})(PriceList);
