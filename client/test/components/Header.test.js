import React from 'react';
import { expect } from 'chai';
import { shallow } from 'enzyme';
import Header from '../../src/components/header/Header';

describe('Header', () => {
  it('should contain correct css class', () => {
    const wrapper = shallow(<Header />);
    expect(wrapper.hasClass('header')).to.be.true;
  });

  it('should contain 7 spans', () => {
    const wrapper = shallow(<Header />);
    expect(wrapper.find('span')).to.have.length(7);
  });
});
