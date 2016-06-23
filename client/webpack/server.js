const express = require('express');
const webpack = require('webpack');
const config = require('./dev.config');
const proxy = require('http-proxy-middleware');
const hotMiddleware = require('webpack-hot-middleware');
const webpackMiddleware = require('webpack-dev-middleware');

const app = express();
const compiler = webpack(config);

app.use(proxy('/api', { target: 'http://localhost:8080', changeOrigin: true }));

app.use(webpackMiddleware(compiler, {
  noInfo: true,
  publicPath: config.output.publicPath,
}));

app.use(hotMiddleware(compiler));

app.listen(3000, 'localhost', (err) => {
  if (err) {
    console.log(err);
    return;
  }

  console.log('Listening at http://localhost:3000');
});
