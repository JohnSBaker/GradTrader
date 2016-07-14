import { GET_VALID_PAIRS_SUCCESS } from '../actions/pairs';

const pairs = (state = [], action) => {
  switch (action.type) {
    case GET_VALID_PAIRS_SUCCESS:
      return action.pairs;
    default:
      return state;
  }
};

export const getTilePairs = (state = []) => state.filter(
  (pair, index) => (index < 9)
);

export const getPair = (state = [], pairId) => (state.filter(pair => pair.id === pairId)[0]);

export default pairs;
