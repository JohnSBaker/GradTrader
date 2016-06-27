import { GET_VALID_PAIRS_SUCCESS } from '../actions';

const pairs = (state = [], action) => {
  switch (action.type) {
    case GET_VALID_PAIRS_SUCCESS:
      return action.pairs;
    default:
      return state;
  }
};

export default pairs;
