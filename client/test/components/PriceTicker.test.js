import React from 'react';
import { expect } from 'chai';
import { shallow } from 'enzyme';
import PriceTicker from '../../src/components/priceTicker/PriceTicker';

describe('PriceTicker', () => {
  it('should format price correctly', () => {
    const wrapper = shallow(<PriceTicker price={'1.23789'} delta={1} stacked />);
    expect(wrapper.find('.start')).to.have.length(1);
    expect(wrapper.find('.main')).to.have.length(1);
    expect(wrapper.find('.pip')).to.have.length(1);
    expect(wrapper.find('.pip-delta')).to.have.length(1);
    expect(wrapper.find('.start').text()).to.equal('1.23');
    expect(wrapper.find('.main').text()).to.equal('78');
    expect(wrapper.find('.pip').text()).to.equal('9');
    expect(wrapper.find('.pip-delta').find('PositiveArrow')).to.have.length(1);
  });
});
