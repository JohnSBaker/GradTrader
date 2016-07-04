import { connect } from 'react-redux';
import Pair from './Pair';
import { tileSelected, requestQuote } from '../../actions';

const mapStateToProps = ({ selectedTile }, { pair }) => ({
  pair,
  selectedTile,
});

export default connect(mapStateToProps, {
  tileSelected,
  requestQuote,
}
)(Pair);
