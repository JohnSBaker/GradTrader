import atmosphere from 'atmosphere.js';

let socket;
let socketConnected = false;
const eventQueue = [];
const callbacks = {};

const UNSUBSCRIBE_PRICE = 'unsubscribePrice';
const SUBSCRIBE_PRICE = 'subscribePrice';
const UNSUBSCRIBE_TRADE = 'unsubscribeTrade';
const SUBSCRIBE_TRADE = 'subscribeTrade';


export const openSocketConnection = () => {
  const request = {
    url: `ws://${window.location.hostname}/api/ws/`,
    contentType: 'application/json',
    transport: 'websocket',
    fallbackTransport: 'long-polling',
  };

  request.onOpen = () => {
      socketConnected = true;
    eventQueue.forEach((event) => event());
  };

  request.onMessage = (response) => {
    const message = response.responseBody;
    const json = JSON.parse(message);
    if (callbacks[json.action]) {
      callbacks[json.action](json);
    }
  };

  request.onError = (response) => {
    console.log('on error');
    console.log('Sorry, but there\'s some problem with your '
    + 'socket or the server is down');
    console.log(response);
  };

  request.onClose = (response) => {
    socketConnected = false;
    console.log('on close');
    console.log(response);
  };

  socket = atmosphere.subscribe(request);
};

export const setCallback = (name, cb) => {
  callbacks[name] = cb;
};

function sendSocketEvent(action, subject) {
  if (!socket || !socketConnected) {
    eventQueue.push(() => socket.push(
        JSON.stringify({ action, subject })
      )
    );
  } else {
    socket.push(
      JSON.stringify({ action, subject })
    );
  }
}

export const subscribePrice = (pairId) => {
  sendSocketEvent(SUBSCRIBE_PRICE, pairId);
};

export const unsubscribePrice = (pairId) => {
  sendSocketEvent(UNSUBSCRIBE_PRICE, pairId);
};

export const subscribeTrade = (userId) => {
  sendSocketEvent(SUBSCRIBE_TRADE, userId);
};

export const unsubscribeTrade = (userId) => {
  sendSocketEvent(UNSUBSCRIBE_TRADE, userId);
};
