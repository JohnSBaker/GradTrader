import { SET_SELECTED_TILE } from '../actions';

const selectedTile = (state = null, action) => {
  switch (action.type) {
    case SET_SELECTED_TILE:
      return action.pair;
    default:
      return state;
  }
};

export default selectedTile;
