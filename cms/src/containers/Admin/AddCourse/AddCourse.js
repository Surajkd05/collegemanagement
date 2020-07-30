import React, { useState } from "react";
import { withRouter } from "react-router-dom";
import { updateObject, checkValidity } from "../../../shared/utility";
import Input from "../../../components/UI/Input/Input";
import Button from "../../../components/UI/Button/Button";
import Spinner from "../../../components/UI/Spinner/Spinner";
import classes from "./AddCourse.module.css";
import axios from "../../../axios-college";

const AddCourse = (props) => {
  const [course, setCourse] = useState({
    courseName: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter new course name",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
  });

  const [loading, setLoading] = useState(false);

  const inputChangedHandler = (event, branchData) => {
    const updatedSchedules = updateObject(course, {
      [branchData]: updateObject(course[branchData], {
        value: event.target.value,
        valid: checkValidity(event.target.value, course[branchData].validation),
        touched: true,
      }),
    });
    setCourse(updatedSchedules);
  };

  const formElementsArray = [];
  for (let key in course) {
    formElementsArray.push({
      id: key,
      config: course[key],
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

    const formData = {};

    for (let key in course) {
      formData[key] = course[key].value;
    }

    axios
      .post("admin/course", formData, {
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

  if (loading) {
    form = <Spinner />;
  }

  return (
    <div className={classes.CourseData}>
      <h4>Course</h4>
      <form>{form}</form>
      <Button btnType="Success" clicked={submitHandler}>
        AddCourse
      </Button>
    </div>
  );
};

export default withRouter(AddCourse);
