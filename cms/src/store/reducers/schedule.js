import { updateObject } from "../../shared/utility"
import * as actionTypes from "../actions/actionTypes"

const initialState = {
    scheduleRedirectPath: "/",
  };

const scheduleRedirectPath = (state, action) => {
  console.log("IN schedule", action.path)
    return updateObject(state, { scheduleRedirectPath: action.path });
  };

  const reducer = (state = initialState, action) => {
    switch (action.type) {
      case actionTypes.SCHEDULE_REDIRECT_PATH:
        return scheduleRedirectPath(state, action);
      default:
        return state;
    }
  };
  
  export default reducer;