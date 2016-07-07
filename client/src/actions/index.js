import * as api from '../api/apiService';
import * as sub from '../api/subscriptionService';

export const GET_VALID_PAIRS_REQUEST = 'GET_VALID_PAIRS_REQUEST';
export const GET_VALID_PAIRS_SUCCESS = 'GET_VALID_PAIRS_SUCCESS';
export const GET_VALID_PAIRS_FAILURE = 'GET_VALID_PAIRS_FAILURE';
export const POPULATE_TILES = 'POPULATE_TILES';
export const SUBSCRIBE_PRICE_REQUEST = 'SUBSCRIBE_PRICE_REQUEST';
export const ADD_PRICE = 'ADD_PRICE';
export const ADD_PRICES = 'ADD_PRICES';
export const SET_SELECTED_TILE = 'SET_SELECTED_TILE';
export const REQUEST_QUOTE = 'REQUEST_QUOTE';
export const QUOTE_RESPONSE_SUCCESS = 'QUOTE_RESPONSE_SUCCESS';
export const QUOTE_RESPONSE_ERROR = 'QUOTE_RESPONSE_ERROR';
export const QUOTE_REQUEST = 'QUOTE_REQUEST';
export const QUOTE_EXPIRED = 'QUOTE_EXPIRED';
export const ADD_TRADE = 'ADD_TRADE';
export const ADD_TRADES = 'ADD_TRADES';

export const getValidPairs = () => (dispatch) => {
  dispatch({
    type: GET_VALID_PAIRS_REQUEST,
  });

  api
    .getValidPairs()
    .then(
      (pairs) => {
        dispatch({
          type: GET_VALID_PAIRS_SUCCESS,
          pairs,
        });
        dispatch({
          type: POPULATE_TILES,
          tiles: pairs.filter((pair, index) => (index < 9 ? pair : undefined)),
        });
      }
    )
    .catch(
      (error) => {
        dispatch({
          type: GET_VALID_PAIRS_FAILURE,
          message: error.message || 'Something went wrong',
        });
      }
    );
};

export const tileSelected = (pairId) => ({
  type: SET_SELECTED_TILE,
  pairId,
});

export const addTrade = (trade) => ({
  type: ADD_TRADE,
  ...trade,
});

const quoteRequest = (pairId, amount, purchaseType) => ({
  type: QUOTE_REQUEST,
  pairId,
  amount,
  purchaseType,
});

export const requestQuote = (pairId, amount, purchaseType) => (dispatch) => {
  dispatch(quoteRequest(pairId, amount, purchaseType));

  // Will be replaced by real request when avalable
  dispatch({
    type: QUOTE_RESPONSE_SUCCESS,
    amount,
    pairId,
    purchaseType,
    timestamp: Date.now(),
    id: 9876,
    price: 1.23456,
  });
};

export const confirmTrade = () => () => console.log('confirm trade');

export const cancelTrade = (pairId) => ({
  type: QUOTE_EXPIRED,
  pairId,
});

export const subscribePrice = (pairId) => () => {
  sub.subscribePrice(pairId);
};

export const unsubscribePrice = (pairId) => () => {
  sub.unsubscribePrice(pairId);
};

function calculatePriceDelta(state, pairId, bid, ask) {
  const price = {
    pairId,
    bid,
    ask,
  };

  if (state[pairId]) {
    price.bidDelta = bid < state[pairId].bid ? 1 : -1;
    price.askDelta = ask < state[pairId].ask ? 1 : -1;
  }

  return price;
}

const addPrice = (price) => ({
  type: ADD_PRICE,
  ...price,
});

const addPrices = (prices) => ({
  type: ADD_PRICES,
  prices,
});

export const setupPriceFeed = () => (dispatch, getState) => {
  sub.setCallback('price', ({ pairId, ask, bid }) => {
    const state = getState().prices;
    const price = calculatePriceDelta(state, pairId, bid, ask);
    dispatch(addPrice(price));
  });
  sub.setCallback('prices', ({ data }) => {
    const state = getState().prices;
    const prices = data.map(({ pairId, bid, ask }) => calculatePriceDelta(state, pairId, bid, ask));
    dispatch(addPrices(prices));
  });
};
