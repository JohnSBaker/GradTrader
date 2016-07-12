import { expect } from 'chai';
import React from 'react';
import { shallow } from 'enzyme';
import Chart from '../../src/components/chart/Chart';

describe('Chart', () => {
  it('should contain correct css class', () => {
    const wrapper = shallow(<Chart pair={{}} currentPrice={{}} />);
    expect(wrapper.hasClass('chart')).to.be.true;
  });
});
