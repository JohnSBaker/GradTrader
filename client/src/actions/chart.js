export const SET_RESOLUTION = 'SET_RESOLUTION';
export const SET_INTERVAL = 'SET_INTERVAL';

export const setResolution = (pairId, resolution) => ({
  type: SET_RESOLUTION,
  pairId,
  resolution,
});

export const setInterval = (pairId, from, to) => ({
  type: SET_INTERVAL,
  pairId,
  from,
  to,
});
