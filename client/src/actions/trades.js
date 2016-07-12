
export const ADD_TRADE = 'ADD_TRADE';
export const CANCEL_TRADE = 'CANCEL_TRADE';

export const addTrade = (trade) => ({
  type: ADD_TRADE,
  ...trade,
});

export const confirmTrade = () => () => console.log('confirm trade');

export const cancelTrade = (pairId) => ({
  type: CANCEL_TRADE,
  pairId,
});
