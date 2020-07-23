import React, { useEffect, useState } from "react";
import { withRouter } from "react-router-dom";
import axios from "../../axios-college";
import Button from "../../components/UI/Button/Button";
import AddQuestion from "./Question/AddQuestion/AddQuestion";
import ViewQuestion from "./Question/ViewQuestion/ViewQuestion";
import classes from "./Preparation.module.css";

const Preparation = (props) => {
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

  const [addQuestion, setAddQuestion] = useState(false);

  const [viewQuestion, setViewQuestion] = useState(false);

  const idChangedHandler = (e) => {
    setBranch(false);
    setBranchId(e.target.value);
    setBranch(true);
    setViewQuestion(false);
    setAddQuestion(false);
  };

  const addQuestionHandler = () => {
    setAddQuestion(true);
    setViewQuestion(false);
  };

  const viewQuestionHandler = () => {
    setAddQuestion(false);
    setViewQuestion(true);
  };

  const interviewTimeHandler = () => {
    props.history.push("/interview")
  }

  let preparationView = null;

  if (!loading) {
    preparationView = (
      <div>
        <div className="row">
          <div className="col-md-10"></div>
          <div className="col-md-2">
            <Button btnType="Success" clicked={() => interviewTimeHandler()}>
              <h4>Interview Time?</h4>
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
              <Button
                btnType="Success"
                clicked={() => addQuestionHandler(branchId)}
              >
                +AddQuestion
              </Button>
            </div>
            <div className="col-md-3">
              <Button btnType="Success" clicked={() => viewQuestionHandler()}>
                View Question
              </Button>
            </div>
            <div className="col-md-3"></div>
          </div>
        ) : null}
        {addQuestion ? (
          <div className="row">
            <div className="col-md-2" />
            <div className="col-md-8">
              <AddQuestion branchId={branchId} />
            </div>
            <div className="col-md-2" />
          </div>
        ) : null}
        {viewQuestion ? (
           props.history.push({
            pathname: "/question",
            state: {
              branchId: branchId,
            },
          })
        ) : null}
      </div>
    );
  }

  return <div>{preparationView}</div>;
};

export default withRouter(Preparation);
