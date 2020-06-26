import React, { useState } from "react";
import { updateObject, checkValidity } from "../../../shared/utility";
import Input from "../../../components/UI/Input/Input";
import Button from "../../../components/UI/Button/Button";
import classes from "./TimeTableView.module.css";
import Spinner from "../../../components/UI/Spinner/Spinner";
import TimeTableViewList from "../../../components/timeTable/TimeTableViewList";
import Aux from "../../../hoc/Aux/aux";
import axios from "../../../axios-college";

const TimeTableView = React.memo((props) => {
  const [schedule, setSchedule] = useState({
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

  const [resultData, setResultData] = useState([]);

  const submitHandler = (event) => {
    event.preventDefault();
    let result = null;
    let query = "?";
    for (let key in schedule) {
      if (key !== "year") {
        query = query + "&" + key + "=" + schedule[key].value;
      } else {
        query = query + key + "=" + schedule[key].value;
      }
    }

    axios
      .get("students/getSchedule" + query, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        console.log(response);
        result = response.data;
        console.log("Result is : ", result);
        setResultData(result);
      })
      .catch((error) => {
        console.log("error is", error);
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

  const timeTableList = (l) => {
    if (l > 0) {
      return <TimeTableViewList periods={resultData} />;
    }
  };

  if (props.loading) {
    form = <Spinner />;
  }

  return (
    <Aux>
      <div className={classes.ViewData}>
        <form onSubmit={submitHandler}>
          <h4>Enter Details to view TimeTable</h4>
          {form}
          <Button btnType="Success">Submit</Button>
        </form>
      </div>
      <div>{<section>{timeTableList(resultData.length)}</section>}</div>
    </Aux>
  );
});

export default TimeTableView;
