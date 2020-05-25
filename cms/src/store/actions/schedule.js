import * as actionTypes from "./actionTypes";

export const scheduleRedirect = (path) => {
  return {
    type: actionTypes.SCHEDULE_REDIRECT_PATH,
    path: path,
  };
};
