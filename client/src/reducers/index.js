import { combineReducers } from 'redux';
import pairs from './pairs';
import tiles from './tiles';
import prices from './prices';
import selectedTile from './selectedTile';

const rootReducer = combineReducers({
  pairs,
  tiles,
  prices,
  selectedTile,
});

export default rootReducer;
