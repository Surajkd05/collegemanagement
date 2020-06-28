import React, { useState } from "react";
import { updateObject, checkValidity } from "../../../shared/utility";
import Spinner from "../../../components/UI/Spinner/Spinner";
import Input from "../../../components/UI/Input/Input";
import Button from "../../../components/UI/Button/Button";
import classes from "./UpdatePassword.module.css";
import axios from "../../../axios-college";
import Aux from "../../../hoc/Aux/aux";
import { withRouter } from "react-router-dom";

const UpdateUserPassword = React.memo((props) => {
  const [params, setParams] = useState({
    oldPass: {
      elementType: "input",
      elementConfig: {
        type: "password",
        placeholder: "Enter your old password",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    newPass: {
      elementType: "input",
      elementConfig: {
        type: "password",
        placeholder: "Enter your new password",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    confirmPass: {
        elementType: "input",
        elementConfig: {
          type: "password",
          placeholder: "Confirm your new password",
        },
        value: "",
        validation: {
          required: true,
        },
        isValid: false,
        touched: false,
      },
  });

  const [loading, setLoading] = useState(false);

  const submitHandler = (event) => {
    event.preventDefault();
    setLoading(true);
    let query = "?";

    for (let key in params) {
      if (key !== "oldPass") {
        query = query + "&" + key + "=" + params[key].value;
      } else {
        query = query + key + "=" + params[key].value;
      }
    }


      axios({
        method: "PATCH",
        url: "user/pass"+query,
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
      .then((response) => {
        setLoading(false);
        alert(response.data)
      })
      .catch((error) => {
        alert(error.response.data.message);
        setLoading(false);
      });
  };

  const inputChangedHandler = (event, paramName) => {
    const updatedData = updateObject(params, {
      [paramName]: updateObject(params[paramName], {
        value: event.target.value,
        valid: checkValidity(event.target.value, params[paramName].validation),
        touched: true,
      }),
    });
    setParams(updatedData);
  };

  const formElementsArray = [];
  for (let key in params) {
    formElementsArray.push({
      id: key,
      config: params[key],
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

  return (
    <Aux>
      <div className={classes.PasswordData}>
        <form onSubmit={submitHandler}>
          <h4>Update Password</h4>
          {form}
          <Button btnType="Success">Submit</Button>
        </form>
      </div>
    </Aux>
  );
});

export default withRouter(UpdateUserPassword);
