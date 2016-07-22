import { GET_VALID_PAIRS_SUCCESS } from 'actions/pairs';

const pairs = (state = [], action) => {
  switch (action.type) {
    case GET_VALID_PAIRS_SUCCESS:
      return action.pairs;
    default:
      return state;
  }
};

export const getPairs = (state = {}) => (state.pairs);

export const getTilePairs = (state = {}) => getPairs(state).filter(
  (pair, index) => (index < 9)
);

export const getPair = (state = {}, pairId) => (getPairs(state).find(pair => pair.id === pairId));

export default pairs;
