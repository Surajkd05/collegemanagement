import React, { useState, useEffect } from "react";
import classes from "./Register.module.css";
import { updateObject, checkValidity } from "../../../shared/utility";
import Input from "../../../components/UI/Input/Input";
import Spinner from "../../../components/UI/Spinner/Spinner";
import axios from "../../../axios-college";
import { withRouter, Redirect } from "react-router-dom";

const EmployeeRegister = (props) => {

  const [branches, setBranches] = useState(null);

  useEffect(() => {
    axios
      .get("preparation/branch")
      .then((response) => {
        setBranches(response.data);
        setLoading(false);
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
  }, []);

  const [branchId, setBranchId] = useState(null);

  const idChangedHandler = (e) => {
    setBranchId(e.target.value);
  };

  let branchView = null;
  if (branches !== null) {
    branchView = (
      <div className="row" style={{ padding: "10px" }}>
        <div className="col-md-12">
          <div>
            <select
              id="empId"
              className="form-control"
              size="0"
              onChange={idChangedHandler}
            >
              <option value="default">Select Branch</option>
              {branches.map((branch) => (
                <option
                  key={branch.branchId}
                  value={branch.branchId}
                  onChange={idChangedHandler}
                >
                  {branch.branchName}
                </option>
              ))}
            </select>
          </div>
        </div>
      </div>
    );
  }

  const [register, setRegister] = useState({
    username: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter your UserName",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    firstName: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter your FirstName",
      },
      value: "",
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
        placeholder: "Enter your LastName",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
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
    confirmPassword: {
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
    age: {
      elementType: "input",
      elementConfig: {
        type: "number",
        placeholder: "Enter your Age",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    label:{
      elementType:"label"
    },
    dateOfBirth: {
      elementType: "input",
      elementConfig: {
        type: "date",
        placeholder: "Enter your Birth date",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    gender: {
      elementType: "select",
      elementConfig: {
        options: [
          { value: "none", displayValue: "SELECT GENDER" },
          { value: "male", displayValue: "Male" },
          { value: "female", displayValue: "Female" },
        ],
      },
      validation: {},
      value: "none",
      isValid: true,
    },
    mobileNo: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter your Mobile number",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
  });

  const [registered, setRegistered] = useState(false)

  const [loading, setLoading] = useState(false);

  const inputChangedHandler = (event, registerData) => {
    const updatedSchedules = updateObject(register, {
      [registerData]: updateObject(register[registerData], {
        value: event.target.value,
        valid: checkValidity(
          event.target.value,
          register[registerData].validation
        ),
        touched: true,
      }),
    });
    setRegister(updatedSchedules);
  };

  const formElementsArray = [];
  for (let key in register) {
    formElementsArray.push({
      id: key,
      config: register[key],
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
    const registerData = { accountNonLocked: true, branchId: branchId };

    for (let key in register) {
      registerData[key] = register[key].value;
    }

      axios.post(
        "auth/employee",
        registerData
      )
      .then((response) => {
        setLoading(false);
        alert(response.data)
        setRegistered(true)
      })
      .catch((error) => {
        setLoading(false);
        alert(error.response.data.message)
      });
  };

  if (loading) {
    form = <Spinner />;
  }

  if(registered){
    return <Redirect to = "/auth" />
  }

  return (
    <div className={classes.RegisterData}>
      <h4>Employee Register</h4>
      <form onSubmit={submitHandler}>
        {form}
        {branchView !== null ? branchView : null}
        <button type="submit">Register</button>
      </form>
    </div>
  );
};

export default withRouter(EmployeeRegister);
