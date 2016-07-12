import { combineReducers } from 'redux';
import pairs from './pairs';
import trades from './trades';
import prices from './prices';
import quotes from './quotes';
import selectedTile from './selectedTile';

const rootReducer = combineReducers({
  pairs,
  prices,
  quotes,
  trades,
  selectedTile,
});

export default rootReducer;
