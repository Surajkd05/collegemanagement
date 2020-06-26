import React, { useState } from "react";
import classes from "./AddressUpdate.module.css";
import { updateObject, checkValidity } from "../../../shared/utility";
import Input from "../../../components/UI/Input/Input";
import Spinner from "../../../components/UI/Spinner/Spinner";
import Button from "../../../components/UI/Button/Button";
import { connect } from "react-redux";
import * as actions from "../../../store/actions/index";
import { withRouter, Redirect } from "react-router-dom";

const AddAddress = (props) => {
  const [address, setAddress] = useState({
    block: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter your Block number",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    plotNumber: {
      elementType: "input",
      elementConfig: {
        type: "number",
        placeholder: "Enter your Plot number",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    sectorNumber: {
      elementType: "input",
      elementConfig: {
        type: "number",
        placeholder: "Enter your Sector number",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    streetName: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter your Street Name",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    label: {
      elementType: "select",
      elementConfig: {
        options: [
          { value: "none", displayValue: "SELECT LABEL" },
          { value: "work", displayValue: "WORK" },
          { value: "home", displayValue: "HOME" },
        ],
      },
      validation: {},
      value: "none",
      isValid: true,
    },
    city: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter your City",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    district: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter your District",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    state: {
      elementType: "input",
      elementConfig: {
        type: "state",
        placeholder: "Enter your State",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
    zipCode: {
      elementType: "input",
      elementConfig: {
        type: "number",
        placeholder: "Enter your Zip Code",
      },
      value: "",
      validation: {
        required: true,
        minLength: 6,
        maxLength: 6,
      },
      isValid: false,
      touched: false,
    },
    country: {
      elementType: "input",
      elementConfig: {
        type: "text",
        placeholder: "Enter your Country Name",
      },
      value: "",
      validation: {
        required: true,
      },
      isValid: false,
      touched: false,
    },
  });

  const inputChangedHandler = (event, registerData) => {
    const updatedAddress = updateObject(address, {
      [registerData]: updateObject(address[registerData], {
        value: event.target.value,
        valid: checkValidity(
          event.target.value,
          address[registerData].validation
        ),
        touched: true,
      }),
    });
    setAddress(updatedAddress);
  };

  const formElementsArray = [];
  for (let key in address) {
    formElementsArray.push({
      id: key,
      config: address[key],
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

    const addressData = {};
    for (let key in address) {
      addressData[key] = address[key].value;
    }

    console.log("Submitted seller info is : ", addressData);
    props.onAddAddress(addressData)
  };

  if (props.loading) {
    form = <Spinner />;
  }

  if(props.addAddress) {
    return <Redirect to="/profile" />
  }

  return (
    <div className={classes.AddressData}>
      <h4>Enter Address Details</h4>
      <form onSubmit={submitHandler}>
        {form}
        {<Button btnType="Success">Submit</Button>}
      </form>
    </div>
  );
};

const mapStateToProps = (state) => {
  return {
    loading: state.user.loading,
    addAddress: state.user.addAddress
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    onAddAddress: (addressData) =>
      dispatch(actions.onAddUserAddress(addressData)),
  };
};

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(AddAddress));
