import React from 'react';
import TileGrid from 'components/tileGrid/TileGridContainer';
import Header from 'components/header/Header';
import Chart from 'components/chart/ChartContainer';
import Blotter from 'components/blotter/desktop/BlotterContainer';
import DevTools from 'components/devTools/DevTools';
import './App.scss';

const App = () => (
  <div id="app">
    <Header />
    <div className="main-container">
      <TileGrid />
      <Chart />
    </div>
    <Blotter />
    {DevTools && <DevTools />}
  </div>
);

export default App;
