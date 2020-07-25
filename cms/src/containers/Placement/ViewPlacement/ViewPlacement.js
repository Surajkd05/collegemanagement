import React, { useEffect, useState } from "react";
import Aux from "../../../hoc/Aux1/aux1";
import axios from "../../../axios-college";
import classes from "./ViewPlacement.module.css";
import { withRouter } from "react-router";
import "react-super-responsive-table/dist/SuperResponsiveTableStyle.css";
import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";

const ViewPlacement = React.memo((props) => {
  const [view, setView] = useState(null);

  const [profileImage, setProfileImage] = useState();

  const [pro, setPro] = useState(false);

  useEffect(() => {
    axios
      .get(
        "placement/placement?placementId=" +
          props.history.location.state.placementId
      )
      .then((response) => {
        setView(response.data);
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
    //

    axios
      .get("placement/image?userId=" + props.history.location.state.studentId, {
        responseType: "blob",
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

  let image = null;
  if (pro) {
    image = (
      <div>
        <img alt="Profile" src={profileImage} />
      </div>
    );
  }

  let userProfile = null;
  if (view !== null) {
    userProfile = (
      <div>
        <div className={classes.CustomerProfile}>
          <h1> USER PROFILE </h1>
          {image}
          <p>
            <strong>Student Name : {view.studentName}</strong>
          </p>
          <p>
            <strong>Mobile Number : {view.mobileNo}</strong>
          </p>
          <p>
            <strong>Email : {view.email}</strong>
          </p>
        </div>
        <div>
          <section className={classes.UserView}>
            <h4>Fetched Placement Details</h4>
            <Table>
              <Thead>
                <Tr>
                  <Th>S.No</Th>
                  <Th>Company Name</Th>
                  <Th>Salary</Th>
                </Tr>
              </Thead>
              <Tbody>
                {view.companyDetailsDtos.map((data, count) => (
                  <Tr key={data.dataId}>
                    <Td key={data.dataId}>{count + 1}</Td>

                    <Td>{data.companyName}</Td>
                    <Td>{data.salary}</Td>
                  </Tr>
                ))}
              </Tbody>
            </Table>
          </section>
        </div>
      </div>
    );
  }

  return (
    <Aux>
      <div>{userProfile}</div>
    </Aux>
  );
});

export default withRouter(ViewPlacement);
