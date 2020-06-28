import React, { useState, useEffect } from "react";
import { withRouter } from "react-router-dom";
import { updateObject, checkValidity } from "../../../shared/utility";
import Input from "../../../components/UI/Input/Input";
import Button from "../../../components/UI/Button/Button";
import Spinner from "../../../components/UI/Spinner/Spinner";
import axios from "../../../axios-college";
import classes from "./AddSubject.module.css";

const AddSubject = (props) => {
  const [branches, setBranches] = useState(null);

  const [branchId, setBranchId] = useState(null);

  const [loading, setLoading] = useState(false);

  useEffect(() => {
    axios
      .get("preparation/branch")
      .then((response) => {
        setBranches(response.data);
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
  }, []);

  const [data, setData] = useState({
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
    subjectName: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter subject name",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    subjectCode: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter Subject Code",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
  });

  const inputChangedHandler = (event, dataData) => {
    const updatedSchedules = updateObject(data, {
      [dataData]: updateObject(data[dataData], {
        value: event.target.value,
        valid: checkValidity(event.target.value, data[dataData].validation),
        touched: true,
      }),
    });
    setData(updatedSchedules);
  };

  const formElementsArray = [];
  for (let key in data) {
    formElementsArray.push({
      id: key,
      config: data[key],
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

    const formData = { branchId: branchId };

    for (let key in data) {
      formData[key] = data[key].value;
    }

    axios
      .post("admin/subject", formData, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        alert(response.data);
        setLoading(false);
      })
      .catch((error) => {
        alert(error.response.data.message);
        setLoading(false);
      });
  };

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

  let fullForm = (
    <form>
      {branchView}
      {form}
    </form>
  );

  if (loading) {
    fullForm = <Spinner />;
  }

  return (
    <div className={classes.LoginData}>
      <h4>Add Subject</h4>
      {fullForm}
      <Button btnType="Success" clicked={submitHandler}>
        Submit
      </Button>
    </div>
  );
};

export default withRouter(AddSubject);
