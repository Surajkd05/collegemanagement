import React, { useState } from "react";
import { withRouter } from "react-router-dom";
import Button from "../../../../components/UI/Button/Button";
import Spinner from "../../../../components/UI/Spinner/Spinner";
import axios from "../../../../axios-college";
import classes from "./AddAnswer.module.css";

const AddAnswer = (props) => {

  const [answer, setAnswer] = useState("Enter your Answer. If programming question please write the language at the top.");

  const [loading, setLoading] = useState(false);

  const inputChangeHandler = (event) => {
    setAnswer(event.target.value);
  };

  const submitHandler = (event) => {
    event.preventDefault();
    setLoading(true);

    const formData = { answer: answer };

    axios
      .post(
        "preparation/answer?questionId=" +
          props.questionId,
        formData
      )
      .then((response) => {
        alert(response.data);
        setLoading(false);
        setAnswer("Enter your Answer. If programming question please write the language at the top.");
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
          <div className="form-group green-border-focus">
            {" "}
            <textarea
              value={answer}
              onChange={inputChangeHandler}
              rows="5"
              cols="90"
              className="form-control"
            />
          </div>
          <Button btnType="Success" clicked={submitHandler}>
            Submit
          </Button>
        </div>
      ) : (
        <div ><Spinner /></div>
      )}
    </div>
  );
};

export default withRouter(AddAnswer);
