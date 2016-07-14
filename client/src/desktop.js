import React from 'react';
import { render } from 'react-dom';
import { Provider } from 'react-redux';
import App from './components/app/desktop/App';
import configureStore from './store/configureStore';
import { openSocketConnection } from './api/subscriptionService';
import { setupPriceFeed } from './actions/prices';
import { setupTradeFeed } from './actions/trades';
import { fakeLogin } from './actions/user';
import './desktop.html';

const store = configureStore();
openSocketConnection();
store.dispatch(fakeLogin());
store.dispatch(setupPriceFeed());
store.dispatch(setupTradeFeed());

render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById('root')
);
