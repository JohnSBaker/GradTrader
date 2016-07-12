import { ADD_TRADE } from '../actions/trades';

const trades = (state = [], action) => {
  switch (action.type) {
    case ADD_TRADE:
      return [
        ...state,
        {
          tradeId: action.tradeId,
          timestamp: action.timestamp,
          time: action.time,
          date: action.date,
          formattedPair: action.formattedPair,
          direction: action.direction,
          quantity: action.quantity,
          rate: action.rate,
          portfolio: action.portfolio,
          user: action.user,
        },
      ];
    default:
      return state;
  }
};

export const getSortedTrades = (state = [], pairId) => {
  let sortedTrades;
  if (pairId) {
    sortedTrades = state.filter(trade => trade.pair === pairId);
  } else {
    sortedTrades = state.slice(0);
  }
  sortedTrades.sort((a, b) => (b.timestamp - a.timestamp));
  return sortedTrades;
};

export default trades;
