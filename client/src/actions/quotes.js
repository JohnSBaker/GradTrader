import * as api from 'api/apiService';
import { getQuotePrice } from 'reducers/prices';
import { getUser } from 'reducers/user';

export const QUOTE_RESPONSE_SUCCESS = 'QUOTE_RESPONSE_SUCCESS';
export const QUOTE_RESPONSE_FAILURE = 'QUOTE_RESPONSE_FAILURE';
export const CLEAR_QUOTE = 'CLEAR_QUOTE';

const BUY = 'BUY';
const SELL = 'SELL';

const quoteResponseSuccess = ({ pairId, quantity, direction, price, expires, quoteId }) => ({
  type: QUOTE_RESPONSE_SUCCESS,
  pairId,
  quantity,
  direction,
  price,
  expires,
  quoteId,
});

const quoteResponseFailure = (error) => ({
  type: QUOTE_RESPONSE_FAILURE,
  message: error.message || 'An unknown error occurred',
});

export const clearQuote = (pairId) => ({
  type: CLEAR_QUOTE,
  pairId,
});

export const requestQuote = (pairId, quantity, direction) => (dispatch, getState) => {
  const state = getState();
  const indicativePrice = getQuotePrice(state, pairId, direction);
  const { id, selectedPortfolioId } = getUser(state);

  api
    .requestQuote(id, selectedPortfolioId, pairId, quantity, direction, indicativePrice)
    .then((quote) => dispatch(quoteResponseSuccess(quote)))
    .catch((error) => dispatch(quoteResponseFailure(error)));
};

export const buyRequest = (pairId, quantity) => requestQuote(pairId, quantity, BUY);
export const sellRequest = (pairId, quantity) => requestQuote(pairId, quantity, SELL);
