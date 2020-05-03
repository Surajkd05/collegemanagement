import { createStore, combineReducers } from 'redux';
import { ruducers } from '../store/ruducers';

const root = combineReducers( {
    ruducers
} );

const store = createStore( root );

export default store;