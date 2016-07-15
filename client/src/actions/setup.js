import { fakeLogin } from './user';
import { setupPriceFeed } from './prices';
import { setupTradeFeed } from './trades';

export const setup = () => (dispatch) => {
  dispatch(fakeLogin());
  dispatch(setupPriceFeed());
  dispatch(setupTradeFeed());
};
