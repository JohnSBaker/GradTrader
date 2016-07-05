import { ADD_TRADE, ADD_TRADES } from '../actions';

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
    case ADD_TRADES:
      return {
        ...state,
        ...action.trades,
      };
    default:
      return state;
  }
};

export const getSortedTrades = (state = []) => (
  state.sort((a, b) => (b.timestamp - a.timestamp))
);

export default trades;
