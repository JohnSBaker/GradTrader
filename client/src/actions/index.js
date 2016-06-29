import * as api from '../api/apiService';

export const GET_VALID_PAIRS_REQUEST = 'GET_VALID_PAIRS_REQUEST';
export const GET_VALID_PAIRS_SUCCESS = 'GET_VALID_PAIRS_SUCCESS';
export const GET_VALID_PAIRS_FAILURE = 'GET_VALID_PAIRS_FAILURE';
export const POPULATE_TILES = 'POPULATE_TILES';
export const ADD_PRICE = 'ADD_PRICE';
export const ADD_PRICES = 'ADD_PRICES';

export const getValidPairs = () => (dispatch) => {
  dispatch({
    type: GET_VALID_PAIRS_REQUEST,
  });

  return api
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
