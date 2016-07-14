import 'whatwg-fetch';

const checkStatus = (response) => {
  if (response.status >= 200 && response.status < 300) {
    return response;
  }
  const error = new Error(response.statusText);
  error.response = response;
  throw error;
};

const parseJSON = (response) => (response.json());

export const getValidPairs = () => (
  fetch('api/pair')
  .then(checkStatus)
  .then(parseJSON)
);

export const requestQuote = (userId, portfolioId, pairId, quantity, direction, indicativePrice) => (
  fetch('api/quote', {
    method: 'post',
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      userId,
      portfolioId,
      pairId,
      quantity,
      direction,
      indicativePrice,
    }),
  })
  .then(checkStatus)
  .then(parseJSON)
);

export const confirmTrade = (quoteId) => (
  fetch('api/trade', {
    method: 'post',
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      quoteId,
    }),
  })
  .then(checkStatus)
  .then(parseJSON)
);

export const requestPreviousTrades = (userId) => (
  fetch(`api/trade/${userId}`, {
    method: 'get',
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/json',
    },
  })
  .then(checkStatus)
  .then(parseJSON)
);
