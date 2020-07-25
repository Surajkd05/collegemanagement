import React, { useState } from "react";
import Input from "../../../../components/UI/Input/Input";
import { updateObject, checkValidity } from "../../../../shared/utility";
import classes from "../Login.module.css";
import Button from "../../../../components/UI/Button/Button";
import axios from "../../../../axios-college";
import Spinner from "../../../../components/UI/Spinner/Spinner";
import { Redirect, withRouter } from "react-router-dom";
import Aux from "../../../../hoc/Aux1/aux1";

const ResendActivation = (props) => {

  const [resendForm, setResendForm] = useState({
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
  });

  const [loading, setLoading] = useState(false);

  const [isLogin, setIsLogin] = useState(false);

  const inputChangedHandler = (event, resendData) => {
    const updatedSchedules = updateObject(resendForm, {
      [resendData]: updateObject(resendForm[resendData], {
        value: event.target.value,
        valid: checkValidity(
          event.target.value,
          resendForm[resendData].validation
        ),
        touched: true,
      }),
    });
    setResendForm(updatedSchedules);
  };

  const submitHandler = (event) => {
    event.preventDefault();
    let query = "?";

    for (let key in resendForm) {
      query = query + key + "=" + resendForm[key].value;
    }

    axios({
      method: "POST",
      url: "auth/resend-activation" + query,
    })
      .then((response) => {
        setLoading(false);
        alert(response.data);
        setIsLogin(true);
      })
      .catch((error) => {
        alert(error.response.data.message);
        setLoading(false);
      });
  };

  const formElementsArray = [];
  for (let key in resendForm) {
    formElementsArray.push({
      id: key,
      config: resendForm[key],
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

  if (loading) {
    form = <Spinner />;
  }

  let redirectLogin = null;
  if (isLogin) {
    props.history.push("/auth");
  }
  return (
    <Aux>
      {redirectLogin}
      <div className={classes.LoginData}>
        <h4>Resend Activation Code</h4>
        <form>{form}</form>
        <Button btnType="Success" clicked={submitHandler}>
          Submit
        </Button>
      </div>
    </Aux>
  );
};

export default withRouter(ResendActivation);
