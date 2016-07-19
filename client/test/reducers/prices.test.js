import { expect } from 'chai';
import prices, { getFormattedPrice } from '../../src/reducers/prices';
import { ADD_PRICES } from '../../src/actions/prices';

describe('Prices Reducer - ADD_PRICES', () => {
  it('can add price', () => {
    expect(
      prices(undefined, {
        type: ADD_PRICES,
        prices: [
          {
            pairId: 'abcdfe',
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
          pairId: 'abcdfe',
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
          pairId: 'abcdfe',
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
          pairId: 'USDEUR',
          ask: 0.500,
          bid: 0.745,
        },
        {
          pairId: 'USDGBP',
          bid: 3.12,
          ask: 4.0,
          bidDelta: 1,
          askDelta: 1,
        },
        {
          pairId: 'EURGBP',
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

describe('Prices Reducer - getFormattedPrice selector', () => {
  it('can get bid price using selector', () => {
    const state = {
      prices: {
        GDPUSD: {
          bid: 2512345,
          bidDelta: 1,
          ask: 2200001,
          askDelta: -1,
        },
      },
    };
    const type = 'bid';

    expect(getFormattedPrice(state, { id: 'GDPUSD', decimals: 5 }, type)).to.deep.equal({
      price: '25.12345',
      delta: 1,
    });
  });

  it('can get ask price using selector', () => {
    const state = {
      prices: {
        GDPUSD: {
          bid: 2512345,
          bidDelta: 1,
          ask: 2200001,
          askDelta: -1,
        },
      },
    };
    const type = 'ask';

    expect(getFormattedPrice(state, { id: 'GDPUSD', decimals: 5 }, type)).to.deep.equal({
      price: '22.00001',
      delta: -1,
    });
  });
});
