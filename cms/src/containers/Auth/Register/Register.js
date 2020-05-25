import React, { useState } from "react";
import classes from "./Register.module.css";
import { updateObject, checkValidity } from "../../../shared/utility";
import Input from "../../../components/UI/Input/Input";
import Spinner from "../../../components/UI/Spinner/Spinner";
import axios from "axios";

const Register = (props) => {
  const [register, setRegister] = useState({
    registerAs: {
      elementType: "select",
      elementConfig: {
        options: [
          { value: "none", displayValue: "SELECT ROLE" },
          { value: "std", displayValue: "Student" },
          { value: "emp", displayValue: "Employee" },
        ],
      },
      validation: {},
      value: "none",
      isValid: true,
    },
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
    year: {
      elementType: "select",
      elementConfig: {
        options: [
          { value: "none", displayValue: "SELECT YEAR" },
          { value: "one", displayValue: "One" },
          { value: "two", displayValue: "Two" },
          { value: "three", displayValue: "Three" },
          { value: "four", displayValue: "Four" },
        ],
      },
      validation: {},
      value: "none",
      isValid: true,
    },
    branch: {
      elementType: "select",
      elementConfig: {
        options: [
          { value: "none", displayValue: "SELECT BRANCH" },
          { value: "cse", displayValue: "Computer Science & Engineering" },
          { value: "ce", displayValue: "Civil Engineering" },
          { value: "me", displayValue: "Mechanical Engineering" },
          {
            value: "ece",
            displayValue: "Electronics & Communication Engineering",
          },
          { value: "bt", displayValue: "Bio-Tech Engineering" },
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
          { value: "one", displayValue: "One" },
          { value: "two", displayValue: "Two" },
          { value: "three", displayValue: "Three" },
          { value: "four", displayValue: "Four" },
        ],
      },
      validation: {},
      value: "none",
      isValid: true,
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
    const registerData = { accountNonLocked: true };

    for (let key in register) {
      registerData[key] = register[key].value;
    }

    console.log("registered data is", registerData);
    console.log("Registered as,", (register.registerAs.value));
    let responseData = null;
    if (register.registerAs.value === "emp") {
      console.log("In employe block")
      responseData = axios.post(
        "http://localhost:8080/college/auth/employee",
        registerData
      );
    } else {
      responseData = axios.post(
        "http://localhost:8080/college/auth/student",
        registerData
      );
    }
    responseData
      .then((response) => {
        setLoading(false);
        console.log("Registered data response is", response);
        alert(response.data)
      })
      .catch((error) => {
        setLoading(false);
        console.log("Error is", error);
      });
  };

  if (loading) {
    form = <Spinner />;
  }

  return (
    <div className={classes.RegisterData}>
      <h4>Register</h4>
      <form onSubmit={submitHandler}>
        {form}
        <button type="submit">Register</button>
      </form>
    </div>
  );
};

export default Register;
