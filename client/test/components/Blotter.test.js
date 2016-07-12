import React from 'react';
import { expect } from 'chai';
import { shallow } from 'enzyme';
import Blotter from '../../src/components/blotter/desktop/Blotter';

describe('Blotter', () => {
  it('should contain correct css class', () => {
    const wrapper = shallow(<Blotter trades={[]} />);
    expect(wrapper.hasClass('blotter')).to.be.true;
  });
});
