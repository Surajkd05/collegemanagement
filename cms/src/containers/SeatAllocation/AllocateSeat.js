import React, { useEffect, useState } from "react";
import { withRouter, Redirect } from "react-router-dom";
import axios from "../../axios-college";
import Button from "../../components/UI/Button/Button";
import classes from "./AllocateSeat.module.css";

const AllocateSeat = (props) => {
  const [employees, setEmployees] = useState(null);
  const [loading, setLoading] = useState(true);
  const [empId, setEmpId] = useState();
  const [allocated, setAllocated] = useState(false);

  const [branches, setBranches] = useState(null);

  const [branchId, setBranchId] = useState(null);

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

  const idChangedHandler1 = (e) => {
    setBranchId(e.target.value);
    axios
      .get("seatAllocation/employee?branchId="+e.target.value)
      .then((response) => {
        console.log("Employees are : ", response.data);
        setEmployees(response.data);
        setLoading(false);
      })
      .catch((error) => {
        console.log("Error is : ", error.response);
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

  const idChangedHandler = (e) => {
    setEmpId(e.target.value);
  };

  const onSubmitHandler = (event) => {
    event.preventDefault();

    const formData = {
      empId: empId,
      seatId: props.history.location.state.seatId,
    };

    axios
      .post("seatAllocation/allocateSeat", formData, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        alert(response.data);
        setAllocated(true);
      })
      .catch((error) => {
        console.log(error.response);
      });
  };

  console.log("Selcted employee props: ", props);

  let allocateSeat = null;

  if (employees !== null) {
    allocateSeat = (
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
  return (
    <div className={classes.LoginData}>
      <h4>Select Employee</h4>
      <form onSubmit={onSubmitHandler}>
        {branchView}
        {allocateSeat}
        <Button btnType="Success"> Submit </Button>
      </form>
    </div>
  );
};

export default withRouter(AllocateSeat);
