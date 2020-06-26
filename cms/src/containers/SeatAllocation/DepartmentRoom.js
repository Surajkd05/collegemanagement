import React, { useState } from "react";
import classes from "./Room.module.css";
import { updateObject, checkValidity } from "../../shared/utility";
import Input from "../../components/UI/Input/Input";
import Spinner from "../../components/UI/Spinner/Spinner";
import axios from "../../axios-college"

const DepartmentRoom = React.memo((props) => {
  const [departmentRoom, setDepartmentRoom] = useState({
    collegeId: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter College Id",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    deptId: {
      elementType: "input",
      elementConfig: {
        type: "number",
        placeholder: "Enter Department Id",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    block: {
        elementType: "input",
        elementConfig: {
          type: "text",
          placeholder: "Enter Block",
        },
        value: "",
        validation: {
          required: true,
        },
        isValid: false,
        touched: false,
      },
      
    floorNo: {
      elementType: "select",
      elementConfig: {
        options: [
          { value: "none", displayValue: "SELECT Floor" },
          { value: "Ground", displayValue: "Ground" },
          { value: "First", displayValue: "First" },
          { value: "Second", displayValue: "Second" },
          { value: "Third", displayValue: "Third" },
        ],
      },
      validation: {},
      value: "none",
      isValid: true,
    },
      roomName: {
        elementType: "input",
        elementConfig: {
          type: "text",
          placeholder: "Enter Room Name",
        },
        value: "",
        validation: {
          required: true,
        },
        isValid: false,
        touched: false,
      },
      totalCapacity: {
        elementType: "input",
        elementConfig: {
          type: "number",
          placeholder: "Enter Total Capacity in room",
        },
        value: "",
        validation: {
          required: true,
        },
        isValid: false,
        touched: false,
      },
  });

const [loading, setLoading] = useState(false)

  const inputChangedHandler = (event, roomData) => {
    const updatedSchedules = updateObject(departmentRoom, {
      [roomData]: updateObject(departmentRoom[roomData], {
        value: event.target.value,
        valid: checkValidity(event.target.value, departmentRoom[roomData].validation),
        touched: true,
      }),
    });
    setDepartmentRoom(updatedSchedules);
  };

  const formElementsArray = [];
  for (let key in departmentRoom) {
    formElementsArray.push({
      id: key,
      config: departmentRoom[key],
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
    setLoading(true)

    const formData = {};

    for (let key in departmentRoom) {
      formData[key] = departmentRoom[key].value;
    }
    axios({
        method: "POST",
        url: "master/createDepartmentRoom",
         data: formData,
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
        .then((response) => {
          console.log("metadata : ", response.data);
          alert(response.data);
          setLoading(false);
        })
        .catch((error) => {
          console.log(" error is : ", error);
          alert(error);
          setLoading(false);
        });
  };

  if (loading) {
    form = <Spinner />;
  }

  return (
    <div className={classes.RoomData}>
      <h4>Create Department Room</h4>
      <form onSubmit={submitHandler}>
        {form}
        <button type="submit">Create</button>
      </form>
    </div>
  );
});

export default DepartmentRoom;