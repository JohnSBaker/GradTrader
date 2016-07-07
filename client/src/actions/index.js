import * as api from '../api/apiService';

export const GET_VALID_PAIRS_REQUEST = 'GET_VALID_PAIRS_REQUEST';
export const GET_VALID_PAIRS_SUCCESS = 'GET_VALID_PAIRS_SUCCESS';
export const GET_VALID_PAIRS_FAILURE = 'GET_VALID_PAIRS_FAILURE';
export const POPULATE_TILES = 'POPULATE_TILES';
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

export const requestQuote = (pairId, amount, purchaseType) => (dispatch) => {
  dispatch({
    type: QUOTE_REQUEST,
    pairId,
    amount,
    purchaseType,
  });

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
