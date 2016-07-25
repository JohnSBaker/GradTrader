import React from 'react';
import 'components/header/Header.scss';
import AccountDropdownIcon from 'svg/AccountDropdown.svg';
import SettingsIcon from 'svg/Settings.svg';
import LogoIcon from 'svg/Logo.svg';

const Header = () => (
  <div className="header">
    <span className="header-title-container">
      <span className="header-logo">
        <LogoIcon />
      </span>
      <span className="header-title">
        GradTrader
      </span>
    </span>
    <span className="header-account">
      <span className="header-account-settings">
        <SettingsIcon />
      </span>
      <span className="header-account-username">SLogic</span>
      <span className="header-account-dropdown">
        <AccountDropdownIcon />
      </span>
    </span>
  </div>
);

export default Header;
