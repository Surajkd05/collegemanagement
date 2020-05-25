import React, { useState } from "react";
import { updateObject, checkValidity } from "../../../shared/utility";
import Input from "../../../components/UI/Input/Input";
import Spinner from "../../../components/UI/Spinner/Spinner";
import classes from "./Schedule.module.css";
import Button from "../../../components/UI/Button/Button"

const Schedule = React.memo((props) => {
  const [schedule, setSchedule] = useState({
    roomNo: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter Room Number",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    day: {
      elementType: "select",
      elementConfig: {
        options: [
          { value: "none", displayValue: "SELECT DAY" },
          { value: "Mon", displayValue: "Monday" },
          { value: "Tues", displayValue: "Tuesday" },
          { value: "Wednes", displayValue: "Wednesday" },
          { value: "Thurs", displayValue: "Thursday" },
          { value: "Fri", displayValue: "Friday" },
          { value: "Satur", displayValue: "Saturday" },
        ],
      },
      validation: {},
      value: "none",
      isValid: true,
    },
    year: {
      elementType: "select",
      elementConfig: {
        options: [
          { value: "none", displayValue: "SELECT YEAR" },
          { value: "one", displayValue: "One" },
          { value: "two", displayValue: "Two" },
          { value: "three", displayValue: "Three" },
          { value: "four", displayValue: "Four" },
        ],
      },
      validation: {},
      value: "none",
      isValid: true,
    },
    branch: {
      elementType: "select",
      elementConfig: {
        options: [
          { value: "none", displayValue: "SELECT BRANCH" },
          { value: "cse", displayValue: "Computer Science & Engineering" },
          { value: "ce", displayValue: "Civil Engineering" },
          { value: "me", displayValue: "Mechanical Engineering" },
          {
            value: "ece",
            displayValue: "Electronics & Communication Engineering",
          },
          { value: "bt", displayValue: "Bio-Tech Engineering" },
        ],
      },
      validation: {},
      value: "none",
      isValid: true,
    },
    section: {
      elementType: "select",
      elementConfig: {
        options: [
          { value: "none", displayValue: "SELECT SECTION" },
          { value: "one", displayValue: "One" },
          { value: "two", displayValue: "Two" },
          { value: "three", displayValue: "Three" },
          { value: "four", displayValue: "Four" },
        ],
      },
      validation: {},
      value: "none",
      isValid: true,
    },
  });

  const submitHandler = (event) => {
    event.preventDefault();
    props.onAddSchedule({
      roomNo: schedule.roomNo,
      day: schedule.day,
      year: schedule.year,
      branch: schedule.branch,
      section: schedule.section,
    });
  };

  const inputChangedHandler = (event, scheduleName) => {
    const updatedSchedules = updateObject(schedule, {
      [scheduleName]: updateObject(schedule[scheduleName], {
        value: event.target.value,
        valid: checkValidity(
          event.target.value,
          schedule[scheduleName].validation
        ),
        touched: true,
      }),
    });
    setSchedule(updatedSchedules);
  };

  const formElementsArray = [];
  for (let key in schedule) {
    formElementsArray.push({
      id: key,
      config: schedule[key],
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

  if (props.loading) {
    form = <Spinner />;
  }

  return (
    <div className={classes.ScheduleData}>
      <form onSubmit={submitHandler}>
        <h4>Enter Schedule Data</h4>
        {form}
        <Button btnType="Success">Add Schedule</Button>
      </form>
    </div>
  );
});

// const mapStateToProps = (state) => {
//   return {
//     scheduleRedirectPath: state.auth.authRedirectPath,
//   };
// };

// const mapDispatchToProps = (dispatch) => {
//   return {
//     onScheduleRedirect: (path) => dispatch(actions.scheduleRedirect(path))
//   };
// };

export default Schedule;
