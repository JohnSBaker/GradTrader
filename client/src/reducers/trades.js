import { ADD_TRADES } from '../actions/trades';

const trades = (state = [], action) => {
  switch (action.type) {
    case ADD_TRADES:
      return [
        ...state,
        ...action.trades,
      ];
    default:
      return state;
  }
};

export const getSortedTrades = (state = [], pairId) => {
  let sortedTrades;
  if (pairId) {
    sortedTrades = state.filter(trade => trade.pairId === pairId);
  } else {
    sortedTrades = state.slice(0);
  }
  sortedTrades.sort((a, b) => (b.timestamp - a.timestamp));
  return sortedTrades;
};

export default trades;
