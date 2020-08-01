import React, { useEffect, useState } from "react";
import axios from "../../../axios-college";
import { updateObject, checkValidity } from "../../../shared/utility";
import Input from "../../../components/UI/Input/Input";
import classes from "./ViewPaper.module.css";
import Spinner from "../../../components/UI/Spinner/Spinner";
import { withRouter } from "react-router-dom";
import Button from "../../../components/UI/Button/Button";
import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";
import "react-super-responsive-table/dist/SuperResponsiveTableStyle.css";
import axios1 from "axios";

const ViewPaper = (props) => {
  const [branches, setBranches] = useState();
  const [loading, setLoading] = useState(false);

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

    axios
      .get("paper/getFile" + query)
      .then((response) => {
        setFile(response.data);
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
      {branchView}
      {branch1 ? form : null}
    </div>
  );

  if (loading) {
    fullForm = <Spinner />;
  }

  return (
    <div>
      <div className={classes.BranchData}>
        <h4> View Paper</h4>

        <form>
          {fullForm}
          <Button btnType="Success" clicked={submitHandler}>
            Submit
          </Button>
        </form>
      </div>
      <div className="row">
        <div className="col-md-12">
          {file !== null ? (
            <section className={classes.UserView}>
              <h4>Fetched Papers</h4>
              <Table>
                <Thead>
                  <Tr>
                    <Th>S.No</Th>
                    <Th>Course Name</Th>
                    <Th>Branch Name</Th>
                    <Th>Year</Th>
                    <Th>Semester</Th>
                    <Th>Session</Th>
                    <Th>Download</Th>
                  </Tr>
                </Thead>
                <Tbody>
                  {file.map((file1, count) => (
                    <Tr key={file1.paperId}>
                      <Td key={file1.paperId}>{count + 1}</Td>

                      <Td>{file1.courseName}</Td>
                      <Td>{file1.branchName}</Td>
                      <Td>{file1.year}</Td>
                      <Td>{file1.semester}</Td>
                      <Td>{file1.session}</Td>
                      <Td>
                        <a href={file1.downloadUri} download>
                          <i class="fa fa-download" aria-hidden="true"></i>
                        </a>
                      </Td>
                    </Tr>
                  ))}
                </Tbody>
              </Table>
            </section>
          ) : null}
        </div>
      </div>
    </div>
  );
};

export default withRouter(ViewPaper);
