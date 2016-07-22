import { connect } from 'react-redux';
import TileGrid from 'components/tileGrid/TileGrid';
import { getValidPairs } from 'actions/pairs';
import { getTilePairs } from 'reducers/pairs';

const mapStateToProps = (state) => ({
  tiles: getTilePairs(state),
});

export default connect(mapStateToProps, {
  getValidPairs,
})(TileGrid);
