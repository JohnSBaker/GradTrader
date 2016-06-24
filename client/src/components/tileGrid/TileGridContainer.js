import { connect } from 'react-redux';
import TileGrid from './TileGrid';
import { getValidPairs } from '../../actions';

const mapStateToProps = () => ({
  tiles: [
    { id: 1 },
    { id: 2 },
    { id: 3 },
    { id: 4 },
    { id: 5 },
    { id: 6 },
    { id: 7 },
    { id: 8 },
    { id: 9 },
  ],
});

export default connect(mapStateToProps, {
  getValidPairs,
})(TileGrid);
