import React, { Component } from 'react';
import moment from 'moment';
import { AutoSizer, FlexTable, FlexColumn, SortIndicator, SortDirection } from 'react-virtualized';
import 'components/blotter/desktop/Blotter.scss';

class Blotter extends Component {

  constructor (props, context) {
      super(props, context)
      this._headerRenderer = this._headerRenderer.bind(this)
      this._sort = this._sort.bind(this)
    }

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

  _sortBy = 'tradeId';
  _sortDirection = SortDirection.ASC;

  _sort ({ sortBy, sortDirection }) {
    //TODO: emit action to update store, instead of this component having state?
    this._sortBy = sortBy;
    this._sortDirection = sortDirection;
    console.log("Sort by %s (%s)", this._sortBy, this._sortDirection);
  }

  _headerRenderer ({
    columnData,
    dataKey,
    disableSort,
    label,
    sortBy,
    sortDirection
  }) {
    return (
      <div>
        {label}
        {this._sortBy === dataKey &&
          <SortIndicator sortDirection={sortDirection} />
        }
      </div>
    )
  }

  render() {
    const sortDirection = this._sortDirection;
    const sortBy = this._sortBy;
    const { trades } = this.props;
    const high = (sortDirection === SortDirection.ASC ? 1 : -1)
    const sortedTrades = trades
        .sort((a, b) => (a[sortBy]>b[sortBy]?high:(a[sortBy]<b[sortBy]?-high:0)));

    const disableHeader = !trades.length;

    const renderCurrency = ({ cellData }) => `${cellData.substr(0, 3)} | ${cellData.substr(3, 3)}`;

    const renderTime = ({ cellData }) => moment(cellData).format('HH:mm');

    const renderDate = ({ cellData }) => moment(cellData).format('DD MMM YYYY');

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
              gridClassName="blotter-table-body"
              rowClassName={
                ({ index }) => {
                  if (index === -1) {
                    return 'blotter-table-header';
                  }
                  return index % 2 ? 'blotter-table-body-row-odd' : 'blotter-table-body-row-even';
                }
              }
              rowGetter={
                ({ index }) => (sortedTrades[index])
              }
              sort={this._sort}
              sortBy={this._sortBy}
              sortDirection={this._sortDirection}
            >
              <FlexColumn
                label="Trade ID"
                dataKey="tradeId"
                flexGrow={1}
                width={100}
              />
              <FlexColumn
                label="Time"
                dataKey="timestamp"
                className="bold-cell"
                flexGrow={1}
                width={100}
                cellRenderer={renderTime}
              />
              <FlexColumn
                label="Date"
                dataKey="timestamp"
                flexGrow={1}
                width={100}
                cellRenderer={renderDate}
              />
              <FlexColumn
                label="Currency"
                dataKey="pairId"
                className="bold-cell"
                flexGrow={1}
                width={100}
                cellRenderer={renderCurrency}
                disableSort={false}
                headerRenderer={this._headerRenderer}
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
                dataKey="formattedPrice"
                className="bold-cell"
                flexGrow={1}
                width={100}
              />
              <FlexColumn
                label="Portfolio"
                dataKey="portfolioName"
                flexGrow={1}
                width={100}
              />
              <FlexColumn
                label="User"
                dataKey="userName"
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
  requestPreviousTrades: React.PropTypes.func.isRequired,
  subscribeTrade: React.PropTypes.func.isRequired,
  unsubscribeTrade: React.PropTypes.func.isRequired,
};

export default Blotter;
