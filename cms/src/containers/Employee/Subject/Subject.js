import React, { useState, useEffect } from "react";
import { withRouter } from "react-router-dom";
import { updateObject, checkValidity } from "../../../shared/utility";
import Input from "../../../components/UI/Input/Input";
import Button from "../../../components/UI/Button/Button";
import Spinner from "../../../components/UI/Spinner/Spinner";
import axios from "../../../axios-college";
import classes from "./Subject.module.css";

const Subject = (props) => {
  const [branches, setBranches] = useState(null);

  const [branchId, setBranchId] = useState();

  const [loading, setLoading] = useState(false);

  const [year, setYear] = useState(null);

  const [subjectId, setSubjectId] = useState(null);

  const [subjectCode, setSubjectCode] = useState(null);

  const [subjects, setSubjects] = useState(null);

  const [info, setInfo] = useState("Enter subject info");

  useEffect(() => {
    axios
      .get("preparation/branch")
      .then((response) => {
        setBranches(response.data);
      })
      .catch((error) => {
        alert(error.response.data.message);
      });

    axios
      .get("employee/subject1", {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        setSubjects(response.data);
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
  }, []);

  const [data, setData] = useState({
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
  });

  const inputChangeHandler1 = (event) => {
    setInfo(event.target.value);
  };

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

    const formData = { branchId: branchId, subjectId: subjectId, year: year , data : info};

    for (let key in data) {
      formData[key] = data[key].value;
    }

    axios
      .post("employee/subject", formData, {
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

  const idChangedHandler = (event) => {
    setBranchId(event.target.value);
  };

  const subjectChangedHandler = (event) => {
    setSubjectId(event.target.value);
  };

  const codeChangedHandler = (event) => {
    setSubjectCode(event.target.value);
  };

  const yearChangedHandler = (event) => {
    setYear(event.target.value);
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

  let subjectName = null;
  if (subjects !== null) {
    subjectName = (
      <div className="row" style={{ padding: "10px" }}>
        <div className="col-md-12">
          <div>
            <select
              id="empId"
              className="form-control"
              size="0"
              onChange={subjectChangedHandler}
            >
              <option value="default">Select Subject Name</option>
              {subjects.map((subject) => (
                <option
                  key={subject.id}
                  value={subject.id}
                  onChange={subjectChangedHandler}
                >
                  {subject.subjectName}
                </option>
              ))}
            </select>
          </div>
        </div>
      </div>
    );
  }

  let subjectCodes = null;
  if (subjects !== null) {
    subjectCodes = (
      <div className="row" style={{ padding: "10px" }}>
        <div className="col-md-12">
          <div>
            <select
              id="empId"
              className="form-control"
              size="0"
              onChange={codeChangedHandler}
            >
              <option value="default">Select Subject Code</option>
              {subjects.map((subject) => (
                <option
                  key={subject.id}
                  value={subject.subjectCode}
                  onChange={codeChangedHandler}
                >
                  {subject.subjectCode}
                </option>
              ))}
            </select>
          </div>
        </div>
      </div>
    );
  }

  let yearView = (
    <div className="row" style={{ padding: "10px" }}>
      <div className="col-md-12">
        <div>
          <select
            id="empId"
            className="form-control"
            size="0"
            onChange={yearChangedHandler}
          >
            <option value="default">Select Year</option>

            <option key="1" value="1" onChange={yearChangedHandler}>
              One
            </option>
            <option key="2" value="2" onChange={yearChangedHandler}>
              Two
            </option>
            <option key="3" value="3" onChange={yearChangedHandler}>
              Three
            </option>
            <option key="4" value="4" onChange={yearChangedHandler}>
              Four
            </option>
          </select>
        </div>
      </div>
    </div>
  );

  let fullForm = (
    <form>
      {branchView}
      {yearView}
      {form}
      {subjectCodes}
      {subjectName}
      <div className="form-group green-border-focus" style={{padding:"10px"}}>
        {" "}
        <textarea
          value={info}
          onChange={inputChangeHandler1}
          rows="5"
          cols="90"
          className="form-control"
        />
      </div>
    </form>
  );

  if (loading) {
    fullForm = <Spinner />;
  }

  return (
    <div className={classes.LoginData}>
      <h4>Add Subject Info</h4>
      {fullForm}
      <Button btnType="Success" clicked={submitHandler}>
        Submit
      </Button>
    </div>
  );
};

export default withRouter(Subject);
