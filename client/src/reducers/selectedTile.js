import { SELECT_TILE } from 'actions/selectedTile';

const selectedTile = (state = null, action) => {
  switch (action.type) {
    case SELECT_TILE:
      return action.pairId;
    default:
      return state;
  }
};

export const getSelectedTile = (state = {}) => state.selectedTile;

export const getSelectedTilePairOrDefault = (state = {}) => (
  state.pairs.find(
    (pair) => (pair.id === state.selectedTile)
  ) || state.pairs[0] || {}
);

export default selectedTile;
