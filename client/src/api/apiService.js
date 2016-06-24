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
