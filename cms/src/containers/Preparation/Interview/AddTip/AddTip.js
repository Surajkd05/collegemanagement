import React, { useState } from "react";
import { withRouter } from "react-router-dom";
import Button from "../../../../components/UI/Button/Button";
import Spinner from "../../../../components/UI/Spinner/Spinner";
import axios from "../../../../axios-college";
import classes from "./AddTip.module.css";

const AddTip = (props) => {

  const [tip, setTip] = useState("Enter any tip and enter your name at the top");

  const [loading, setLoading] = useState(false);

  const inputChangeHandler = (event) => {
    setTip(event.target.value);
  };

  const submitHandler = (event) => {
    event.preventDefault();
    setLoading(true);
    const formData = { tip: tip };

    axios
      .post(
        "preparation/interview?branchId=" +
          props.branchId,
        formData
      )
      .then((response) => {
        alert(response.data);
        setLoading(false);
        setTip("Enter any tip and enter your name at the top");
      })
      .catch((error) => {
        alert(error.response.data.message);
        setLoading(false);
      });
  };

  return (
    <div>
      {!loading ? (
        <div className={classes.Box}>
          <h4>AddTip</h4>
          <div className="form-group green-border-focus">
            {" "}
            <textarea
              value={tip}
              onChange={inputChangeHandler}
              rows="5"
              cols="90"
              className="form-control"
            />
          </div>
          <Button btnType="Success" clicked={submitHandler}>
            AddTip
          </Button>
        </div>
      ) : (
        <Spinner />
      )}
    </div>
  );
};

export default withRouter(AddTip);
