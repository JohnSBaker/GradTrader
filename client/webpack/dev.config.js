const path = require('path');
const webpack = require('webpack');
const config = require('./shared.config');
const projectRootPath = path.resolve(__dirname, '../');

config.devtool = 'inline-source-map';
config.entry.unshift('webpack-hot-middleware/client');
config.output.publicPath = 'http://localhost:3000/';
config.plugins = [
  new webpack.HotModuleReplacementPlugin(),
  new webpack.NoErrorsPlugin(),
  new webpack.DefinePlugin({
    'process.env': {
      NODE_ENV: JSON.stringify('development'),
    },
  }),
];

config.module.loaders.unshift({
  test: /\.js$/,
  exclude: /node_modules/,
  loaders: ['react-hot', 'babel?cacheDirectory'],
  include: [
    path.resolve(projectRootPath, 'src/'),
  ],
});

module.exports = config;
