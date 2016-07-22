import React, { Component, PropTypes } from 'react';
import Confirmation from 'components/tile/ConfirmationContainer';
import Pair from 'components/tile/PairContainer';
import 'components/tile/Tile.scss';

class Tile extends Component {
  componentDidMount() {
    if (!this.props.pair) {
      this.props.getValidPairs();
    } else {
      this.props.subscribePrice(this.props.pair.id);
    }
  }

  componentWillReceiveProps(nextProps) {
    if (!this.props.pair || this.props.pair !== nextProps.pair) {
      this.props.subscribePrice(nextProps.pair.id);
    }
  }

  componentWillUnmount() {
    this.props.unsubscribePrice(this.props.pair.id);
  }

  render() {
    const { pair } = this.props;
    if (!pair) {
      return <div className="tile-loading">Loading</div>;
    }

    const child = this.props.quote ?
      <Confirmation pair={this.props.pair} /> :
      <Pair pair={this.props.pair} />;

    return (
      <div className="tile">
        {child}
      </div>
    );
  }
}

Tile.propTypes = {
  quote: PropTypes.object,
  pair: PropTypes.object.isRequired,
  subscribePrice: PropTypes.func.isRequired,
  unsubscribePrice: PropTypes.func.isRequired,
  getValidPairs: React.PropTypes.func.isRequired,
};

export default Tile;
