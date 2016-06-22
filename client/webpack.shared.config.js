const path = require('path');

module.exports = {
  output: {
    publicPath: '/',
    path: 'dist',
    filename: 'main.js',
  },
  module: {
    loaders: [
      {
        test: /\.js$/,
        exclude: /node_modules/,
        loaders: ['react-hot', 'babel'],
      },
      {
        test: /\.html$/,
        include: path.resolve(__dirname, 'src/'),
        loader: 'file-loader?name=[name].[ext]',
      },
      {
        test: /\.scss$/,
        exclude: /node_modules/,
        loader: 'style!css!sass',
      },
    ],
  },
};
