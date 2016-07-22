import { fakeLogin } from 'actions/user';
import { setupPriceFeed } from 'actions/prices';
import { setupTradeFeed } from 'actions/trades';

export const setup = () => (dispatch) => {
  dispatch(fakeLogin());
  dispatch(setupPriceFeed());
  dispatch(setupTradeFeed());
};
