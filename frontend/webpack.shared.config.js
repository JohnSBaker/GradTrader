const path = require('path');

module.exports = {
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
  }
};
