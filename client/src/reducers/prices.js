import { ADD_PRICE, ADD_PRICES } from '../actions';

/**
 * Transforms the new prices from an array to an object map
 * @param  {Array} newPrices Array in the form [{ pair, ask, askDelta, bid, bidDelta }, ...]
 * @return {Object} object map in the form { pair: { ask, askDelta, bid, bidDelta }, ...}
 */
const transformNewPrices = (newPrices) => (
  newPrices.reduce((accumulatorObj, newPrice) => ({
    ...accumulatorObj,
    [newPrice.pairId]: {
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
        [action.pairId]: {
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

export const getPrice = (state = {}, pair, type) => {
  const subState = state[pair.id];

  if (!subState) {
    return state;
  }

  if (type === 'bid') {
    return {
      price: subState.bid,
      delta: subState.bidDelta,
    };
  } else if (type === 'ask') {
    return {
      price: subState.ask,
      delta: subState.askDelta,
    };
  }
  return state;
};


export default prices;
