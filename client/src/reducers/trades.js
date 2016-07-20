import { ADD_TRADES, SET_TRADES } from 'actions/trades';
import { getUser } from './user';
import formatPrice from 'utils/priceFormatter';
import { getPair } from 'reducers/pairs';

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

export const getTrades = (state = {}) => (state.trades);

export const getBlotterTrades = (state = {}, pairId) => {
  let sortedTrades;

  const tradesState = getTrades(state);

  if (pairId) {
    sortedTrades = tradesState.filter(trade => trade.pairId === pairId);
  } else {
    sortedTrades = tradesState.slice(0);
  }

  sortedTrades.sort((a, b) => (b.timestamp - a.timestamp));
  return sortedTrades.map((trade) => {
    const user = getUser(state);
    const portfolioName = user.portfolios[trade.portfolioId];
    const userName = user.name;
    return {
      portfolioName,
      userName,
      direction: trade.direction,
      pairId: trade.pairId,
      quantity: trade.quantity,
      timestamp: trade.timestamp,
      tradeId: trade.tradeId,
      formattedPrice: formatPrice(trade.price, getPair(state, trade.pairId).decimals),
    };
  });
};

export default trades;
