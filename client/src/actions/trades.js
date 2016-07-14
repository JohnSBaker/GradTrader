import * as api from '../api/apiService';
import * as subscription from '../api/subscriptionService';
import { getQuote } from '../reducers/quotes';
import { clearQuote } from './quotes';
import { getUser } from '../reducers/user';

export const ADD_TRADE = 'ADD_TRADE';
export const ADD_TRADES = 'ADD_TRADES';
export const REQUEST_TRADES_FAILURE = 'REQUEST_TRADES_FAILURE';

export const addTrade = (trade) => ({
  type: ADD_TRADE,
  ...trade,
});

export const addTrades = (trades) => ({
  type: ADD_TRADES,
  trades,
});

const requestTradesFailure = (error) => ({
  type: REQUEST_TRADES_FAILURE,
  message: error.message || 'An unknown error occurred',
});


export const requestPreviousTrades = () => (dispatch, getState) => {
  const state = getState();
  const { id } = getUser(state);
  api
    .requestPreviousTrades(id)
    .then((trades) => dispatch(addTrades(trades)))
    .catch((error) => dispatch(requestTradesFailure(error)));
};

export const subscribeTrade = () => (_, getState) => {
  const { id } = getUser(getState());
  subscription.subscribeTrade(id);
};

export const unsubscribeTrade = () => (_, getState) => {
  const { id } = getUser(getState());
  subscription.unsubscribeTrade(id);
};

export const confirmTrade = (pairId) => (dispatch, getState) => {
  const quote = getQuote(getState().quotes, pairId);

  api
    .confirmTrade(quote.quoteId)
    .then(() => dispatch(clearQuote(pairId)));
};

export const setupTradeFeed = () => (dispatch) => {
  subscription.setCallback('trades', ({ data }) => {
    dispatch(addTrades(data));
  });
};
