import React, { useEffect, useState } from "react";
import { withRouter } from "react-router-dom";
import axios from "../../../../axios-college";
import Spinner from "../../../../components/UI/Spinner/Spinner";
import Button from "../../../../components/UI/Button/Button";
import Question from "./Question";
import classes from "./ViewQuestion.module.css";

const ViewQuestion = (props) => {
  const [questions, setQuestions] = useState();
  const [loading, setLoading] = useState(true);
  const [count, setCount] = useState(0);

  useEffect(() => {
    axios
      .get(
        "preparation/question?branchId=" +
          props.branchId
      )
      .then((response) => {
        setQuestions(response.data);
        setLoading(false);
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
  }, []);

  const pageChangeHandler = (page) => {
    setCount(page);
    axios
      .get(
        "preparation/question?branchId=" +
          props.branchId +
          "&page=" +
          page
      )
      .then((response) => {
        setQuestions(response.data);
        setLoading(false);
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
  };

  console.log("Count is : ", count);
  return (
    <div>
      {loading ? (
        <div>
          <Spinner />
        </div>
      ) : (
        <div>
          {questions.map((question, count) => (
            <div key={question.questionId} className="col-sm-12">
              <div className="row">
                <Question question={question} count={count + 1} />
              </div>
            </div>
          ))}
          <div className="row">
            <div className={classes.View}>
              <div className="col-sm-12 col-md-2">
                {count !== 0 ? (
                  <Button
                    btnType="Success"
                    clicked={() => pageChangeHandler(count - 1)}
                  >
                    Prev
                  </Button>
                ) : null}
              </div>
              <div className="col-sm-12 col-md-8" />
              <div className="col-sm-12 col-md-2">
                <Button
                  btnType="Success"
                  clicked={() => pageChangeHandler(count + 1)}
                >
                  Next
                </Button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default withRouter(ViewQuestion);
