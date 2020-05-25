import React, { useState } from "react";
import classes from "./UserView.module.css";
import Button from "../UI/Button/Button"
import axios from "axios";
import Spinner from "../UI/Spinner/Spinner";

const UserView = (props) => {
  console.log("Props in list is", props);
  console.log("In Period list");
  const count = 0;
  const [loading, setLoading] = useState(false)
  
  const activateUserHandler = (userId) => {
      setLoading(true)
      console.log("UserId received is ; ",userId)
      let fetchedData = null
      if(props.userRole === "std"){
          fetchedData = axios.patch("http://localhost:8080/college/admin/home/activateStudent/"+userId)
      }else{
        fetchedData = axios.patch("http://localhost:8080/college/admin/home/activateEmployee/"+userId)
      }
      fetchedData.then(response => {
          setLoading(false)
          alert(response.data)
          console.log(response)
      }).catch(error => {
          setLoading(false)
          alert(error.data)
          console.log("Error is", error.data)
      })
  }

  const deActivateUserHandler = (userId) => {
      setLoading(true)
    console.log("UserId received is ; ",userId)
    let fetchedData = null
    if(props.userRole === "std"){
        fetchedData = axios.patch("http://localhost:8080/college/admin/home/de-activateStudent/"+userId)
    }else{
      fetchedData = axios.patch("http://localhost:8080/college/admin/home/de-activateEmployee/"+userId)
    }
    fetchedData.then(response => {
        setLoading(false)
        alert(response.data)
        console.log(response)
    }).catch(error => {
        setLoading(false)
        console.log("Error is", error)
    })
}

if(loading){
    return <Spinner />
}

  return (
    <section className={classes.UserView}>
      <h4>Fetched Users</h4>
      <table>
        <thead>
          <tr>
            <th>S.No</th>
            <th>User Id</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Active</th>
            <th>Activate</th>
            <th>DeActivate</th>
          </tr>
        </thead>
        <tbody>
          {props.fetchedUsers.map((user) => (
            <tr key={user.userId}>
                  <td
                key={user.userId}
              >
                {count+1}
              </td>

              <td>{user.userId}</td>
              <td>{user.firstName}</td>
              <td>{user.lastName}</td>
              <td>{user.email}</td>
              <td>{String(user.active)}</td>
              <td><Button clicked = {(userId) => activateUserHandler(user.userId)} btnType="Success">Activate</Button></td>
              <td><Button clicked = {(userId) => deActivateUserHandler(user.userId)} btnType="Danger">DeActivate</Button></td>
            </tr>
          ))}
        </tbody>
      </table>
    </section>
  );
};

export default UserView;
