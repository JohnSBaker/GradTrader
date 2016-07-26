import React from 'react';
import { expect } from 'chai';
import { shallow } from 'enzyme';
import PriceTicker from '../../src/components/priceTicker/PriceTicker';

describe('PriceTicker', () => {
  it('should format price correctly', () => {
    const wrapper = shallow(<PriceTicker price={'1.23789'} delta={1} stacked />);
    expect(wrapper.find('.price-ticker-start')).to.have.length(1);
    expect(wrapper.find('.price-ticker-main')).to.have.length(1);
    expect(wrapper.find('.price-ticker-pip')).to.have.length(1);
    expect(wrapper.find('.price-ticker-pip-delta')).to.have.length(1);
    expect(wrapper.find('.price-ticker-start').text()).to.equal('1.23');
    expect(wrapper.find('.price-ticker-main').text()).to.equal('78');
    expect(wrapper.find('.price-ticker-pip').text()).to.equal('9');
    expect(wrapper.find('.price-ticker-pip-delta').find('PositiveArrow')).to.have.length(1);
  });
});
