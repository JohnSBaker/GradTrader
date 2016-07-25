import * as api from 'api/apiService';

export const ADD_PRICE_HISTORY = 'ADD_PRICE_HISTORY';
export const REQUEST_PRICE_HISTORY_FAILURE = 'REQUEST_PRICE_HISTORY_FAILURE';

const addPriceHistory = (history, resolution, pairId) => ({
  type: ADD_PRICE_HISTORY,
  history,
  resolution,
  pairId,
});

const requestPriceHistoryFailure = (error) => ({
  type: REQUEST_PRICE_HISTORY_FAILURE,
  message: error.message || 'An unknown error occurred',
});

export const requestPriceHistory = (pairId, resolution, from, to) => (dispatch) => {
  api
    .requestPriceHistory({ pairId, resolution, from, to })
    .then((history) => {
      dispatch(addPriceHistory(history.map((price) => ({
        ...price,
        date: new Date(price.timestamp),
      })), resolution, pairId));
    })
    .catch((error) => {
      dispatch(requestPriceHistoryFailure(error));
    });
};
