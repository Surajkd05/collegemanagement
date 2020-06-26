import * as actionTypes from "./actionTypes";
import axios from "../../axios-college";
import qs from "qs";

export const authStart = () => {
  return {
    type: actionTypes.AUTH_START,
  };
};

export const authSuccess = (token) => {
  return {
    type: actionTypes.AUTH_SUCCESS,
    idToken: token,
  };
};

export const authFail = (error) => {
  return {
    type: actionTypes.AUTH_FAIL,
    error: error,
  };
};

export const logout = () => {
  axios
    .get("app/doLogout", {
      headers: { Authorization: "Bearer " + localStorage.getItem("token") },
    })
    .then((response) => {
      alert(response.data);
    })
    .catch((error) => {
      console.log(error.response);
    });
  localStorage.removeItem("token");
  localStorage.removeItem("expirationDate");
  localStorage.removeItem("role");
  return {
    type: actionTypes.AUTH_LOGOUT,
  };
};

export const checkAuthTimeout = (expirationTime) => {
  console.log("Expiration time is : ", expirationTime);
  return (dispatch) => {
    setTimeout(() => {
      dispatch(logout());
    }, expirationTime * 1000);
  };
};

export const auth = (login) => {
  return (dispatch) => {
    dispatch(authStart());

    console.log("Login info is :", login);
    const loginData = {
      grant_type: "password",
      client_id: "live-test",
      client_secret: "abcde",
    };

    for (let key in login) {
      if (key !== "role") {
        loginData[key] = login[key].value;
      }
    }

    console.log("Login Information : ", loginData);

    axios
      .post(
        "oauth/token",
        qs.stringify(loginData)
      )
      .then((response) => {
        console.log(response);
        console.log(response.data);
        console.log("Expires in : ", response.data.expires_in);
        const expirationDate = new Date(
          new Date().getTime() + response.data.expires_in * 1000
        );
        localStorage.setItem("token", response.data.access_token);
        localStorage.setItem("expirationDate", expirationDate);
        //dispatch(authSuccess(response.data.idToken, response.data.localId));
        dispatch(authSuccess(response.data.idToken));
        dispatch(checkAuthTimeout(response.data.expires_in));
      })
      .catch((error) => {
        console.log(error);
        dispatch(authFail(error.response.data.error));
      });
  };
};

export const authRedirect = (path) => {
  return {
    type: actionTypes.AUTH_REDIRECT_PATH,
    path: path,
  };
};

export const authCheckState = () => {
  return (dispatch) => {
    const token = localStorage.getItem("token");
    if (!token) {
      dispatch(logout());
    } else {
      const expirationDate = new Date(localStorage.getItem("expirationDate"));
      if (expirationDate > new Date()) {
        dispatch(authSuccess(token));
        dispatch(
          checkAuthTimeout(
            (expirationDate.getTime() - new Date().getTime()) / 1000
          )
        );
      } else {
        dispatch(logout());
      }
    }
  };
};
