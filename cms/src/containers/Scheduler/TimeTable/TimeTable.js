import React, { useState } from "react";
import { updateObject, checkValidity } from "../../../shared/utility";
import Input from "../../../components/UI/Input/Input";
import classes from "./TimeTable.module.css";
import Button from "../../../components/UI/Button//Button";

const TimeTable = React.memo((props) => {
  const [periodTimes, setPeriodTimes] = useState({
    facName: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter Faculty Name",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    subCode: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter Subject Code",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    subName: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter Subject Name",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    hour: {
      elementType: "input",
      elementConfig: {
        type: "number",
        placeholder: "Enter period hour",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    startTime: {
      elementType: "select",
      elementConfig: {
        options: [
          { value: "none", displayValue: "SELECT StartTime" },
          { value: "nine", displayValue: "Nine" },
          { value: "ten", displayValue: "Ten" },
          { value: "eleven", displayValue: "Eleven" },
          { value: "twelve", displayValue: "Twelve" },
          { value: "one", displayValue: "One" },
          { value: "two", displayValue: "Two" },
          { value: "three", displayValue: "three" },
          { value: "four", displayValue: "Four" },
        ],
      },
      validation: {},
      value: "none",
      isValid: true,
    },
    endTime: {
      elementType: "select",
      elementConfig: {
        options: [
          { value: "none", displayValue: "SELECT EndTime" },
          { value: "ten", displayValue: "Ten" },
          { value: "eleven", displayValue: "Eleven" },
          { value: "twelve", displayValue: "Twelve" },
          { value: "two", displayValue: "Two" },
          { value: "three", displayValue: "three" },
          { value: "four", displayValue: "Four" },
        ],
      },
      validation: {},
      value: "none",
      isValid: true,
    },
  });

  const submitHandler = (event) => {
    console.log("In time table");
    event.preventDefault();
    props.onAddIngredient({
      facName: periodTimes.facName,
      subCode: periodTimes.subCode,
      subName: periodTimes.subName,
      hour: periodTimes.hour,
      startTime: periodTimes.startTime,
      endTime: periodTimes.endTime,
    });
  };

  const inputChangedHandler = (event, scheduleName) => {
    const updatedSchedules = updateObject(periodTimes, {
      [scheduleName]: updateObject(periodTimes[scheduleName], {
        value: event.target.value,
        valid: checkValidity(
          event.target.value,
          periodTimes[scheduleName].validation
        ),
        touched: true,
      }),
    });
    setPeriodTimes(updatedSchedules);
  };

  const formElementsArray = [];
  for (let key in periodTimes) {
    formElementsArray.push({
      id: key,
      config: periodTimes[key],
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

  return (
    <div className={classes.TimeTableData}>
      <form onSubmit={submitHandler}>
        <h4>Enter Period Details</h4>
        {form}
        <Button btnType="Success">Add Period</Button>
      </form>
    </div>
  );
});

export default TimeTable;
