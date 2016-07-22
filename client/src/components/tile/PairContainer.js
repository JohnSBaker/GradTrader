import { connect } from 'react-redux';
import Pair from 'components/tile/Pair';
import { buyRequest, sellRequest } from 'actions/quotes';
import { selectTile } from 'actions/selectedTile';
import { getSelectedTile } from 'reducers/selectedTile';

const mapStateToProps = (state, { pair }) => ({
  pair,
  isSelected: getSelectedTile(state) === pair.id,
});

export default connect(mapStateToProps, {
  buyRequest,
  sellRequest,
  selectTile,
})(Pair);
