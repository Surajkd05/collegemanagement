import React, { useState,useEffect } from "react";
import { updateObject, checkValidity } from "../../../shared/utility";
import Spinner from "../../../components/UI/Spinner/Spinner";
import Input from "../../../components/UI/Input/Input";
import Button from "../../../components/UI/Button/Button";
import classes from "./StudentView.module.css";
import UserView from "../../../components/admin/UserView";
import axios from "../../../axios-college";
import Aux from "../../../hoc/Aux1/aux1";

const StudentView = React.memo((props) => {
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

  const [params, setParams] = useState({
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
  const [users, setUsers] = useState([]);

  const submitHandler = (event) => {
    event.preventDefault();
    setLoading(true);

    let query = "?branchId="+branchId;
    for (let key in params) {
          query = query + "&" + key + "=" + params[key].value;
      }

   axios.get("employee/student" + query, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        setUsers(response.data);
        setLoading(false);
      })
      .catch((error) => {
        alert(error.response.data.message);
        setLoading(false);
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
      return <UserView fetchedUsers={users} userRole={"std"} />;
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

  let fullForm = (
    <div>
      {branchView}
      {form}
    </div>
  );

  if (loading) {
    fullForm = <Spinner />;
  }

  return (
    <Aux>
      <div className={classes.AdminData}>
        <form onSubmit={submitHandler}>
          <h4>Enter Branch Details</h4>
          {fullForm}
          <Button btnType="Success">Get Users</Button>
        </form>
      </div>
      <div>
        <section>{userViewList(users.length)}</section>
      </div>
    </Aux>
  );
});

export default StudentView;
