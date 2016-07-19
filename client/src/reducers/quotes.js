import { QUOTE_RESPONSE_SUCCESS, CLEAR_QUOTE } from 'actions/quotes';
import formatPrice from 'utils/priceFormatter';
import { getPair } from 'reducers/pairs';

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
    case CLEAR_QUOTE: {
      const newState = Object.assign({}, state);
      delete newState[action.pairId];
      return newState;
    }
    default:
      return state;
  }
};

export const getQuotes = (state = {}) => (state.quotes);

export const getQuote = (state = {}, pairId) => (getQuotes(state)[pairId]);

export const getFormattedQuote = (state = {}, pairId) => {
  const quote = getQuotes(state)[pairId];
  return {
    quantity: quote.quantity,
    direction: quote.direction,
    formattedPrice: formatPrice(quote.price, getPair(state, pairId).decimals),
    expires: quote.expires,
    quoteId: quote.quoteId,
  };
};

export default quotes;
