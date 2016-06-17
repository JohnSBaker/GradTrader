import React from 'react';
import './Header.scss';

const dropdownArrowCode = '9662';

const Header = () => (
  <div className="header">
    <span className="header-title-container">
      <span className="logo">Q</span>
      <span className="header-title">GradTrader</span>
    </span>
    <span className="account">
      <span className="account-settings">Q</span>
      <span className="username">SLogic</span>
      <span className="dropdown">{String.fromCharCode(dropdownArrowCode)}</span>
    </span>
  </div>
);

export default Header;
