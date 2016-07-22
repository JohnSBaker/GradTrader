import { applyMiddleware, compose, createStore } from 'redux';
import thunk from 'redux-thunk';
import createLogger from 'redux-logger';
import DevTools from 'components/devTools/DevTools';
import rootReducer from 'reducers';

const configureStore = () => {
  const middlewares = [thunk, createLogger()];

  const store = createStore(
    rootReducer,
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
