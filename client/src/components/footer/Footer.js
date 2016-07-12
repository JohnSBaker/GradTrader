import React from 'react';
import { Link } from 'react-router';
import classnames from 'classnames';
import './Footer.scss';
import MyTradesIcon from 'svg/MyTrades.svg';

const buttons = [
  { location: '/pairs', label: 'Buy/Sell', icon: MyTradesIcon },
  { location: '/trades', label: 'My Trades', icon: MyTradesIcon },
];
const Footer = ({ location }) => {
  const content = buttons.map((button, index) => {
    const className = classnames('footer-button', {
      'footer-button-selected': location === button.location,
    });
    return (
      <Link to={button.location} className={className} key={index}>
        <span className="footer-icon">
          <button.icon />
        </span>
        <span className="footer-title">{button.label}</span>
      </Link>
    );
  });

  return <div className="footer">{content}</div>;
};

Footer.propTypes = {
  location: React.PropTypes.string.isRequired,
};

export default Footer;
