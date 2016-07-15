import { ADD_TRADES, SET_TRADES } from '../actions/trades';
import { getPortfolioName, getUserName } from './user';

const trades = (state = [], action) => {
  switch (action.type) {
    case ADD_TRADES:
      return [
        ...state,
        ...action.trades,
      ];
    case SET_TRADES:
      return action.trades;
    default:
      return state;
  }
};

export const getSortedTrades = (state = {}, pairId) => {
  let sortedTrades;
  if (pairId) {
    sortedTrades = state.trades.filter(trade => trade.pairId === pairId);
  } else {
    sortedTrades = state.trades.slice(0);
  }
  sortedTrades.sort((a, b) => (b.timestamp - a.timestamp));
  return sortedTrades.map((trade) => {
    const portfolioName = getPortfolioName(state, trade.portfolioId);
    const userName = getUserName(state);
    return {
      portfolioName,
      userName,
      tradeId: trade.tradeId,
      pairId: trade.pairId,
      quantity: trade.quantity,
      direction: trade.direction,
      price: trade.price,
    };
  });
};

export default trades;
