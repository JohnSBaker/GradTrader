import { QUOTE_RESPONSE_SUCCESS, QUOTE_EXPIRED } from '../actions';


const quotes = (state = {}, action) => {
  switch (action.type) {
    case QUOTE_RESPONSE_SUCCESS:
      return {
        ...state,
        [action.pairName]: {
          amount: action.amount,
          purchaseType: action.purchaseType,
          price: action.price,
          timestamp: action.timestamp,
        },
      };
    case QUOTE_EXPIRED: {
      const newState = Object.assign({}, state);
      delete newState[action.pairName];
      return newState;
    }
    default:
      return state;
  }
};

export const getQuote = (state = {}, pairName) => (state[pairName]);

export default quotes;
