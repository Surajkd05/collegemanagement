import React, { useState } from "react";
import classes from "./Password.module.css";
import { updateObject, checkValidity } from "../../../shared/utility";
import Input from "../../../components/UI/Input/Input";
import Spinner from "../../../components/UI/Spinner/Spinner";
import axios from "../../../axios-college";
import { Redirect, withRouter } from "react-router";

const ForgotPassword = React.memo((props) => {
  const [email, setEmail] = useState({
    username: {
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
  });

  const [loading, setLoading] = useState(false);

  const [reset, setReset] = useState(false);

  const inputChangedHandler = (event, emailData) => {
    const updatedSchedules = updateObject(email, {
      [emailData]: updateObject(email[emailData], {
        value: event.target.value,
        valid: checkValidity(event.target.value, email[emailData].validation),
        touched: true,
      }),
    });
    setEmail(updatedSchedules);
  };

  const formElementsArray = [];
  for (let key in email) {
    formElementsArray.push({
      id: key,
      config: email[key],
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
    setLoading(true);

    let formData = "";

    for (let key in email) {
      formData = email[key].value;
    }

    axios
      .post("forgotPassword/token/" + formData)
      .then((response) => {
        alert(response.data);
        setLoading(false);
        setReset(true);
      })
      .catch((error) => {
        alert(error.response.data.message);
        setLoading(false);
      });
  };

  if (reset) {
    props.history.push("/resetPassword");
  }

  if (loading) {
    form = <Spinner />;
  }

  return (
    <div className={classes.PasswordData}>
      <h4>Forgot Password</h4>
      <form onSubmit={submitHandler}>
        {form}
        <button type="submit">Submit</button>
      </form>
    </div>
  );
});

export default withRouter(ForgotPassword);
