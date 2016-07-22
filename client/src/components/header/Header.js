import React from 'react';
import 'components/header/Header.scss';
import AccountDropdownIcon from 'svg/AccountDropdown.svg';
import SettingsIcon from 'svg/Settings.svg';
import LogoIcon from 'svg/Logo.svg';

const Header = () => (
  <div className="header">
    <span className="header-title-container">
      <LogoIcon />
      <span className="header-title">GradTrader</span>
    </span>
    <span className="account">
      <span className="account-settings">
        <SettingsIcon />
      </span>
      <span className="username">SLogic</span>
      <span className="dropdown">
        <AccountDropdownIcon />
      </span>
    </span>
  </div>
);

export default Header;
