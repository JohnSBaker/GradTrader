import React from 'react';
import { AutoSizer, VirtualScroll } from 'react-virtualized';
import Trade from './Trade';
import './Blotter.scss';

class Blotter extends React.Component {

  componentDidMount() {
    this.props.requestPreviousTrades();
    this.props.subscribeTrade();
  }

  componentWillUnmount() {
    this.props.unsubscribeTrade();
  }

  noRowsRenderer() {
    return (
      <div>
        No trades
      </div>
    );
  }

  render() {
    const { trades, pairId, showBox } = this.props;

    const rowRenderer = ({ index }) => <Trade
      trade={trades[index]}
      showCurrency={!pairId}
      showBox={showBox}
    />;

    const rowHeight = showBox ? 80 : 60;

    return (
      <div className="blotter">
        <AutoSizer>
          {({ height, width }) => (
            <VirtualScroll
              width={width}
              height={height}
              rowCount={trades.length}
              rowHeight={rowHeight}
              rowRenderer={rowRenderer}
              noRowsRenderer={this.noRowsRenderer}
            />)}
        </AutoSizer>
      </div>
    );
  }
}

Blotter.propTypes = {
  trades: React.PropTypes.array.isRequired,
  pairId: React.PropTypes.string,
  showBox: React.PropTypes.bool,
  requestPreviousTrades: React.PropTypes.func.isRequired,
  subscribeTrade: React.PropTypes.func.isRequired,
  unsubscribeTrade: React.PropTypes.func.isRequired,
};

Blotter.defaultProps = {
  showBox: true,
};

export default Blotter;
