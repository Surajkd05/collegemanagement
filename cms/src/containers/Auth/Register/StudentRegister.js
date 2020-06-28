import React, { useState, useEffect } from "react";
import classes from "./Register.module.css";
import { updateObject, checkValidity } from "../../../shared/utility";
import Input from "../../../components/UI/Input/Input";
import Spinner from "../../../components/UI/Spinner/Spinner";
import axios from "../../../axios-college";
import { withRouter, Redirect } from "react-router-dom";

const StudentRegister = (props) => {
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

    dateOfBirth: {
      elementType: "input",
      elementConfig: {
        type: "date",
        placeholder: "Enter your Birth date",
        onfocus : "Enter your Birth date"
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
    year: {
      elementType: "select",
      elementConfig: {
        options: [
          { value: "none", displayValue: "SELECT YEAR" },
          { value: 1, displayValue: "One" },
          { value: 2, displayValue: "Two" },
          { value: 3, displayValue: "Three" },
          { value: 4, displayValue: "Four" },
        ],
      },
      validation: {},
      value: "none",
      isValid: true,
    },
    section: {
      elementType: "select",
      elementConfig: {
        options: [
          { value: "none", displayValue: "SELECT SECTION" },
          { value: 1, displayValue: "One" },
          { value: 2, displayValue: "Two" },
          { value: 3, displayValue: "Three" },
          { value: 4, displayValue: "Four" },
        ],
      },
      validation: {},
      value: "none",
      isValid: true,
    },

    semester: {
      elementType: "select",
      elementConfig: {
        options: [
          { value: "none", displayValue: "SELECT SEMESTER" },
          { value: 1, displayValue: "One" },
          { value: 2, displayValue: "Two" },
          { value: 3, displayValue: "Three" },
          { value: 4, displayValue: "Four" },
          { value: 5, displayValue: "Five" },
          { value: 6, displayValue: "Six" },
          { value: 7, displayValue: "Seven" },
          { value: 8, displayValue: "Eight" },
        ],
      },
      validation: {},
      value: "none",
      isValid: true,
    },
  });

  const [loading, setLoading] = useState(false);

  const [registered, setRegistered] = useState(false);

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

    axios
      .post("auth/student", registerData)
      .then((response) => {
        setLoading(false);
        alert(response.data);
        setRegistered(true);
      })
      .catch((error) => {
        setLoading(false);
        alert(error.response.data.message);
        console.log("Error is", error);
      });
  };

  if (loading) {
    form = <Spinner />;
  }

  if (registered) {
    return <Redirect to="/auth" />;
  }

  return (
    <div className={classes.RegisterData}>
      <h4> Student Register</h4>
      <form onSubmit={submitHandler}>
        {form}
        {branchView !== null ? branchView : null}
        <button type="submit">Register</button>
      </form>
    </div>
  );
};

export default withRouter(StudentRegister);