import { QUOTE_RESPONSE_SUCCESS } from '../actions/quotes';
import { CANCEL_TRADE } from '../actions/trades';


const quotes = (state = {}, action) => {
  switch (action.type) {
    case QUOTE_RESPONSE_SUCCESS:
      return {
        ...state,
        [action.pairId]: {
          quantity: action.quantity,
          direction: action.direction,
          price: action.price,
          expires: action.expires,
          quoteId: action.quoteId,
        },
      };
    case CANCEL_TRADE: {
      const newState = Object.assign({}, state);
      delete newState[action.pairId];
      return newState;
    }
    default:
      return state;
  }
};

export const getQuote = (state = {}, pairId) => (state[pairId]);

export default quotes;
