import React, { useEffect, useState } from "react";
import { withRouter } from "react-router-dom";
import Button from "../../components/UI/Button/Button";
import axios from "../../axios-college";
import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";
import "react-super-responsive-table/dist/SuperResponsiveTableStyle.css";
import classes from "./Placement.module.css";

const Placement = (props) => {
  const [placedData, setPlacedData] = useState(null);

  const [count, setCount] = useState(0);

  const [url, setUrl] = useState("placement/all?page=");

  const [name, setName] = useState("Search student by name");

  const [branch, setBranch] = useState(false);

  const [branchId, setBranchId] = useState();

  const [branches, setBranches] = useState(null);

  const [branchCount, setBranchCount] = useState(0);

  useEffect(() => {
    axios
      .get("placement/all")
      .then((response) => {
        setPlacedData(response.data);
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
  }, []);

  const onClickHandler = (url) => {
    setUrl(url);
    axios
      .get(url + 0)
      .then((response) => {
        setPlacedData(response.data);
        setBranch(false);
        setName("Search student by name");
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
  };

  const branchChangeHandler = () => {
    setBranch(!branch);
    setPlacedData(null);
    if (branchCount === 0) {
      setBranchCount(1);
      axios
        .get("preparation/branch")
        .then((response) => {
          setBranches(response.data);
        })
        .catch((error) => {
          alert(error.response.data.message);
        });
    }
  };

  const onChangeHandler = (event) => {
    setName(event.target.value);
  };

  const pageChangeHandler = (page) => {
    setCount(page);
    axios
      .get(url + page)
      .then((response) => {
        setPlacedData(response.data);
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
  };

  const addPlacementHandler = () => {
    props.history.push("/addPlacement");
  };

  const idChangedHandler = (e) => {
    setBranchId(e.target.value);
  };

  const viewCompanyHandler = (placementId, studentId) => {
      props.history.push({
          pathname:"/viewPlacement",
          state: {
              placementId : placementId,
              studentId : studentId
          }
      })
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

  let placedView = null;
  if (placedData !== null) {
    placedView = (
      <section className={classes.UserView}>
        <h4>Fetched Placement Details</h4>
        <Table>
          <Thead>
            <Tr>
              <Th>S.No</Th>
              <Th>Student Name</Th>
              <Th>Branch Name</Th>
              <Th>Number of Companies</Th>
              <Th>View Companies</Th>
            </Tr>
          </Thead>
          <Tbody>
            {placedData.map((data, count) => (
              <Tr key={data.placementId}>
                <Td key={data.placementId}>{count + 1}</Td>

                <Td>{data.studentName}</Td>
                <Td>{data.branchName}</Td>
                <Td>{data.companies}</Td>
                <Td>
                  <Button
                    clicked={() => viewCompanyHandler(data.placementId,data.studentId)}
                    btnType="Success"
                  >
                    View
                  </Button>
                </Td>
              </Tr>
            ))}
          </Tbody>
        </Table>
      </section>
    );
  }

  return (
    <div>
      <div className="row">
        <div className="col-md-4" />
        <div className="col-md-4">
          <input type="text" value={name} onChange={onChangeHandler} />
          <Button
            btnType="Success"
            clicked={() =>
              onClickHandler("placement/student?studentName=" + name + "&page=")
            }
          >
            <i className="fa fa-search"></i>
          </Button>
        </div>
        <div className="col-md-2" />
        <div className="col-md-2">
          {localStorage.getItem("role") === "std" ? (
            <Button btnType="Success" clicked={addPlacementHandler}>
              Add Placement Data
            </Button>
          ) : null}
        </div>
      </div>

      <div className="row">
        <div className="col-md-4" />
        <div className="col-md-4">
          <Button
            btnType="Success"
            clicked={() => onClickHandler("placement/all?page=")}
          >
            View All
          </Button>

          <Button btnType="Success" clicked={() => branchChangeHandler()}>
            View By Branch
          </Button>
        </div>
        <div className="col-md-4" />
      </div>

      <div>
        {" "}
        {branch ? (
          <div className={classes.LoginData}>
            <h4>View By Branch</h4>
            {branchView}
            <Button
              btnType="Success"
              clicked={() =>
                onClickHandler(
                  "placement/branch?branchId=" + branchId + "&page="
                )
              }
            >
              Submit
            </Button>
          </div>
        ) : null}
      </div>

      {placedData !== null ? (
        <div>
          {placedView}
          <div className="row" style={{ paddingLeft: "30px" }}>
            <div className="col-md-1">
              {count !== 0 ? (
                <Button
                  btnType="Success"
                  clicked={() => pageChangeHandler(count - 1)}
                >
                  Prev
                </Button>
              ) : null}
            </div>
            <div className="col-md-10" />
            <div className="col-md-1">
              <Button
                btnType="Success"
                clicked={() => pageChangeHandler(count + 1)}
              >
                Next
              </Button>
            </div>
          </div>
        </div>
      ) : null}
    </div>
  );
};

export default withRouter(Placement);
