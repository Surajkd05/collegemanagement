import * as actionTypes from "../actions/actionTypes";
import { updateObject } from "../../shared/utility";

const initialState = {
  user: null,
  loading: false,
  addAddress: false,
  error: null,  
  response: null,
  updateAddress: false,
  addresses: []
};

//get User profile
const fetchUserProfileStart = (state, action) => {
  return updateObject(state, { loading: true });
};

const fetchUserProfileSuccess = (state, action) => {
  return updateObject(state, {
    user : action.user,
    loading: false,
  });
};

const fetchUserProfileFail = (state, action) => {
  return updateObject(state, { loading: false, error: action.error });
};

//get User address

const fetchUserAddressStart = (state, action) => {
  return updateObject(state, { loading: true });
};

const fetchUserAddressSuccess = (state, action) => {
  return updateObject(state, {
    addresses : action.addresses,
    loading: false,
  });
};

const fetchUserAddressFail = (state, action) => {
  return updateObject(state, { loading: false, error: action.error });
};

//delete User address
const deleteUserAddressStart = (state, action) => {
  return updateObject(state, { loading: true });
};

const deleteUserAddressSuccess = (state, action) => {
  return updateObject(state, {
    response : action.response,
    loading: false,
  });
};

const deleteUserAddressFail = (state, action) => {
  return updateObject(state, { loading: false, error: action.error });
};

//add User address
const addUserAddressStart = (state, action) => {
  return updateObject(state, { loading: true });
};

const addUserAddressSuccess = (state, action) => {
  return updateObject(state, {
    loading: false,
    addAddress:true
  });
};

const addUserAddressFail = (state, action) => {
  return updateObject(state, { loading: false, error: action.error });
};

//update User address
const updateUserAddressStart = (state, action) => {
  return updateObject(state, { loading: true });
};

const updateUserAddressSuccess = (state, action) => {
  return updateObject(state, {
    loading: false,
    updateAddress:true
  });
};

const updateUserAddressFail = (state, action) => {
  return updateObject(state, { loading: false, error: action.error });
};


const reducer = (state = initialState, action) => {
  switch (action.type) {
    case actionTypes.FETCH_USER_PROFILE_START:
      return fetchUserProfileStart(state, action);
    case actionTypes.FETCH_USER_PROFILE_SUCCESS:
      return fetchUserProfileSuccess(state, action);
    case actionTypes.FETCH_USER_PROFILE_FAIL:
      return fetchUserProfileFail(state, action);
    case actionTypes.FETCH_USER_ADDRESS_START:
      return fetchUserAddressStart(state, action);
    case actionTypes.FETCH_USER_ADDRESS_SUCCESS:
      return fetchUserAddressSuccess(state, action);
    case actionTypes.FETCH_USER_ADDRESS_FAIL:
      return fetchUserAddressFail(state, action);
    case actionTypes.DELETE_USER_ADDRESS_START:
      return deleteUserAddressStart(state, action);
    case actionTypes.DELETE_USER_ADDRESS_SUCCESS:
      return deleteUserAddressSuccess(state, action);
    case actionTypes.DELETE_USER_ADDRESS_FAIL:
      return deleteUserAddressFail(state, action);
    case actionTypes.ADD_USER_ADDRESS_START:
      return addUserAddressStart(state, action);
    case actionTypes.ADD_USER_ADDRESS_SUCCESS:
      return addUserAddressSuccess(state, action);
    case actionTypes.ADD_USER_ADDRESS_FAIL:
      return addUserAddressFail(state, action);
    case actionTypes.UPDATE_USER_ADDRESS_START:
      return updateUserAddressStart(state, action);
    case actionTypes.UPDATE_USER_ADDRESS_SUCCESS:
      return updateUserAddressSuccess(state, action);
    case actionTypes.UPDATE_USER_ADDRESS_FAIL:
      return updateUserAddressFail(state, action);
    default:
      return state;
  }
};

export default reducer;
