import React, { Component } from 'react';
import { AutoSizer, FlexTable, FlexColumn } from 'react-virtualized';
import './Blotter.scss';

class Blotter extends Component {

  noRowsRenderer() {
    return (
      <div>
        No trades
      </div>
    );
  }

  render() {
    const { trades } = this.props;
    const disableHeader = !trades.length;

    return (
      <div className="blotter">
        <AutoSizer>
          {({ height, width }) => (
            <FlexTable
              disableHeader={disableHeader}
              width={width}
              height={height}
              rowCount={trades.length}
              rowHeight={39}
              noRowsRenderer={this.noRowsRenderer}
              headerHeight={24}
              className="blotter-table"
              rowClassName={
                ({ index }) => {
                  if (index === -1) {
                    return 'blotter-header-row';
                  }
                  return index % 2 ? 'blotter-odd-row' : 'blotter-even-row';
                }
              }
              rowGetter={
                ({ index }) => (trades[index])
              }
            >
              <FlexColumn
                label="Trade ID"
                dataKey="tradeId"
                flexGrow={1}
                width={100}
              />
              <FlexColumn
                label="Time"
                dataKey="time"
                className="bold-cell"
                flexGrow={1}
                width={100}
              />
              <FlexColumn
                label="Date"
                dataKey="date"
                flexGrow={1}
                width={100}
              />
              <FlexColumn
                label="Currency"
                dataKey="currency"
                className="bold-cell"
                flexGrow={1}
                width={100}
              />
              <FlexColumn
                label="Direction"
                dataKey="direction"
                flexGrow={1}
                width={100}
              />
              <FlexColumn
                label="Quantity"
                dataKey="quantity"
                className="bold-cell"
                flexGrow={1}
                width={100}
              />
              <FlexColumn
                label="Rate"
                dataKey="rate"
                className="bold-cell"
                flexGrow={1}
                width={100}
              />
              <FlexColumn
                label="Portfolio"
                dataKey="portfolio"
                flexGrow={1}
                width={100}
              />
              <FlexColumn
                label="User"
                dataKey="user"
                flexGrow={1}
                width={100}
              />
            </FlexTable>
          )
        }
        </AutoSizer>
      </div>
    );
  }
}

Blotter.propTypes = {
  trades: React.PropTypes.array.isRequired,
};

export default Blotter;
