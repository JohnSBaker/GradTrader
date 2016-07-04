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

export const getQuote = (pair, amount, type) => (
  fetch('api/quote', {
    method: 'post',
    body: {
      amount,
      pair,
      type,
    },
  })
  .then(checkStatus)
  .then(parseJSON)
);
