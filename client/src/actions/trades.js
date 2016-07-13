import * as api from '../api/apiService';
import { getQuote } from '../reducers/quotes';
import { clearQuote } from './quotes';

export const ADD_TRADE = 'ADD_TRADE';

export const addTrade = (trade) => ({
  type: ADD_TRADE,
  ...trade,
});

export const confirmTrade = (pairId) => (dispatch, getState) => {
  const quote = getQuote(getState().quotes, pairId);

  api
    .confirmTrade(quote.quoteId)
    .then(() => dispatch(clearQuote(pairId)));
};
