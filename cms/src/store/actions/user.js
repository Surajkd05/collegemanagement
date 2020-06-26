import axios from "../../axios-college";
import * as actionTypes from "./actionTypes";

//get User profile
export const fetchUserProfileSuccess = (user) => {
  return {
    type: actionTypes.FETCH_USER_PROFILE_SUCCESS,
    user: user,
  };
};

export const fetchUserProfileFail = (error) => {
  return {
    type: actionTypes.FETCH_USER_PROFILE_FAIL,
    error: error,
  };
};

export const fetchUserProfileStart = () => {
  return {
    type: actionTypes.FETCH_USER_PROFILE_START,
  };
};

export const fetchUserProfile = () => {
  return (dispatch) => {
    dispatch(fetchUserProfileStart());

    let responseData = null;

    if (localStorage.getItem("role") === "emp") {
      responseData = axios.get("user/", {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      });
    } else {
      responseData = axios.get("user/student", {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      });
    }

    responseData
      .then((response) => {
        console.log("Fetched user is : ", response.data);
        dispatch(fetchUserProfileSuccess(response.data));
      })
      .catch((error) => {
        console.log("ERROR is : ", error.response.data.message);
        dispatch(fetchUserProfileFail(error.response.data.message));
      });
  };
};

//get User address
export const fetchUserAddressSuccess = (addresses) => {
  return {
    type: actionTypes.DELETE_USER_ADDRESS_SUCCESS,
    addresses: addresses,
  };
};

export const fetchUserAddressFail = (error) => {
  return {
    type: actionTypes.DELETE_USER_ADDRESS_FAIL,
    error: error,
  };
};

export const fetchUserAddressStart = () => {
  return {
    type: actionTypes.DELETE_USER_ADDRESS_START,
  };
};

export const fetchUserAddress = (token) => {
  return (dispatch) => {
    dispatch(fetchUserAddressStart());
    axios
      .get("user/address", {
        headers: { Authorization: "Bearer " + token },
      })
      .then((response) => {
        console.log("Fetched user address is : ", response.data);
        dispatch(fetchUserAddressSuccess(response.data));
      })
      .catch((error) => {
        console.log("ERROR is : ", error.response);
        dispatch(fetchUserAddressFail(error.response));
      });
  };
};

//delete User address
export const deleteUserAddressSuccess = (response) => {
  return {
    type: actionTypes.DELETE_USER_ADDRESS_SUCCESS,
    response: response,
  };
};

export const deleteUserAddressFail = (error) => {
  return {
    type: actionTypes.DELETE_USER_ADDRESS_FAIL,
    error: error,
  };
};

export const deleteUserAddressStart = () => {
  return {
    type: actionTypes.DELETE_USER_ADDRESS_START,
  };
};

export const onDeleteUserAddress = (addressId) => {
  return (dispatch) => {
    dispatch(deleteUserAddressStart());
    axios({
      method: "DELETE",
      url: "user/" + addressId,
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
    })
      .then((response) => {
        console.log("Fetched user address is : ", response.data);
        alert(response.data);
        dispatch(deleteUserAddressSuccess(response.data));
      })
      .catch((error) => {
        console.log("ERROR is : ", error.response);
        dispatch(deleteUserAddressFail(error.response));
      });
  };
};

// add User address
export const addUserAddressSuccess = (response) => {
  return {
    type: actionTypes.ADD_USER_ADDRESS_SUCCESS,
    response: response,
  };
};

export const addUserAddressFail = (error) => {
  return {
    type: actionTypes.ADD_USER_ADDRESS_FAIL,
    error: error,
  };
};

export const addUserAddressStart = () => {
  return {
    type: actionTypes.ADD_USER_ADDRESS_START,
  };
};

export const onAddUserAddress = (addressData) => {
  return (dispatch) => {
    dispatch(addUserAddressStart());
    axios({
      method: "POST",
      url: "user/",
      data: addressData,
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
    })
      .then((response) => {
        console.log("metadata : ", response.data);
        alert(response.data);
        dispatch(addUserAddressSuccess(response.data));
      })
      .catch((error) => {
        console.log(" error is : ", error.response.data.message);
        alert(error.response.data.message);
        dispatch(addUserAddressFail(error.response.data.message));
      });
  };
};

//update User address
export const updateUserAddressSuccess = (response) => {
  return {
    type: actionTypes.UPDATE_USER_ADDRESS_SUCCESS,
    response: response,
  };
};

export const updateUserAddressFail = (error) => {
  return {
    type: actionTypes.UPDATE_USER_ADDRESS_FAIL,
    error: error,
  };
};

export const updateUserAddressStart = () => {
  return {
    type: actionTypes.UPDATE_USER_ADDRESS_START,
  };
};

export const onUpdateUserAddress = (addressData, addressId) => {
  return (dispatch) => {
    dispatch(updateUserAddressStart());
    axios({
      method: "PUT",
      url: "user/" + addressId,
      data: addressData,
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
    })
      .then((response) => {
        console.log("metadata : ", response.data);
        alert(response.data);
        dispatch(updateUserAddressSuccess(response.data));
      })
      .catch((error) => {
        console.log(" error is : ", error.response.data.message);
        alert(error.response.data.message);
        dispatch(updateUserAddressFail(error.response.data.message));
      });
  };
};
