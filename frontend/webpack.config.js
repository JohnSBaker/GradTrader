const webpack = require('webpack');
const path = require('path');

module.exports = {
  entry: [
    './src/main.jsx',
    './assets/styles/main.scss',
    './assets/index.html',
    'webpack-dev-server/client?http://localhost:3000',
    'webpack/hot/only-dev-server'
  ],
  output: {
    publicPath: '/',
    path: 'dist',
    filename: 'main.js'
  },
  devtool: 'source-map',
  module: {
    loaders: [
      {
        test: /\.jsx?$/,
        exclude: /node_modules/,
        include: path.resolve(__dirname, 'src/'),
        loaders: ['react-hot', 'babel']
      },
      {
        test: /\.html$/,
        include: path.resolve(__dirname, 'assets/'),
        loader: 'file-loader?name=[name].[ext]'
      },
      {
        test: /\.scss$/,
        include: path.resolve(__dirname, 'assets/styles/'),
        loader: 'style!css!sass'
      }
    ]
  },
  plugins: [
    new webpack.HotModuleReplacementPlugin()
  ],
  debug: true,
  devServer: {
    contentBase: './',
    port: 3000
  }
};
