const path = require('path');
const projectRootPath = path.resolve(__dirname, '../');
const webpack = require('webpack');
const nodeExternals = require('webpack-node-externals');
const config = require('./shared.config');

config.plugins = [
  new webpack.DefinePlugin({
    'process.env': {
      NODE_ENV: JSON.stringify('test'),
    },
  }),
];

config.module.loaders.unshift({
  test: /\.js$/,
  loaders: ['babel'],
  include: [
    path.resolve(projectRootPath, 'src/'),
    path.resolve(projectRootPath, 'test/'),
  ],
});

config.target = 'node';
config.externals = [nodeExternals()];

module.exports = config;
