import React from 'react';
import Route from './common/router/router';
import { Provider } from 'react-redux';
import store from './store/index';

function App() {
  return (
    <Provider store={ store }><Route /></Provider>   
  );
}

export default App;
