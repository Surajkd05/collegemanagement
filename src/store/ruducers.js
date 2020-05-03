import { actions } from './actions';


const init = {
    allProjectRoom: []
};

export const ruducers = ( state = init, action ) => {
    const { type, data } = action;

    switch( type ) {

        case actions.GET_ALL_PROJECTS_ROOM : {
            
            return {
                ...state,
                allProjectRoom: [ ...state.allProjectRoom, ...data ]
            };
        }

        default: 
            return state;
    }
};