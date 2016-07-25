import { ADD_PRICE_HISTORY } from 'actions/history';

const history = (state = {}, action) => {
  switch (action.type) {
    case ADD_PRICE_HISTORY:
      return {
        ...state,
        [action.pairId]: {
          ...state[action.pairId],
          [action.resolution]: [...action.history],
        },
      };
    default:
      return state;
  }
};

const getHistory = (state = {}) => (state.history);

export const getPriceHistory = (state = {}, pairId, resolution) => {
  const historyForPair = getHistory(state)[pairId];

  if (historyForPair) {
    const historyForResolution = historyForPair[resolution];

    if (historyForResolution) {
      return historyForResolution;
    }
  }

  return null;
};

export default history;
