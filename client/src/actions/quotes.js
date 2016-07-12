import * as api from '../api/apiService';

export const QUOTE_RESPONSE_SUCCESS = 'QUOTE_RESPONSE_SUCCESS';
export const QUOTE_RESPONSE_ERROR = 'QUOTE_RESPONSE_ERROR';

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

const requestQuote = (pairId, quantity, direction) => (dispatch) => {
  // Will be replaced by real request when avalable
  const quote = {
    pairId,
    quantity,
    direction,
    price: 1.23456,
    expires: Date.now(),
    quoteId: 9876,
  };
  dispatch(quoteResponseSuccess(quote));
};

export const buyRequest = (pairId, quantity) => requestQuote(pairId, quantity, BUY);
export const sellRequest = (pairId, quantity) => requestQuote(pairId, quantity, SELL);
