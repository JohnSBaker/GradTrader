const webpack = require('webpack');
const sharedConfig = require('./webpack.shared.config');

const devConfig = {
  entry: [
    'babel-polyfill',
    './src/main',
    'webpack-dev-server/client?http://0.0.0.0:8000',
    'webpack/hot/only-dev-server',
  ],
  plugins: [
    new webpack.HotModuleReplacementPlugin(),
    new webpack.ProvidePlugin({
      'es6-promise': 'es6-promise',
      fetch: 'imports?this=>global!exports?global.fetch!whatwg-fetch',
    }),
  ],
  devtool: 'source-map',
  debug: true,
  devServer: {
    contentBase: './',
    host: '0.0.0.0',
    colors: true,
    port: 8000,
    proxy: {
      '/api/*': 'http://0.0.0.0:8080/',
    },
  },
};

module.exports = Object.assign({}, sharedConfig, devConfig);
