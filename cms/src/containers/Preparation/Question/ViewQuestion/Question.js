import React, { useState } from "react";
import { withRouter } from "react-router-dom";
import classes from "./ViewQuestion.module.css";
import Button from "../../../../components/UI/Button/Button";
import AddAnswer from "../../Answer/AddAnswer/AddAnswer";

const Question = (props) => {
  const [addAnswer, setAddAnswer] = useState(false);

  const addAnswerHandler = () => {
    setAddAnswer(!addAnswer);
  };

  const viewAnswerHandler = (questionId) => {
    props.history.push({
      pathname: "/answer",
      state: {
        questionId: questionId,
      },
    });
  };
  return (
    <div>
      <div className={classes.Box}>
        <div className={classes.displayLineBreak}>
          Q{props.count}
          {".  "}
          {props.question.question}
        </div>
        <div className="row">
          <div className="col-md-3">
            <Button btnType="Success" clicked={() => addAnswerHandler()}>
              Add Answer
            </Button>
          </div>
          <div className="col-md-6" />
          <div className="col-md-3">
            <Button
              btnType="Success"
              clicked={() => viewAnswerHandler(props.question.questionId)}
            >
              View Answer
            </Button>
          </div>
        </div>
        {addAnswer ? (
          <div className="row">
            <AddAnswer
              questionId={props.question.questionId}
              style={{ textAlign: "center" }}
            />
          </div>
        ) : null}
      </div>
    </div>
  );
};

export default withRouter(Question);
