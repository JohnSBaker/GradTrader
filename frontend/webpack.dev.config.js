const webpack = require('webpack');
const sharedConfig = require('./webpack.shared.config');

const devConfig = {
  entry: [
    './src/main',
    'webpack-dev-server/client?http://localhost:3000',
    'webpack/hot/only-dev-server'
  ],
  output: {
    publicPath: '/',
    path: '../backend/src/main/resources/assets',
    filename: 'main.js'
  },
  plugins: [
    new webpack.HotModuleReplacementPlugin(),
    new webpack.ProvidePlugin({
      'es6-promise': 'es6-promise',
      'fetch': 'imports?this=>global!exports?global.fetch!whatwg-fetch'
    })
  ],
  devtool: 'source-map',
  debug: true,
  devServer: {
    contentBase: './',
    port: 3000,
    proxy: {
      '/api/*': 'http://localhost:8080/'
    }
  }

};

module.exports = Object.assign({},
  sharedConfig,
  devConfig
);
