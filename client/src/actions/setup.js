
import { fakeLogin } from './user';
import { setupPriceFeed } from './prices';
import { setupTradeFeed, requestPreviousTrades, subscribeTrade } from './trades';

export const setup = () => (dispatch) => {
  dispatch(fakeLogin());
  dispatch(setupPriceFeed());
  dispatch(setupTradeFeed());
  dispatch(requestPreviousTrades());
  dispatch(subscribeTrade());
};
