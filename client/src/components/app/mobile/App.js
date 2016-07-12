import React, { PropTypes } from 'react';
import Footer from 'components/footer/Footer';
import './App.scss';

const App = ({ children, location }) => (
  <div id="app">
    <div className="main-container">
      {children}
    </div>
    <div className="footer-container">
      <Footer location={location.pathname} />
    </div>
  </div>
);

App.propTypes = {
  children: PropTypes.object.isRequired,
  location: PropTypes.object.isRequired,
};

export default App;
