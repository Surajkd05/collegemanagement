import React, { useEffect, useState } from "react";
import { withRouter } from "react-router-dom";
import axios from "../../../axios-college";
import Button from "../../../components/UI/Button/Button";
import classes from "./Interview.module.css";
import AddTip from "./AddTip/AddTip";
import ViewTip from "./ViewTip/ViewTip";

const Interview = (props) => {
  const [branches, setBranches] = useState();
  const [loading, setLoading] = useState(true);

  const [courses, setCourses] = useState(null);
  const [courseId, setCourseId] = useState(null);

  const [branch1, setBranch1] = useState(false);

  useEffect(() => {
    axios
      .get("app/course")
      .then((response) => {
        setCourses(response.data);
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
    props.history.push("/preparation");
  };

  const idChangedHandler1 = (e) => {
    setCourseId(e.target.value);

    axios
      .get("app/branch?courseId=" + e.target.value)
      .then((response) => {
        setBranches(response.data);
        setBranch1(true);
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
  };

  let courseView = null;
  if (courses !== null) {
    courseView = (
      <div className="row">
        <div className="col-md-4"></div>
        <div className="col-md-4">
          <div>
            <select
              id="courseId"
              className="form-control"
              size="0"
              onChange={idChangedHandler1}
            >
              <option value="default">Select Course</option>
              {courses.map((course) => (
                <option
                  key={course.courseId}
                  value={course.courseId}
                  onChange={idChangedHandler1}
                >
                  {course.courseName}
                </option>
              ))}
            </select>
          </div>
        </div>
        <div className="col-md-4"></div>
      </div>
    );
  }

  let branchView = null;
  if (branch1) {
    branchView = (
      <div className="row">
        <div className="col-md-4"></div>
        <div className="col-md-4">
          <div>
            <select
              id="branchId"
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
    );
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
        {courseView}
        {branchView}
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
