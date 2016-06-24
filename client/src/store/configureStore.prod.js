import { applyMiddleware, compose, createStore } from 'redux';
import thunk from 'redux-thunk';
import rootReducer from '../reducers';

const configureStore = () => {
  const middlewares = [thunk];

  const store = createStore(
    rootReducer,
    compose(
      applyMiddleware(...middlewares)
    )
  );

  return store;
};

export default configureStore;
