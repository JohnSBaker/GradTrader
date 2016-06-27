import { combineReducers } from 'redux';
import pairs from './pairs';
import tiles from './tiles';

const rootReducer = combineReducers({
  pairs,
  tiles,
});

export default rootReducer;
