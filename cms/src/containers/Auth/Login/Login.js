import React, { useState } from "react";
import classes from "./Login.module.css";
import { updateObject, checkValidity } from "../../../shared/utility";
import Input from "../../../components/UI/Input/Input";
import Spinner from "../../../components/UI/Spinner/Spinner";

const Login = React.memo((props) => {
  const [login, setLogin] = useState({
    email: {
      elementType: "input",
      elementConfig: {
        type: "email",
        placeholder: "Enter your E-mail",
      },
      value: "",
      validation: {
        required: true,
        isEmail: true,
      },
      isValid: false,
      touched: false,
    },
    password: {
      elementType: "input",
      elementConfig: {
        type: "password",
        placeholder: "Enter your Password",
      },
      value: "",
      validation: {
        required: true,
        minLength: 8,
        maxLength: 15,
      },
      isValid: false,
      touched: false,
    },
  });

  const inputChangedHandler = (event, loginData) => {
    const updatedSchedules = updateObject(login, {
      [loginData]: updateObject(login[loginData], {
        value: event.target.value,
        valid: checkValidity(event.target.value, login[loginData].validation),
        touched: true,
      }),
    });
    setLogin(updatedSchedules);
  };

  const formElementsArray = [];
  for (let key in login) {
    formElementsArray.push({
      id: key,
      config: login[key],
    });
  }

  let form = formElementsArray.map((formElement) => (
    <Input
      key={formElement.id}
      elementType={formElement.config.elementType}
      elementConfig={formElement.config.elementConfig}
      value={formElement.config.value}
      invalid={!formElement.config.valid}
      shouldValidate={formElement.config.validation}
      touched={formElement.config.touched}
      changed={(event) => inputChangedHandler(event, formElement.id)}
    />
  ));

  const submitHandler = (event) => {
    event.preventDefault();
  };

  if (props.loading) {
    form = <Spinner />;
  }

  return (
    <div className={classes.LoginData}>
      <h4>Login</h4>
      <form onSubmit={submitHandler}>
        {form}
        <button type="submit">LOGIN</button>
      </form>
    </div>
  );
});

export default Login;
