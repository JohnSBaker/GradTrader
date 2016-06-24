import { combineReducers } from 'redux';
import pairs from './pairs';

const rootReducer = combineReducers({
  pairs,
});

export default rootReducer;
