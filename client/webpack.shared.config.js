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
        include: path.resolve(__dirname, 'src/'),
        loaders: ['react-hot', 'babel'],
      },
      {
        test: /\.html$/,
        include: path.resolve(__dirname, 'src/'),
        loader: 'file-loader?name=[name].[ext]',
      },
      {
        test: /\.scss$/,
        include: path.resolve(__dirname, 'src/assets/styles/'),
        loader: 'style!css!sass',
      },
    ],
  },
};
