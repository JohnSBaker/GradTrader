
export const LOGIN = 'LOGIN';

export const login = ({ id, name, portfolios, selectedPortfolioId }) => ({
  type: LOGIN,
  id,
  name,
  portfolios,
  selectedPortfolioId,
});

export const fakeLogin = () => (dispatch) => {
  const fakeInfo = {
    id: 'e9690980-9c98-4ab3-8966-310e14cd012e',
    name: 'Scott Logic',
    portfolios: {
      1: 'portfolio1',
      25: 'portfolio2',
      369: 'portfolio3',
      423: 'portfolio4',
    },
    selectedPortfolioId: '1',
  };
  dispatch(login(fakeInfo));
};
