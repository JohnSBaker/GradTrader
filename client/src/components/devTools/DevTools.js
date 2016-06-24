if (process.env.NODE_ENV === 'production') {
  module.exports = null;
} else {
  // eslint-disable-next-line global-require
  module.exports = require('./DevTools.dev').default;
}
