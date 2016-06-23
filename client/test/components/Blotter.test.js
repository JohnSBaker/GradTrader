import React from 'react';
import { expect } from 'chai';
import { shallow } from 'enzyme';
import Blotter from '../../src/components/blotter/Blotter';

describe('Blotter', () => {
  it('should contain text', () => {
    const wrapper = shallow(<Blotter />);
    expect(wrapper.text()).to.contain('blotter will go here');
  });
});
