import * as actionTypes from "./actionTypes"
import axios from "../../axios-burger"

export const timeTableSuccess = (id, timeTableData) => {
  return {
    type: actionTypes.ORDER_PURCHASE_SUCCCESS,
    orderId: id,
    timeTableData: timeTableData,
  };
};

export const timeTableFailed = (error) => {
  return {
    type: actionTypes.ORDER_PURCHASE_FAILED,
    error: error,
  };
};

export const timeTableEntryStart = () => {
  return {
    type: actionTypes.PURCHASE_BURGER_START,
  };
};

export const timeTableStart = (timeTableData, token) => {
  return (dispatch) => {
    dispatch(timeTableEntryStart());
    axios
      .post("/orders.json?auth=" + token, timeTableData)
      .then((response) => {
        dispatch(timeTableSuccess(response.data.name, timeTableData));
      })
      .catch((error) => {
        dispatch(timeTableFailed(error));
      });
  };
};

export const fetchTimeTableSuccess = (orders) => {
    return {
      type: actionTypes.FETCH_ORDERS_SUCCESS,
      orders: orders,
    };
  };
  
  export const fetchTimeTableFail = (error) => {
    return {
      type: actionTypes.FETCH_ORDERS_FAIL,
      error: error,
    };
  };
  
  export const fetchTimeTableStart = () => {
    return {
      type: actionTypes.FETCH_ORDERS_START,
    };
  };
  
  export const fetchTimeTables = (token, userId) => {
    return (dispatch) => {
      dispatch(fetchTimeTableStart());
      const queryParams =
        "?auth=" + token + '&orderBy="userId"&equalTo="' + userId + '"';
      axios
        .get("/timetable.json" + queryParams)
        .then((res) => {
          const fetchedTimeTables = [];
          for (let key in res.data) {
            fetchedTimeTables.push({
              ...res.data[key],
              id: key,
            });
          }
          dispatch(fetchTimeTableSuccess(fetchedTimeTables));
        })
        .catch((error) => {
          dispatch(fetchTimeTableFail(error));
        });
    };
  };
  