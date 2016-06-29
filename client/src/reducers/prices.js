import { ADD_PRICE, ADD_PRICES } from '../actions';

/**
 * Transforms the new prices from an array to an object map
 * @param  {Array} newPrices Array in the form [{ pair, ask, askDelta, bid, bidDelta }, ...]
 * @return {Object} object map in the form { pair: { ask, askDelta, bid, bidDelta }, ...}
 */
const transformNewPrices = (newPrices) => (
  newPrices.reduce((accumulatorObj, newPrice) => ({
    ...accumulatorObj,
    [newPrice.pair]: {
      ask: newPrice.ask,
      askDelta: newPrice.askDelta,
      bid: newPrice.bid,
      bidDelta: newPrice.bidDelta,
    },
  }), {})
);

const prices = (state = {}, action) => {
  switch (action.type) {
    case ADD_PRICE:
      return {
        ...state,
        [action.pair]: {
          ask: action.ask,
          askDelta: action.askDelta,
          bid: action.bid,
          bidDelta: action.bidDelta,
        },
      };
    case ADD_PRICES:
      return {
        ...state,
        ...transformNewPrices(action.prices),
      };
    default:
      return state;
  }
};

export const getPrice = (state = {}, type) => {
  if (type === 'bid') {
    return {
      price: state.bid,
      delta: state.bidDelta,
    };
  } else if (type === 'ask') {
    return {
      price: state.ask,
      delta: state.askDelta,
    };
  }
  return state;
};


export default prices;
