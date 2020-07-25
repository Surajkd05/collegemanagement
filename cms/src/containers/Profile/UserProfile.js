import React, { useEffect, useState } from "react";
import { connect } from "react-redux";
import * as actions from "../../store/actions/index";
import classes from "./UserProfile.module.css";
import Button from "../../components/UI/Button/Button";
import Aux from "../../hoc/Aux1/aux1";
import AddressView from "./Address/AddressView";
import axios from "../../axios-college";
import avatar from "../../assets/images/avatar.webp";

const UserProfile = React.memo((props) => {
  const { onFetchUserProfile } = props;

  const [view, setView] = useState(false);

  const [profileImage, setProfileImage] = useState();

  const [pro, setPro] = useState(false);

  useEffect(() => {
    onFetchUserProfile();
    axios
      .get("image/", {
        responseType: "blob",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
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

  const imageUploaderHandler = () => {
    props.history.push({
      pathname: "/upload",
    });
  };

  let image = null;
  if (!pro) {
    image = (
      <div className={classes.container}>
        <div
          className={classes.uploadImage}
          onClick={() => imageUploaderHandler()}
        >
          <img src={avatar} alt="dummy" className={classes.Image} />
          <div className={classes.Middle}>
            <div className={classes.Text}>Upload Image</div>
          </div>
        </div>
      </div>
    );
  } else {
    image = (
      <div
        className={classes.Container}
        onClick={() => imageUploaderHandler()}
        style={{ cursor: "pointer" }}
      >
        <img src={profileImage} alt="ProfileImage" className={classes.Image} />
        <div className={classes.Middle}>
          <div className={classes.Text}>Upload Image</div>
        </div>
      </div>
    );
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

  const switchAddressHandler = () => {
    console.log("Address view clicked");
    setView(!view);
  };

  const editProfileHandler = () => {
    if (localStorage.getItem("role") === "emp") {
      props.history.push({
        pathname: "/editEmployeeProfile",
        state: { profile: props.profile },
      });
    } else {
      props.history.push({
        pathname: "/editStudentProfile",
        state: { profile: props.profile },
      });
    }
  };

  const addAddressHandler = () => {
    props.history.push("/addAddress");
  };

  let addressView = null;
  if (view) {
    addressView = <AddressView />;
  }

  if (props.profile !== null) {
    userProfile = (
      <div className={classes.CustomerProfile}>
        <h1> USER PROFILE </h1>
        {image}
        <p>
          <strong>Username : {props.profile.username}</strong>
        </p>
        <p>
          <strong>First Name : {props.profile.firstName}</strong>
        </p>
        <p>
          <strong>Last Name : {props.profile.lastName}</strong>
        </p>
        <p>
          <strong>Mobile Number : {props.profile.mobileNo}</strong>
        </p>

        {localStorage.getItem("role") === "std" ? (
          <p>
            <strong>Branch : {props.profile.branch}</strong>
          </p>
        ) : null}

        {localStorage.getItem("role") === "std" ? (
          <p>
            <strong>Year: {props.profile.year}</strong>
          </p>
        ) : null}

        {localStorage.getItem("role") === "std" ? (
          <p>
            <strong>Section : {props.profile.section}</strong>
          </p>
        ) : null}
        {localStorage.getItem("role") === "std" ? (
          <p>
            <strong>Semester : {props.profile.semester}</strong>
          </p>
        ) : null}
        <p>
          {!view ? (
            <Button clicked={switchAddressHandler} btnType="Success">
              Show Address
            </Button>
          ) : (
            <Button clicked={switchAddressHandler} btnType="Success">
              Hide
            </Button>
          )}

          <Button clicked={editProfileHandler} btnType="Success">
            {" "}
            Edit Profile{" "}
          </Button>
          <Button clicked={addAddressHandler} btnType="Success">
            Add Address
          </Button>
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
      <div>{addressView}</div>
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
    onFetchUserAddress: () => dispatch(actions.fetchUserAddress()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(UserProfile);
