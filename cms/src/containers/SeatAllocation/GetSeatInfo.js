import React, { useEffect, useState } from "react";
import { connect } from "react-redux";
import * as actions from "../../store/actions/index";
import withErrorHandler from "../../hoc/withErrorHandler/withErrorHandler";
import classes from "./GetSeatInfo.module.css";
import Constants from "../../Constants/index";
import Aux from "../../hoc/Aux/aux";
import axios from "../../axios-college";
import { Redirect } from "react-router";

const GetSeatInfo = React.memo((props) => {
  const [profileImage, setProfileImage] = useState();

  const [pro, setPro] = useState(false);

  const [profile, setProfile] = useState();

  const [loading, setLoading] = useState();

  useEffect(() => {
    axios
      .get(
        "seatAllocation/getSeatInfo?seatId=" +
          props.history.location.state.seatId,
        {
          headers: { Authorization: "Bearer " + localStorage.getItem("token") },
        }
      )
      .then((response) => {
        setProfile(response.data);
        setLoading(true);
      })
      .catch((error) => {
        alert(error.response.data.message);
      });

    axios
      .get(
        "seatAllocation/image?seatId=" + props.history.location.state.seatId,
        {
          responseType: "blob",
        }
      )
      .then((response) => {
        var reader = new window.FileReader();
        reader.readAsDataURL(response.data);
        reader.onload = () => {
          var imageDataUrl = reader.result;
          setPro(true);
          setProfileImage(imageDataUrl);
        };
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  let image = null;
  if (pro) {
    image = <img alt="Profile" src={profileImage} />;
  }

  let userProfile = null;

  let errorMessage = null;

  if (props.error != null) {
    errorMessage = (
      <div className={classes.userProfile}>
        <h4>{props.error}</h4>
      </div>
    );
  }

  if (loading) {
    userProfile = (
      <div className={classes.CustomerProfile}>
        <h1> USER PROFILE </h1>
        {image}
        <p>
          <strong>Username : {profile.username}</strong>
        </p>
        <p>
          <strong>First Name : {profile.firstName}</strong>
        </p>
        <p>
          <strong>Last Name : {profile.lastName}</strong>
        </p>
        <p>
          <strong>Mobile Number : {profile.mobileNo}</strong>
        </p>
      </div>
    );
  }

  return (
    <Aux>
      <div>
        {errorMessage}
        {userProfile}
      </div>
    </Aux>
  );
});

const mapStateToProps = (state) => {
  return {
    profile: state.user.user,
    loading: state.user.loading,
    error: state.user.error,
    token: state.auth.token,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    onFetchUserProfile: () => dispatch(actions.fetchUserProfile()),
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withErrorHandler(GetSeatInfo, axios));
