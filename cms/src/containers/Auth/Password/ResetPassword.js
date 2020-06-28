import React, { useState } from "react";
import classes from "./Password.module.css";
import { updateObject, checkValidity } from "../../../shared/utility";
import Input from "../../../components/UI/Input/Input";
import Spinner from "../../../components/UI/Spinner/Spinner";
import axios from "../../../axios-college"
import { withRouter } from "react-router-dom";

const ResetPassword = React.memo((props) => {
  const [info, setInfo] = useState({
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
    pass: {
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

    cpass: {
        elementType: "input",
        elementConfig: {
          type: "password",
          placeholder: "Confirm your Password",
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
      token: {
        elementType: "input",
        elementConfig: {
          type: "text",
          placeholder: "Enter token sent to your mail",
        },
        value: "",
        validation: {
          required: true,
        },
        isValid: false,
        touched: false,
      },
  });

  const [loading, setLoading] = useState(false)

  const inputChangedHandler = (event, infoData) => {
    const updatedSchedules = updateObject(info, {
      [infoData]: updateObject(info[infoData], {
        value: event.target.value,
        valid: checkValidity(event.target.value, info[infoData].validation),
        touched: true,
      }),
    });
    setInfo(updatedSchedules);
  };

  const formElementsArray = [];
  for (let key in info) {
    formElementsArray.push({
      id: key,
      config: info[key],
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
    setLoading(true)

    let query = "?"

    for(let key in info){
        if (key !== "email") {
            query = query + "&" + key + "=" + info[key].value;
          } else {
            query = query + key + "=" + info[key].value;
          }
    }

    axios.patch("forgotPassword/resetPassword"+query)
    .then(response => {
      alert(response.data)
      setLoading(false)
    }).catch(error => {
      alert(error.response.data.message)
      setLoading(false)
    })
  };

  if (loading) {
    form = <Spinner />;
  }

  return (
    <div className={classes.PasswordData}>
      <h4>Reset Password</h4>
      <form onSubmit={submitHandler}>
        {form}
        <button type="submit">Submit</button>
      </form>
    </div>
  );
});

export default withRouter(ResetPassword);
