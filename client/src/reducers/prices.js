
import { ADD_PRICES } from 'actions/prices';
import formatPrice from 'utils/priceFormatter';

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
    case ADD_PRICES:
      return {
        ...state,
        ...transformNewPrices(action.prices),
      };
    default:
      return state;
  }
};

export const getPrices = (state = {}) => (state.prices);

export const getQuotePrice = (state = {}, pairId, direction) => {
  const price = getPrices(state)[pairId] || {};
  return direction === 'BUY' ? price.ask : price.bid;
};

export const getFormattedPrice = (state = {}, pair = {}, type) => {
  const price = getPrices(state)[pair.id];

  if (!price) {
    return {};
  }

  if (type === 'bid') {
    return {
      price: formatPrice(price.bid, pair.decimals),
      delta: price.bidDelta,
    };
  } else if (type === 'ask') {
    return {
      price: formatPrice(price.ask, pair.decimals),
      delta: price.askDelta,
    };
  }
  return state;
};

export default prices;
