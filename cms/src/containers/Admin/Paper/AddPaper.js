import React, { useEffect, useState } from "react";
import axios from "../../../axios-college";
import { updateObject, checkValidity } from "../../../shared/utility";
import Input from "../../../components/UI/Input/Input";
import classes from "./AddPaper.module.css";
import Spinner from "../../../components/UI/Spinner/Spinner";
import { withRouter } from "react-router-dom";
import Button from "../../../components/UI/Button/Button";


const AddPaper = (props) => {
  const [branches, setBranches] = useState();
  const [loading, setLoading] = useState(false);

  const [disabled, setDisabled] = useState(true)

  const [courses, setCourses] = useState(null);
  const [courseId, setCourseId] = useState(null);

  const [branch1, setBranch1] = useState(false);

  const [branchId, setBranchId] = useState();

  const [branch, setBranch] = useState(false);

  const [file, setFile] = useState(null);

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

  const idChangedHandler = (e) => {
    setBranch(false);
    setBranchId(e.target.value);
    setBranch(true);
  };

  const idChangedHandler1 = (e) => {
    setCourseId(e.target.value);
    setBranch1(false);
    setBranches(null);

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
      <div className="row" style={{ padding: "10px" }}>
        
        <div className="col-md-12">
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
     
      </div>
    );
  }

  let branchView = null;
  if (branch1) {
    branchView = (
      <div className="row" style={{ padding: "10px" }}>
     
        <div className="col-md-12">
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
      
      </div>
    );
  }

  const [register, setRegister] = useState({
    session: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter Session for ex: 1990 - 1991",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
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
    semester: {
      elementType: "select",
      elementConfig: {
        options: [
          { value: "none", displayValue: "SELECT SEMESTER" },
          { value: 1, displayValue: "One" },
          { value: 2, displayValue: "Two" },
          { value: 3, displayValue: "Three" },
          { value: 4, displayValue: "Four" },
          { value: 5, displayValue: "Five" },
          { value: 6, displayValue: "Six" },
          { value: 7, displayValue: "Seven" },
          { value: 8, displayValue: "Eight" },
        ],
      },
      validation: {},
      value: "none",
      isValid: true,
    },
  });

  const inputChangedHandler = (event, registerData) => {
    const updatedSchedules = updateObject(register, {
      [registerData]: updateObject(register[registerData], {
        value: event.target.value,
        valid: checkValidity(
          event.target.value,
          register[registerData].validation
        ),
        touched: true,
      }),
    });
    setRegister(updatedSchedules);
  };

  const formElementsArray = [];
  for (let key in register) {
    formElementsArray.push({
      id: key,
      config: register[key],
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

    let query = "?branchId=" + branchId;

    for (let key in register) {
      query = query + "&" + key + "=" + register[key].value;
    }
    const formData = new FormData();
    formData.append("file", file);

    axios({
      method: "POST",
      url: "paper/uploadFile/" + query,
      data: formData,
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
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

  const onChangeHandler = (event) => {
    setFile(event.target.files[0]);
    setDisabled(false)
  };

  let fullForm = (
    <div>
      {courseView}
      {branchView}
      {branch1 ? form : null}
      {branch1 ? (
        <div>
          <label for="myfile"  style = {{padding: "10px"}}>Select a file:</label>
          <input
            type="file"
            id="myfile"
            name="myfile"
            onChange={onChangeHandler}
            style = {{padding: "10px"}}
          />
        </div>
      ) : null}
    </div>
  );

  if (loading) {
    fullForm = <Spinner />;
  }

  return (
    <div className={classes.BranchData}>
      <h4> Add Paper</h4>
      <form>
        {fullForm}
        <Button btnType = "Success" clicked={submitHandler} disabled = {disabled}>Submit</Button>
      </form>
    </div>
  );
};

export default withRouter(AddPaper);
