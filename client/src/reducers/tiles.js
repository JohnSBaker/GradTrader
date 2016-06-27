import { POPULATE_TILES } from '../actions';

const tiles = (state = [], action) => {
  switch (action.type) {
    case POPULATE_TILES:
      return action.tiles;
    default:
      return state;
  }
};

export default tiles;
