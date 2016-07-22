import { SELECT_TILE } from 'actions/selectedTile';

const selectedTile = (state = null, action) => {
  switch (action.type) {
    case SELECT_TILE:
      return action.pairId;
    default:
      return state;
  }
};

export const getSelectedTilePair = (state = {}) => (
  state.pairs.find(
    (pair) => (pair.id === state.selectedTile)
  ) || {}
);

export default selectedTile;
