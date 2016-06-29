import { combineReducers } from 'redux';
import pairs from './pairs';
import tiles from './tiles';
import prices from './prices';

const rootReducer = combineReducers({
  pairs,
  tiles,
  prices,
});

export default rootReducer;
