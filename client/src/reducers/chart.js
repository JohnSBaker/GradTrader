import { SET_RESOLUTION, SET_INTERVAL } from 'actions/chart';
import moment from 'moment';

const updatePair = (state, pairId, update) =>
  Object.assign({}, state, {
    [pairId]: Object.assign({}, state[pairId], update),
  });

const chart = (state = {}, action) => {
  switch (action.type) {
    case SET_RESOLUTION:
      return updatePair(state, action.pairId, {
        resolution: action.resolution,
      });
    case SET_INTERVAL:
      return updatePair(state, action.pairId, {
        interval: {
          from: action.from,
          to: action.to,
        },
      });
    default:
      return state;
  }
};

const defaultResolution = 1000 * 60 * 60 * 12;
const defaultInterval = {
  from: moment()
    .startOf('day')
    .subtract(14, 'day')
    .valueOf(),
  to: moment()
    .endOf('day')
    .valueOf(),
};

export const getChart = (state = {}) => state.chart;

export const getResolutionOrDefault = (state = {}, pairId) => (getChart(state)[pairId] || {}).resolution || defaultResolution;

export const getIntervalOrDefault = (state = {}, pairId) => (getChart(state)[pairId] || {}).interval || defaultInterval;

export default chart;
