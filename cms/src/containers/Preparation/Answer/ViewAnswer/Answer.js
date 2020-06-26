import React, { useState } from "react";
import { withRouter, Redirect } from "react-router-dom";
import classes from "./ViewAnswer.module.css";
import Button from "../../../../components/UI/Button/Button";
import axios from "../../../../axios-college";

const Answer = (props) => {
  const [editAnswer, setEditAnswer] = useState(false);

  const [updatedAnswer, setUpdatedAnswer] = useState();

  const editAnswerHandler = () => {
    setEditAnswer(!editAnswer);
    setUpdatedAnswer(props.answer.answer)
  };

  const inputChangeHandler = (event) => {
    setUpdatedAnswer(event.target.value);
  };

  const onSubmitHandler = (answerId) => {
    const formData = { answer: updatedAnswer };

    axios
      .put(
        "preparation/answer?answerId=" + answerId,
        formData
      )
      .then((response) => {
        alert(response.data);
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
  };

  return (
    <div>
      <div className={classes.Box}>
        <h4 style={{ color: "Blue", textAlign: "center" }}>
          Answer {props.count}
        </h4>
        <br />
        {!editAnswer ? (
          <div className={classes.displayLineBreak}>{props.answer.answer}</div>
        ) : (
          <div className={classes.displayLineBreak}>
            <div className="form-group green-border-focus">
              <textarea
                value={updatedAnswer}
                rows="25"
                cols="73"
                className="form-control"
                onChange={inputChangeHandler}
              />
            </div>
            <div style={{ textAlign: "center" }}>
              <Button
                btnType="Success"
                clicked={() => onSubmitHandler(props.answer.answerId)}
              >
                Submit
              </Button>
            </div>
          </div>
        )}
        <div className="row">
          <div className="col-md-9" />
          <div className="col-md-3">
            {
              <Button btnType="Success" clicked={() => editAnswerHandler()}>
                Edit Answer
              </Button>
            }
          </div>
        </div>
      </div>
    </div>
  );
};

export default withRouter(Answer);
