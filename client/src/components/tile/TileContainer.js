import { connect } from 'react-redux';
import Tile from './Tile';

const mapStateToProps = (_, { pair }) => ({
  pair,
});

export default connect(mapStateToProps)(Tile);
