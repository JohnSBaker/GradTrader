import { expect } from 'chai';
import React from 'react';
import { shallow } from 'enzyme';
import Chart from '../../src/components/chart/Chart';

describe('Chart', () => {
  it('should contain text', () => {
    const wrapper = shallow(<Chart />);
    expect(wrapper.text()).to.contain('chart will go here');
  });
});
