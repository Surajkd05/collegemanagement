import React, { useEffect, useState } from "react";
import classes from "./UserProfile.module.css";
import Button from "../../components/UI/Button/Button";
import Aux from "../../hoc/Aux1/aux1";
import axios from "../../axios-college";
import { withRouter } from "react-router-dom";
import UserAddressView from "./Address/UserAddressView";

const ViewProfile = React.memo((props) => {
  const [view, setView] = useState(false);

  const [profileImage, setProfileImage] = useState();

  const [pro, setPro] = useState(false);

  const [profile, setProfile] = useState(null);

  useEffect(() => {
    axios
      .get("user/user?userId=" + props.history.location.state.userId, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        setProfile(response.data);
        console.log("Data : ",response.data)
      })
      .catch((error) => {
        alert(error.response.data.message);
      });

    axios
      .get("image/user?userId=" + props.history.location.state.userId, {
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
        alert(error.response.data.message);
      });
  }, []);

  let image = null;
  if (pro) {
    image = (
      <img src={profileImage} alt="ProfileImage" className={classes.Image} />
    );
  }

  let userProfile = null;

  let errorMessage = null;

  const switchAddressHandler = () => {
    setView(!view);
  };

  let addressView = null;
  if (view) {
    addressView = <UserAddressView userId={props.history.location.state} />;
  }

  if (profile !== null) {
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

export default withRouter(ViewProfile);
