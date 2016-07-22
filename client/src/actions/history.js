import * as api from 'api/apiService';

export const ADD_TRADE_HISTORY = 'ADD_TRADE_HISTORY';
export const REQUEST_TRADE_HISTORY_FAILURE = 'REQUEST_TRADE_HISTORY_FAILURE';

const addTradeHistory = (history, pairId) => ({
  type: ADD_TRADE_HISTORY,
  history,
  pairId,
});

const requestTradeHistoryFailure = (error) => ({
  type: REQUEST_TRADE_HISTORY_FAILURE,
  message: error.message || 'An unknown error occurred',
});

export const requestTradeHistory = (pairId, resolution, from, to) => (dispatch) => {
  api
    .requestTradeHistory({ pairId, resolution, from, to })
    .then((history) => dispatch(addTradeHistory(history, pairId)))
    .catch((error) => dispatch(requestTradeHistoryFailure(error)));
};
