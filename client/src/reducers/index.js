import { combineReducers } from 'redux';
import { routerReducer } from 'react-router-redux';
import pairs from './pairs';
import trades from './trades';
import prices from './prices';
import quotes from './quotes';
import selectedTile from './selectedTile';

const rootReducer = combineReducers({
  routing: routerReducer,
  pairs,
  prices,
  quotes,
  trades,
  selectedTile,
});

export default rootReducer;
