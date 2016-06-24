import * as api from '../api/apiService';

export const GET_VALID_PAIRS_REQUEST = 'GET_VALID_PAIRS_REQUEST';
export const GET_VALID_PAIRS_SUCCESS = 'GET_VALID_PAIRS_SUCCESS';
export const GET_VALID_PAIRS_FAILURE = 'GET_VALID_PAIRS_FAILURE';

export const getValidPairs = () => (dispatch) => {
  dispatch({
    type: GET_VALID_PAIRS_REQUEST,
  });

  return api
    .getValidPairs()
    .then(
      (data) => {
        dispatch({
          type: GET_VALID_PAIRS_SUCCESS,
          response: data,
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
