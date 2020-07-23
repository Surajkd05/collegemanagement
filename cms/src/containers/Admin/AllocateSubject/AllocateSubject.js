import React, { useEffect, useState } from "react";
import axios from "../../../axios-college";
import { withRouter } from "react-router-dom";
import { updateObject, checkValidity } from "../../../shared/utility";
import Input from "../../../components/UI/Input/Input";
import classes from "./AllocateSubject.module.css";
import Button from "../../../components/UI/Button/Button";
import Spinner from "../../../components/UI/Spinner/Spinner";

const AllocateSubject = (props) => {
  const [branches, setBranches] = useState(null);

  const [employees, setEmployees] = useState(null);

  const [branchId, setBranchId] = useState(null);

  const [subjects, setSubjects] = useState(null);

  const [empId, setEmpId] = useState();

  const [subId, setSubId] = useState();

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

  const idChangedHandler = (e) => {
    setEmpId(e.target.value);
  };

  const idChangedHandler1 = (e) => {
    setBranchId(e.target.value);
    axios
      .get("admin/employee?branchId=" + e.target.value, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        console.log("Employees are : ", response.data);
        setEmployees(response.data);
      })
      .catch((error) => {
        console.log("Error is : ", error.response);
      });
  };

  const idChangedHandler2 = (event) => {
    setSubId(event.target.value);
  };

  const yearChangedHandler = (event) => {
    axios
      .get(
        "admin/subject?branchId=" + branchId + "&year=" + event.target.value,
        {
          headers: { Authorization: "Bearer " + localStorage.getItem("token") },
        }
      )
      .then((response) => {
        setSubjects(response.data);
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
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
              onChange={idChangedHandler1}
            >
              <option value="default">Select Branch</option>
              {branches.map((branch) => (
                <option
                  key={branch.branchId}
                  value={branch.branchId}
                  onChange={idChangedHandler1}
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

  let employeeView = null;
  if (employees !== null) {
    employeeView = (
      <div className="row" style={{ padding: "10px" }}>
        <div className="col-md-12">
          <div>
            <select
              id="empId"
              className="form-control"
              size="0"
              onChange={idChangedHandler}
            >
              <option value="default">Select Employee</option>
              {employees.map((employee) => (
                <option
                  key={employee.userId}
                  value={employee.userId}
                  onChange={idChangedHandler}
                >
                  {employee.firstName + " " + employee.lastName}
                </option>
              ))}
            </select>
          </div>
        </div>
      </div>
    );
  }

  let subjectView = null;
  if (subjects !== null) {
    subjectView = (
      <div className="row" style={{ padding: "10px" }}>
        <div className="col-md-12">
          <div>
            <select
              id="subId"
              className="form-control"
              size="0"
              onChange={idChangedHandler2}
            >
              <option value="default">Select Subject</option>
              {subjects.map((subject) => (
                <option
                  key={subject.id}
                  value={subject.id}
                  onChange={idChangedHandler2}
                >
                  {subject.subjectCode + " - " + subject.subjectName}
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
      {yearView}
      {employeeView}
      {subjectView}
    </form>
  );

  const onSubmitHandler = (event) => {
    event.preventDefault();
    setLoading(true);

    const formData = {
      empId: empId,
      subId: subId,
    };

    axios
      .post("admin/allocate", formData, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        alert(response.data);
        setLoading(false);
      })
      .catch((error) => {
        setLoading(false);
        alert(error.response.data.message);
      });
  };

  if (loading) {
    fullForm = <Spinner />;
  }

  return (
    <div className={classes.LoginData}>
      <h4>Allocate Subject</h4>
      {fullForm}
      <Button btnType="Success" clicked={onSubmitHandler}>
        Submit
      </Button>
    </div>
  );
};

export default withRouter(AllocateSubject);
