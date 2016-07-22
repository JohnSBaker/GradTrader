import * as api from 'api/apiService';

export const GET_VALID_PAIRS_SUCCESS = 'GET_VALID_PAIRS_SUCCESS';
export const GET_VALID_PAIRS_FAILURE = 'GET_VALID_PAIRS_FAILURE';

const validPairsRequestSuccessful = (pairs) => ({
  type: GET_VALID_PAIRS_SUCCESS,
  pairs,
});

const validPairsRequestFailure = (error) => ({
  type: GET_VALID_PAIRS_FAILURE,
  message: error.message || 'An unknown error occurred',
});

export const getValidPairs = () => (dispatch) => {
  api
    .getValidPairs()
    .then((pairs) => dispatch(validPairsRequestSuccessful(pairs)))
    .catch((error) => dispatch(validPairsRequestFailure(error)));
};
