import { applyMiddleware, compose, createStore } from 'redux';
import thunk from 'redux-thunk';
import createLogger from 'redux-logger';
import DevTools from '../components/devTools/DevTools';
import rootReducer from '../reducers';

const configureStore = () => {
  const middlewares = [thunk, createLogger()];

  const store = createStore(
    rootReducer,
    {
      trades: [{
        tradeId: 26374857,
        timestamp: 1467565719,
        time: '18:09',
        date: '03 JUL 2016',
        currency: 'GBP | USD',
        direction: 'Buy',
        quantity: 500000,
        rate: 1.11556,
        portfolio: 'Portfolio1',
        user: 'SLogic',
      }],
    },
    compose(
      applyMiddleware(...middlewares),
      DevTools.instrument(),
    )
  );

  if (module.hot) {
    // Enable Webpack hot module replacement for reducers
    module.hot.accept('../reducers', () => {
      // eslint-disable-next-line global-require
      const nextRootReducer = require('../reducers').default;
      store.replaceReducer(nextRootReducer);
    });
  }

  return store;
};

export default configureStore;
