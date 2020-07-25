import React, { useState } from "react";
import { updateObject, checkValidity } from "../../shared/utility";
import Spinner from "../../components/UI/Spinner/Spinner";
import Input from "../../components/UI/Input/Input";
import Button from "../../components/UI/Button/Button";
import classes from "./Admin.module.css";
import UserView from "../../components/admin/UserView";
import axios from "../../axios-college";
import Aux from "../../hoc/Aux1/aux1";

const Admin = React.memo((props) => {
  const [params, setParams] = useState({
    role: {
      elementType: "select",
      elementConfig: {
        options: [
          { value: "none", displayValue: "SELECT USER TYPE" },
          { value: "emp", displayValue: "Employee" },
          { value: "std", displayValue: "Student" },
        ],
      },
      validation: {},
      value: "none",
      isValid: true,
    },
    SortBy: {
      elementType: "select",
      elementConfig: {
        options: [
          { value: "none", displayValue: "SELECT SORT BY" },
          { value: "userId", displayValue: "UserId" },
        ],
      },
      validation: {},
      value: "none",
      isValid: true,
    },
    page: {
      elementType: "input",
      elementConfig: {
        type: "number",
        placeholder: "Enter Page Number",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    size: {
      elementType: "input",
      elementConfig: {
        type: "number",
        placeholder: "Enter Size",
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
  const [users, setUsers] = useState([]);

  const submitHandler = (event) => {
    event.preventDefault();
    setLoading(true);
    let result = null;

    const paramData = {};

    let query = "?";
    for (let key in params) {
      if (key !== "role") {
        if (key !== "SortBy") {
          query = query + "&" + key + "=" + params[key].value;
        } else {
          query = query + key + "=" + params[key].value;
        }
      }
    }

    for (let key in params) {
      paramData[key] = params[key].value;
    }

    let fetchedData = null;

    if (paramData.role === "emp") {
      fetchedData = axios.get(
        "admin/employees" + query,
        {
          headers: { Authorization: "Bearer " + localStorage.getItem("token") },
        }
      );
      setLoading(false);
    } else {
      fetchedData = axios.get(
        "admin/students" + query,
        {
          headers: { Authorization: "Bearer " + localStorage.getItem("token") },
        }
      );
      setLoading(false);
    }

    fetchedData
      .then((response) => {
        result = response.data;
        setUsers(result);
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
  };

  const inputChangedHandler = (event, paramName) => {
    const updatedSchedules = updateObject(params, {
      [paramName]: updateObject(params[paramName], {
        value: event.target.value,
        valid: checkValidity(event.target.value, params[paramName].validation),
        touched: true,
      }),
    });
    setParams(updatedSchedules);
  };

  const userViewList = (l) => {
    if (l > 0) {
      return <UserView fetchedUsers={users} userRole={params.role.value} />;
    }
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
      <div className={classes.AdminData}>
        <form onSubmit={submitHandler}>
          <h4>Enter User Type</h4>
          {form}
          <Button btnType="Success">Get Users</Button>
        </form>
      </div>
      <div>
        <section>{userViewList(users.length)}</section>
      </div>
    </Aux>
  );
});

export default Admin;
