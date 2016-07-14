import { LOGIN } from '../actions/user';

const user = (state = {}, action) => {
  switch (action.type) {
    case LOGIN:
      return {
        id: action.id,
        name: action.name,
        portfolios: action.portfolios,
        selectedPortfolioId: action.selectedPortfolioId,
      };
    default:
      return state;
  }
};

export const getUser = (state = {}) => (state.user);

export const getUserName = (state = {}) => (getUser(state).name);

export const getPortfolioName = (state = {}, id) => (getUser(state).portfolios[id]);

export default user;
