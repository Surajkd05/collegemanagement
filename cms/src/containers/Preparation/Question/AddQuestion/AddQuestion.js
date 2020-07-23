import React, { useState } from "react";
import { withRouter } from "react-router-dom";
import Button from "../../../../components/UI/Button/Button";
import Spinner from "../../../../components/UI/Spinner/Spinner";
import axios from "../../../../axios-college";
import classes from "./AddQuestion.module.css";

const AddQuestion = (props) => {

  const [questions, setQuestion] = useState("Enter your question");

  const [loading, setLoading] = useState(false);

  const inputChangeHandler = (event) => {
    setQuestion(event.target.value);
  };

  const submitHandler = (event) => {
    event.preventDefault();
    setLoading(true);

    console.log("Question is : ", questions);
    const formData = { question: questions };

    axios
      .post(
        "preparation/question?branchId=" +
          props.branchId,
        formData
      )
      .then((response) => {
        alert(response.data);
        setLoading(false);
        setQuestion("Enter your question");
      })
      .catch((error) => {
        alert(error.response.data.message);
        setLoading(false);
      });
  };

  return (
    <div>
      {!loading ? (
        <div>
        <div className={classes.Box}>
          <h4>AddQuestion</h4>
          <div className="form-group green-border-focus">
            {" "}
            <textarea
              value={questions}
              onChange={inputChangeHandler}
              rows="5"
              cols="90"
              className="form-control"
            />
          </div>
          <Button btnType="Success" clicked={submitHandler}>
            AddQuestion
          </Button>
        </div>
        </div>
      ) : (
        <Spinner />
      )}
    </div>
  );
};

export default withRouter(AddQuestion);
