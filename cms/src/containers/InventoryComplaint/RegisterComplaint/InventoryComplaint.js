import React, { useState } from "react";
import { updateObject, checkValidity } from "../../../shared/utility";
import Input from "../../../components/UI/Input/Input";
import Spinner from "../../../components/UI/Spinner/Spinner";
import Button from "../../../components/UI/Button/Button";
import classes from "./InventoryComplaint.module.css";
import axios from "../../../axios-college";
import { withRouter } from "react-router-dom";

const InventoryComplaint = (props) => {
  const [complaint, setComplaint] = useState({
    block: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter Block Number",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    room: {
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
    inventoryName: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter Defected Inventory Name",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    quantity: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter Quantity",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    complaintBy: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter Your Name",
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

  const inputChangedHandler = (event, inventoryName) => {
    const updatedComplaint = updateObject(complaint, {
      [inventoryName]: updateObject(complaint[inventoryName], {
        value: event.target.value,
        valid: checkValidity(
          event.target.value,
          complaint[inventoryName].validation
        ),
        touched: true,
      }),
    });
    setComplaint(updatedComplaint);
  };

  const onSubmitHandler = (event) => {
    setLoading(true);
    event.preventDefault();
    const complaint1 = {};

    for (let key in complaint) {
      complaint1[key] = complaint[key].value;
    }

    console.log("Added complaint is: ", complaint1);

    axios
      .post(
        "inventory/register-complaint",
        complaint1
      )
      .then((response) => {
        setLoading(false);
        console.log("Complaint response", response);
        return response.data;
      })
      .catch((error) => {
        setLoading(false);
        console.log("Error is", error);
      });
  };

  const formElementsArray = [];
  for (let key in complaint) {
    formElementsArray.push({
      id: key,
      config: complaint[key],
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

  if (loading) {
    form = <Spinner />;
  }

  return (
    <div className={classes.ComplaintData}>
      <h4>Enter Complaint Data</h4>
      <form>
        {form}
        <Button clicked={onSubmitHandler} btnType="Success">
          SUBMIT
        </Button>
      </form>
    </div>
  );
};

export default withRouter(InventoryComplaint);
