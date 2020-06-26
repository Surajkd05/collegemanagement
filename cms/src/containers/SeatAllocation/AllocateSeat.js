import React, { useEffect, useState } from "react";
import { withRouter, Redirect } from "react-router-dom";
import axios from "../../axios-college";
import Button from "../../components/UI/Button/Button";

const AllocateSeat = (props) => {
  const [employees, setEmployees] = useState();
  const [loading, setLoading] = useState(true);
  const [empId, setEmpId] = useState();
  const [allocated, setAllocated] = useState(false);

  useEffect(() => {
    axios
      .get("admin/home/employees1", {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        console.log("Employees are : ", response.data);
        setEmployees(response.data);
        setLoading(false);
      })
      .catch((error) => {
        console.log("Error is : ", error.response);
      });
  }, []);

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
      .post(
        "seatAllocation/allocateSeat",
        formData,
        {
          headers: { Authorization: "Bearer " + localStorage.getItem("token") },
        }
      )
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

  if (!loading) {
    allocateSeat = (
      <div
        className="container"
        style={{ width: "50%", border: "2px solid green", padding: "50px" }}
      >
        <form onSubmit={onSubmitHandler}>
          <div className="col-lg-9">
            <h4>Select Employee</h4>
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
          <Button btnType="Success"> Submit </Button>
        </form>
      </div>
    );
  }
  return <div>{allocateSeat}</div>;
};

export default withRouter(AllocateSeat);
