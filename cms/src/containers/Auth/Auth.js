import React, { useState } from "react";
import classes from "./Auth.module.css";
import Button from "../../components/UI/Button/Button";
import Login from "./Login/Login";

const Auth = (props) => {
  const registerAsEmployeeHandelr = () => {
    props.history.push("/employeeRegister");
  };

  const registerAsStudentHandelr = () => {
    props.history.push("studentRegister");
  };

  return (
    <div className={classes.Auth}>
      <Login />

      <Button clicked={registerAsEmployeeHandelr} btnType="Success">
        Register as Employee?
      </Button>
      <Button clicked={registerAsStudentHandelr} btnType="Success">
        Register as Student?
      </Button>
    </div>
  );
};

export default Auth;
