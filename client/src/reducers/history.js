import { ADD_TRADE_HISTORY } from 'actions/history';

const history = (state = {}, action) => {
  switch (action.type) {
    case ADD_TRADE_HISTORY:
      return {
        ...state,
        [action.pairId]: [...action.history],
      };
    default:
      return state;
  }
};

const getHistory = (state = {}) => (state.history);

export const getTradeHistory = (state = {}, pairId) => {
  const historyForPair = getHistory(state)[pairId];

  if (historyForPair) {
    return historyForPair.map(({ timestamp, open, high, low, close }) => ({
      date: new Date(timestamp),
      open,
      high,
      low,
      close,
    }));
  }
  return null;
};

export default history;
