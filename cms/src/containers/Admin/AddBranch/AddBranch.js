import React, { useState, useEffect } from "react";
import { withRouter } from "react-router-dom";
import { updateObject, checkValidity } from "../../../shared/utility";
import Input from "../../../components/UI/Input/Input";
import Button from "../../../components/UI/Button/Button";
import Spinner from "../../../components/UI/Spinner/Spinner";
import classes from "./AddBranch.module.css";
import axios from "../../../axios-college";

const AddBranch = (props) => {
  const [courses, setCourses] = useState(null);
  const [courseId, setCourseId] = useState(null);
  const [branch, setBranch] = useState({
    branchName: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter new branch name",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
  });

  useEffect(() => {
    axios
      .get("app/course")
      .then((response) => {
        setCourses(response.data);
      })
      .catch((error) => {
        alert(error.response.data.error);
      });
  }, []);

  const [loading, setLoading] = useState(false);

  const inputChangedHandler = (event, branchData) => {
    const updatedSchedules = updateObject(branch, {
      [branchData]: updateObject(branch[branchData], {
        value: event.target.value,
        valid: checkValidity(event.target.value, branch[branchData].validation),
        touched: true,
      }),
    });
    setBranch(updatedSchedules);
  };

  const formElementsArray = [];
  for (let key in branch) {
    formElementsArray.push({
      id: key,
      config: branch[key],
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

  const idChangedHandler = (e) => {
    setCourseId(e.target.value);
  };

  let courseView = null;
  if (courses !== null) {
    courseView = (
      <div className="row" style={{ padding: "10px" }}>
        <div className="col-md-12">
          <div>
            <select
              id="empId"
              className="form-control"
              size="0"
              onChange={idChangedHandler}
            >
              <option value="default">Select Course</option>
              {courses.map((course) => (
                <option
                  key={course.courseId}
                  value={course.courseId}
                  onChange={idChangedHandler}
                >
                  {course.courseName}
                </option>
              ))}
            </select>
          </div>
        </div>
      </div>
    );
  }

  const submitHandler = (event) => {
    event.preventDefault();
    setLoading(true);

    const formData = { courseId: courseId};

    for (let key in branch) {
      formData[key] = branch[key].value;
    }

    axios
      .post("admin/branch", formData, {
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

  let fullForm = (
    <div>
      {courseView}
      {form}
    </div>
  );

  if (loading) {
    fullForm = <Spinner />;
  }

  return (
    <div className={classes.BranchData}>
      <h4>Branch</h4>
      <form>
        {fullForm}
        <Button btnType="Success" clicked={submitHandler}>
          AddBranch
        </Button>
      </form>
    </div>
  );
};

export default withRouter(AddBranch);
