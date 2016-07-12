import React from 'react';
import { render } from 'react-dom';
import App from './components/App';
import { Provider } from 'react-redux';
import configureStore from './store/configureStore';
import { openSocketConnection } from './api/subscriptionService';
import { setupPriceFeed } from './actions/prices';
import './index.html';

const store = configureStore();
openSocketConnection();
store.dispatch(setupPriceFeed());

render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById('root')
);
