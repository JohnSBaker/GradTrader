import React, { Component, PropTypes } from 'react';
import { AutoSizer, FlexTable, FlexColumn } from 'react-virtualized';
import PriceTicker from 'components/priceTicker/PriceTickerContainer';
import './PriceList.scss';

class PriceList extends Component {
  componentDidMount() {
    if (this.props.pairs.length === 0) {
      this.props.getValidPairs();
    } else {
      this.subscribePrices(null, this.props.pairs);
    }
  }

  componentWillReceiveProps(nextProps) {
    this.subscribePrices(this.props.pairs, nextProps.pairs);
  }

  componentWillUnmount() {
    this.subscribePrices(this.props.pairs);
  }

  subscribePrices(subscribedPairs, pairs) {
    if (pairs) {
      const subscribe = pairs.filter((pair) => !subscribedPairs || subscribedPairs.every(subscribedPair => subscribedPair.id !== pair.id));
      subscribe.forEach(pair => {
        this.props.subscribePrice(pair.id);
      });
    }

    if (subscribedPairs) {
      const unsubscribe = subscribedPairs.filter((subscribedPair) => !pairs || pairs.every(pair => subscribedPair.id !== pair.id));
      unsubscribe.forEach(pair => {
        this.props.unsubscribePrice(pair.id);
      });
    }
  }

  render() {
    const { pairs, onPairSelected } = this.props;

    if (!pairs || pairs.length === 0) {
      return <div className="loading">Loading</div>;
    }

    const renderCurrency = ({ cellData }) => (
      <span className="price-list-currency">
        <span className="price-list-currency-base">{cellData.substr(0, 3)}</span>
        |
        <span className="price-list-currency-counter">{cellData.substr(3, 3)}</span>
      </span>);
    const renderBid = ({ rowData }) => <PriceTicker pair={rowData} type="bid" stacked={false} />;
    const renderAsk = ({ rowData }) => <PriceTicker pair={rowData} type="ask" stacked={false} />;
    const rowWrapperClassName = ({ index }) => (index % 2 === 0 ? 'price-list-row-even' : 'price-list-row-odd');
    const rowGetter = ({ index }) => pairs[index];
    const onRowClick = ({ index }) => {
      const pair = pairs[index];
      if (onPairSelected) {
        onPairSelected(pair);
      }
    };

    return (
      <div className="price-list">
        <AutoSizer>
          {({ height, width }) => (
            <FlexTable
              width={width}
              height={height}
              rowCount={pairs.length}
              rowHeight={85}
              headerHeight={65}
              headerClassName="price-list-header"
              rowWrapperClassName={rowWrapperClassName}
              rowGetter={rowGetter}
              onRowClick={onRowClick}
            >
              <FlexColumn
                label="Currency"
                dataKey="id"
                flexGrow={1}
                width={100}
                cellRenderer={renderCurrency}
              />
              <FlexColumn
                label="Bid"
                dataKey="id"
                flexGrow={0.7}
                width={70}
                cellRenderer={renderBid}
              />
              <FlexColumn
                label="Ask"
                dataKey="id"
                flexGrow={0.7}
                width={70}
                cellRenderer={renderAsk}
              />
            </FlexTable>
          )
          }
        </AutoSizer>
      </div>
    );
  }
}

PriceList.propTypes = {
  pairs: PropTypes.array.isRequired,
  getValidPairs: PropTypes.func.isRequired,
  subscribePrice: PropTypes.func.isRequired,
  unsubscribePrice: PropTypes.func.isRequired,
  onPairSelected: PropTypes.func,
};

export default PriceList;
