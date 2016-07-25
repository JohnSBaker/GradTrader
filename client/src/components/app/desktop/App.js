import React from 'react';
import TileGrid from 'components/tileGrid/TileGridContainer';
import Header from 'components/header/Header';
import Chart from 'components/chart/ChartContainer';
import Blotter from 'components/blotter/desktop/BlotterContainer';
import 'components/app/desktop/App.scss';

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
