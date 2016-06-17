import React from 'react';
import TileGrid from './tileGrid/TileGridContainer';
import Header from './header/Header';
import Chart from './chart/ChartContainer';
import Blotter from './blotter/BlotterContainer';
import './App.scss';

const App = () => (
  <div id="app">
    <Header />
    <div className="main-container">
      <TileGrid />
      <Chart />
    </div>
    <Blotter />
  </div>
);

export default App;
