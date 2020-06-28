import React, { useEffect, useState } from "react";
import { withRouter } from "react-router-dom";
import axios from "../../../axios-college";
import Button from "../../../components/UI/Button/Button";
// import addTip from "./Question/addTip/addTip";
// import viewTip from "./Question/viewTip/viewTip";
import classes from "./Interview.module.css";
import AddTip from "./AddTip/AddTip";
import ViewTip from "./ViewTip/ViewTip";

const Interview = (props) => {
  const [branches, setBranches] = useState();
  const [loading, setLoading] = useState(true);

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

  const [branchId, setBranchId] = useState();

  const [branch, setBranch] = useState(false);

  const [addTip, setAddTip] = useState(false);

  const [viewTip, setViewTip] = useState(false);

  const idChangedHandler = (e) => {
    setBranch(false);
    setBranchId(e.target.value);
    setBranch(true);
    setViewTip(false);
    setAddTip(false);
  };

  const addTipHandler = () => {
    setAddTip(true);
    setViewTip(false);
  };

  const viewTipHandler = () => {
    setAddTip(false);
    setViewTip(true);
  };

  const questionTimeHandler = () => {
      props.history.push("/preparation")
  }

  let preparationView = null;

  if (!loading) {
    preparationView = (
      <div>
        <div className="row">
          <div className="col-md-10"></div>
          <div className="col-md-2">
            <Button btnType="Success" clicked={() => questionTimeHandler()}>
              <h4>Question Time?</h4>
            </Button>
          </div>
        </div>
        <div className="row">
          <div className="col-md-4"></div>
          <div className="col-md-4">
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
          <div className="col-md-4"></div>
        </div>
        {branch ? (
          <div className="row">
            <div className="col-md-4"></div>
            <div className="col-md-3">
              <Button btnType="Success" clicked={() => addTipHandler(branchId)}>
                +AddTip
              </Button>
            </div>
            <div className="col-md-3">
              <Button btnType="Success" clicked={() => viewTipHandler()}>
                View Tips
              </Button>
            </div>
            <div className="col-md-3"></div>
          </div>
        ) : null}
        {addTip ? (
          <div className="row">
            <div className="col-md-2" />
            <div className="col-md-8">
              <AddTip branchId={branchId} />
            </div>
            <div className="col-md-2" />
          </div>
        ) : null}
        {viewTip ? (
          <div className="row">
            <div className="container-fluid">
              <div className="col-md-2" />
              <div className="col-md-8">
                <div className={classes.Box}>
                  <ViewTip branchId={branchId} />
                </div>
              </div>
              <div className="col-md-2" />
            </div>
          </div>
        ) : null}
      </div>
    );
  }

  return <div>{preparationView}</div>;
};

export default withRouter(Interview);
