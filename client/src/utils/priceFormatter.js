
const formatPrice = (price, decimals) => {
  const pointSize = Math.pow(10, decimals);
  return (price / pointSize).toFixed(decimals);
};

export default formatPrice;
