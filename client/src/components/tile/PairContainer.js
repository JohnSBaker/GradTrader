import { connect } from 'react-redux';
import Pair from './Pair';
import { tileSelected } from '../../actions';

const mapStateToProps = ({ selectedTile }, { pair }) => ({
  pair,
  selectedTile,
});

export default connect(mapStateToProps, {
  tileSelected,
}
)(Pair);
