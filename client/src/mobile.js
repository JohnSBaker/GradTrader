import React from 'react';
import { render } from 'react-dom';
import { Provider } from 'react-redux';
import { Router, Route, IndexRedirect, hashHistory } from 'react-router';
import { syncHistoryWithStore } from 'react-router-redux';
import App from './components/app/mobile/App';
import Pairs from 'routes/mobile/pairs/Pairs';
import Pair from 'routes/mobile/pair/Pair';
import Trades from 'routes/mobile/trades/Trades';
import configureStore from './store/configureStore';
import { openSocketConnection } from './api/subscriptionService';
import { setup } from './actions/setup';
import './mobile.html';

const store = configureStore();

const history = syncHistoryWithStore(hashHistory, store);

openSocketConnection();
store.dispatch(setup());

// ToDo: switch to browserHistory
render((
  <Provider store={store}>
    <Router history={history}>
      <Route path="/" component={App}>
        <IndexRedirect to="/pairs" />
        <Route path="/pairs" component={Pairs} />
        <Route path="/pair/:pairId" component={Pair} />
        <Route path="/trades" component={Trades} />
      </Route>
    </Router>
  </Provider>), document.getElementById('root')
);
