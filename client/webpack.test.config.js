const nodeExternals = require('webpack-node-externals');
const config = require('./webpack.shared.config');

config.target = 'node';
config.externals = [nodeExternals()];

module.exports = config;
