import { connect } from 'react-redux';
import TileGrid from './TileGrid';
import { getValidPairs } from '../../actions';

const mapStateToProps = ({ tiles }) => ({
  tiles,
});

export default connect(mapStateToProps, {
  getValidPairs,
})(TileGrid);
