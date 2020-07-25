import React, { useState } from "react";
import classes from "./UserView.module.css";
import Button from "../UI/Button/Button";
import axios from "../../axios-college";
import Spinner from "../UI/Spinner/Spinner";
import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";
import "react-super-responsive-table/dist/SuperResponsiveTableStyle.css";
import { withRouter } from "react-router-dom";

const UserView = (props) => {
  const [loading, setLoading] = useState(false);

  const activateUserHandler = (userId) => {
    setLoading(true);
    let fetchedData = null;

    if (!(localStorage.getItem("role") === "emp")) {
      if (props.userRole === "std") {
        fetchedData = axios({
          method: "PATCH",
          url: "admin/activateStudent/" + userId,
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        });
      } else {
        fetchedData = axios({
          method: "PATCH",
          url: "admin/activateEmployee/" + userId,
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        });
      }
    } else {
      fetchedData = axios({
        method: "PATCH",
        url: "employee/activateStudent/" + userId,
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      });
    }
    fetchedData
      .then((response) => {
        setLoading(false);
        alert(response.data);
      })
      .catch((error) => {
        setLoading(false);
        alert(error.response.data.message);
      });
  };

  const deActivateUserHandler = (userId) => {
    setLoading(true);
    let fetchedData = null;
    if (!(localStorage.getItem("role") === "emp")) {
      if (props.userRole === "std") {
        fetchedData = axios({
          method: "PATCH",
          url: "admin/de-activateStudent/" + userId,
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        });
      } else {
        fetchedData = axios({
          method: "PATCH",
          url: "admin/de-activateEmployee/" + userId,
          headers: {
            Authorization: "Bearer " + localStorage.getItem("token"),
          },
        });
      }
    } else {
      fetchedData = axios({
        method: "PATCH",
        url: "employee/de-activateStudent/" + userId,
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      });
    }
    fetchedData
      .then((response) => {
        setLoading(false);
        alert(response.data);
      })
      .catch((error) => {
        setLoading(false);
        alert(error.response.data.message);
      });
  };

  if (loading) {
    return <Spinner />;
  }

  return (
    <section className={classes.UserView}>
      <h4>Fetched Users</h4>
      <Table>
        <Thead>
          <Tr>
            <Th>S.No</Th>
            <Th>User Id</Th>
            <Th>First Name</Th>
            <Th>Last Name</Th>
            <Th>Email</Th>
            <Th>Active</Th>
            <Th>Activate</Th>
            <Th>DeActivate</Th>
          </Tr>
        </Thead>
        <Tbody>
          {props.fetchedUsers.map((user, count) => (
            <Tr key={user.userId}>
              <Td key={user.userId}>{count + 1}</Td>

              <Td>{user.userId}</Td>
              <Td>{user.firstName}</Td>
              <Td>{user.lastName}</Td>
              <Td>{user.email}</Td>
              <Td>{String(user.active)}</Td>
              <Td>
                <Button
                  clicked={(userId) => activateUserHandler(user.userId)}
                  btnType="Success"
                >
                  Activate
                </Button>
              </Td>
              <Td>
                <Button
                  clicked={(userId) => deActivateUserHandler(user.userId)}
                  btnType="Danger"
                >
                  DeActivate
                </Button>
              </Td>
            </Tr>
          ))}
        </Tbody>
      </Table>
    </section>
  );
};

export default withRouter(UserView);
