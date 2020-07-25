import React, { useState } from "react";
import { updateObject, checkValidity } from "../../../shared/utility";
import Input from "../../../components/UI/Input/Input";
import classes from "./UpdateProfile.module.css";
import Button from "../../../components/UI/Button/Button";
import Spinner from "../../../components/UI/Spinner/Spinner";
import axios from "../../../axios-college";
import { connect } from "react-redux";
import { Redirect, withRouter } from "react-router-dom";
import Aux from "../../../hoc/Aux1/aux1";

const UpdateStudentProfile = (props) => {
  const oldProfile = props.history.location.state.profile
  const [params, setParams] = useState({
    firstName: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter your first name",
      },
      value: oldProfile.firstName,
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    lastName: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter your last name",
      },
      value: oldProfile.lastName,
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    mobileNo: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter your mobile number",
      },
      value: oldProfile.mobileNo,
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    branch: {
        elementType: "input",
        elementConfig: {
          type: "text",
          placeholder: "Enter your branch",
        },
        value: oldProfile.branch,
        validation: {
          required: true,
        },
        isValid: false,
        touched: false,
      },
      year: {
        elementType: "input",
        elementConfig: {
          type: "text",
          placeholder: "Enter your year",
        },
        value: oldProfile.year,
        validation: {
          required: true,
        },
        isValid: false,
        touched: false,
      },
      section: {
        elementType: "input",
        elementConfig: {
          type: "text",
          placeholder: "Enter your section",
        },
        value: oldProfile.section,
        validation: {
          required: true,
        },
        isValid: false,
        touched: false,
      },
  });

  const [loading, setLoading] = useState(false);
  const [updated, setUpdated] = useState(false);

  const submitHandler = (event) => {
    event.preventDefault();
    setLoading(true);
    const formData = {};

    for (let key in params) {
      formData[key] = params[key].value;
    }

    axios({
      method: "PUT",
      url: "user/student",
      data: formData,
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
    })
      .then((response) => {
        setLoading(false);
        alert(response.data);
        setUpdated(true);
      })
      .catch((error) => {
        setLoading(false);
        alert(error.response.data.message);
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
  if (updated) {
    return <Redirect to="/profile" />;
  }

  return (
    <div className={classes.ProfileData}>
      <h4>Update Profile</h4>
      <form>{form}</form>
      <Button clicked={submitHandler} btnType="Success">
        Update
      </Button>
    </div>
  );
};

// const mapDispatchToProps =(dispatch) => {
//   return{
//     onUpdateProfile: (updatedData)
//   }
// }

export default withRouter(UpdateStudentProfile);
