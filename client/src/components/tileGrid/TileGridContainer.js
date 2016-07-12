import { connect } from 'react-redux';
import TileGrid from './TileGrid';
import { getValidPairs } from '../../actions/pairs';
import { getTilePairs } from '../../reducers/pairs';

const mapStateToProps = ({ pairs }) => ({
  tiles: getTilePairs(pairs),
});

export default connect(mapStateToProps, {
  getValidPairs,
})(TileGrid);
