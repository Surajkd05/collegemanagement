import React, { useState } from "react";
import { withRouter } from "react-router-dom";
import { updateObject, checkValidity } from "../../../shared/utility";
import Input from "../../../components/UI/Input/Input";
import Button from "../../../components/UI/Button/Button";
import Spinner from "../../../components/UI/Spinner/Spinner";
import classes from "./AddBranch.module.css";
import axios from "../../../axios-college";

const AddBranch = (props) => {
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

  const submitHandler = (event) => {
    event.preventDefault();
    setLoading(true);

    const formData = {};

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

  if (loading) {
    form = <Spinner />;
  }

  return (
    <div className={classes.BranchData}>
      <h4>Branch</h4>
      <form>{form}</form>
      <Button btnType="Success" clicked={submitHandler}>
        AddBranch
      </Button>
    </div>
  );
};

export default withRouter(AddBranch);
