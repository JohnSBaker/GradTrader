import React, { PropTypes } from 'react';
import PriceList from 'components/priceList/PriceListContainer';
import './Pairs.scss';

const Pairs = (props, { router }) => {
  const onPairSelected = (pair) => {
    router.push({
      pathname: `/pair/${pair.id}`,
    });
  };

  return (
    <div className="routes-pairs">
      <PriceList onPairSelected={onPairSelected} />
    </div>
  );
};

Pairs.contextTypes = {
  router: PropTypes.object.isRequired,
};

export default Pairs;
