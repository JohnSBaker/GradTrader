import { combineReducers } from 'redux';
import pairs from './pairs';
import tiles from './tiles';
import prices from './prices';
import quotes from './quotes';
import selectedTile from './selectedTile';

const rootReducer = combineReducers({
  pairs,
  tiles,
  prices,
  quotes,
  selectedTile,
});

export default rootReducer;
