import { combineReducers } from 'redux';
import { routerReducer } from 'react-router-redux';
import pairs from 'reducers/pairs';
import trades from 'reducers/trades';
import prices from 'reducers/prices';
import quotes from 'reducers/quotes';
import selectedTile from 'reducers/selectedTile';
import user from 'reducers/user';
import history from 'reducers/history';

const rootReducer = combineReducers({
  routing: routerReducer,
  pairs,
  prices,
  quotes,
  trades,
  selectedTile,
  user,
  history,
});

export default rootReducer;
