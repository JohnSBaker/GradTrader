import { combineReducers } from 'redux';
import { routerReducer } from 'react-router-redux';
import pairs from 'reducers/pairs';
import trades, { blotterSort } from 'reducers/trades';
import prices from 'reducers/prices';
import quotes from 'reducers/quotes';
import selectedTile from 'reducers/selectedTile';
import user from 'reducers/user';
import history from 'reducers/history';
import chart from 'reducers/chart';

const rootReducer = combineReducers({
  routing: routerReducer,
  pairs,
  prices,
  quotes,
  trades,
  blotterSort,
  selectedTile,
  user,
  history,
  chart,
});

export default rootReducer;
