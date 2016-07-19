import * as subscription from '../api/subscriptionService';

export const SUBSCRIBE_PRICE_REQUEST = 'SUBSCRIBE_PRICE_REQUEST';
export const ADD_PRICES = 'ADD_PRICES';
export const SET_SELECTED_TILE = 'SET_SELECTED_TILE';

export const subscribePrice = (pairId) => () => {
  subscription.subscribePrice(pairId);
};

export const unsubscribePrice = (pairId) => () => {
  subscription.unsubscribePrice(pairId);
};

function calculatePriceDelta(state, pairId, bid, ask) {
  const price = {
    pairId,
    bid,
    ask,
  };

  if (state[pairId]) {
    price.bidDelta = bid < state[pairId].bid ? -1 : 1;
    price.askDelta = ask < state[pairId].ask ? -1 : 1;
  }

  return price;
}

const addPrices = (prices) => ({
  type: ADD_PRICES,
  prices,
});

export const setupPriceFeed = () => (dispatch, getState) => {
  subscription.setCallback('prices', ({ data }) => {
    const state = getState().prices;
    const prices = data.map(({ pairId, bid, ask }) => calculatePriceDelta(state, pairId, bid, ask));
    dispatch(addPrices(prices));
  });
};
