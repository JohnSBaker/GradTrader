const path = require('path');
const webpack = require('webpack');
const config = require('./shared.config');
const projectRootPath = path.resolve(__dirname, '../');

config.plugins = [
  new webpack.DefinePlugin({
    'process.env': {
      NODE_ENV: JSON.stringify('production'),
    },
  }),
];

config.module.loaders.unshift({
  test: /\.js$/,
  loaders: ['babel'],
  include: [
    path.resolve(projectRootPath, 'src/'),
  ],
});

module.exports = config;
