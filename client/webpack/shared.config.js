const path = require('path');
const projectRootPath = path.resolve(__dirname, '../');

module.exports = {
  entry: {
    desktop: ['./src/desktop'],
    mobile: ['./src/mobile'],
  },
  output: {
    path: path.join(projectRootPath, 'dist'),
    filename: '[name].js',
  },
  resolve: {
    root: path.resolve('./src'),
    extensions: ['', '.js'],
  },
  module: {
    loaders: [{
      test: /\.html$/,
      include: path.resolve(projectRootPath, 'src/'),
      loader: 'file-loader?name=[name].[ext]',
    },
    {
      test: /\.scss$/,
      exclude: /node_modules/,
      include: path.resolve(projectRootPath, 'src/'),
      loader: 'style!css!sass',
    },
    {
      test: /\.svg$/,
      exclude: /node_modules/,
      include: path.resolve(projectRootPath, 'src/'),
      loader: 'babel!svg-react',
    }],
  },
};
