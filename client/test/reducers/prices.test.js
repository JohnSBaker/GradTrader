import { expect } from 'chai';
import prices, { getPrice } from '../../src/reducers/prices';
import { ADD_PRICE, ADD_PRICES } from '../../src/actions';

describe('Prices Reducer - ADD_PRICE', () => {
  it('can add price', () => {
    expect(
      prices(undefined, {
        type: ADD_PRICE,
        pair: 'abcdfe',
        bid: 123.345,
        ask: 444.344,
        bidDelta: 1,
        askDelta: 1,

      })
    )
    .to.deep.equal({
      abcdfe: {
        bid: 123.345,
        ask: 444.344,
        bidDelta: 1,
        askDelta: 1,
      },
    });
  });

  it('overriding a price updates it and sets correct deltas', () => {
    let state = undefined;

    const action1 = {
      type: ADD_PRICE,
      pair: 'abcdfe',
      bid: 123.300,
      ask: 445.500,
      bidDelta: 1,
      askDelta: -1,
    };

    const action2 = {
      type: ADD_PRICE,
      pair: 'abcdfe',
      bid: 223.540,
      ask: 444.0,
      bidDelta: -1,
      askDelta: -1,
    };

    state = prices(state, action1);
    state = prices(state, action2);
    expect(state).to.deep.equal({
      abcdfe: {
        bid: 223.540,
        ask: 444.0,
        bidDelta: -1,
        askDelta: -1,
      },
    });
  });

  it('can add multiple prices consecutively', () => {
    let state = undefined;

    const action1 = {
      type: ADD_PRICE,
      pair: 'USDEUR',
      bid: 0.745,
      ask: 0.500,
    };

    const action2 = {
      type: ADD_PRICE,
      pair: 'USDGBP',
      bid: 3.12,
      ask: 4.0,
      bidDelta: 1,
      askDelta: 1,
    };

    const action3 = {
      type: ADD_PRICE,
      pair: 'EURGBP',
      bid: 1.543,
      ask: 2.0,
    };

    state = prices(state, action1);
    state = prices(state, action2);
    state = prices(state, action3);
    expect(state).to.deep.equal({
      USDEUR: {
        bid: 0.745,
        bidDelta: undefined,
        ask: 0.500,
        askDelta: undefined,
      },
      EURGBP: {
        bid: 1.543,
        bidDelta: undefined,
        ask: 2,
        askDelta: undefined,
      },
      USDGBP: {
        bid: 3.12,
        bidDelta: 1,
        ask: 4,
        askDelta: 1,
      },
    });
  });
});

describe('Prices Reducer - ADD_PRICES', () => {
  it('can add price', () => {
    expect(
      prices(undefined, {
        type: ADD_PRICES,
        prices: [
          {
            pair: 'abcdfe',
            bid: 123.345,
            ask: 444.344,
            bidDelta: 1,
            askDelta: 1,
          },
        ],

      })
    )
    .to.deep.equal({
      abcdfe: {
        bid: 123.345,
        ask: 444.344,
        bidDelta: 1,
        askDelta: 1,
      },
    });
  });

  it('overriding a price updates it and sets correct deltas', () => {
    let state = undefined;

    const action1 = {
      type: ADD_PRICES,
      prices: [
        {
          pair: 'abcdfe',
          bid: 123.300,
          ask: 445.500,
          bidDelta: 1,
          askDelta: -1,
        },
      ],
    };

    const action2 = {
      type: ADD_PRICES,
      prices: [
        {
          pair: 'abcdfe',
          bid: 223.540,
          ask: 444.0,
          bidDelta: -1,
          askDelta: -1,
        },
      ],
    };

    state = prices(state, action1);
    state = prices(state, action2);
    expect(state).to.deep.equal({
      abcdfe: {
        bid: 223.540,
        ask: 444.0,
        bidDelta: -1,
        askDelta: -1,
      },
    });
  });

  it('can add multiple prices all at once', () => {
    let state = undefined;

    const action1 = {
      type: ADD_PRICES,
      prices: [
        {
          pair: 'USDEUR',
          ask: 0.500,
          bid: 0.745,
        },
        {
          pair: 'USDGBP',
          bid: 3.12,
          ask: 4.0,
          bidDelta: 1,
          askDelta: 1,
        },
        {
          pair: 'EURGBP',
          bid: 1.543,
          ask: 2.0,
        },
      ],
    };

    state = prices(state, action1);
    expect(state).to.deep.equal({
      USDEUR: {
        bid: 0.745,
        bidDelta: undefined,
        ask: 0.500,
        askDelta: undefined,
      },
      EURGBP: {
        bid: 1.543,
        bidDelta: undefined,
        ask: 2,
        askDelta: undefined,
      },
      USDGBP: {
        bid: 3.12,
        bidDelta: 1,
        ask: 4,
        askDelta: 1,
      },
    });
  });
});

describe('Prices Reducer - getPrice selector', () => {
  it('can get bid price using selector', () => {
    const state = {
      bid: 25.12345,
      bidDelta: 1,
      ask: 22,
      askDelta: -1,
    };
    const type = 'bid';

    expect(getPrice(state, type)).to.deep.equal({
      price: 25.12345,
      delta: 1,
    });
  });

  it('can get ask price using selector', () => {
    const state = {
      bid: 25.12345,
      bidDelta: 1,
      ask: 22.00001,
      askDelta: -1,
    };
    const type = 'ask';

    expect(getPrice(state, type)).to.deep.equal({
      price: 22.00001,
      delta: -1,
    });
  });
});
