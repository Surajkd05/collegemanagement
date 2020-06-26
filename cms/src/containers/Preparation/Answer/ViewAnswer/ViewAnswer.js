import React, { useEffect, useState } from "react";
import { withRouter } from "react-router-dom";
import axios from "../../../../axios-college";
import Spinner from "../../../../components/UI/Spinner/Spinner";
import Button from "../../../../components/UI/Button/Button";
import classes from "./ViewAnswer.module.css";
import Answer from "./Answer";

const ViewAnswer = (props) => {
  const [answers, setAnswers] = useState();
  const [loading, setLoading] = useState(true);
  const [count, setCount] = useState(0);

  useEffect(() => {
    axios
      .get(
        "preparation/answer?questionId=" +
          props.history.location.state.questionId
      )
      .then((response) => {
        setAnswers(response.data);
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
        "preparation/answer?questionId=" +
          props.history.location.state.questionId +
          "&page=" +
          page
      )
      .then((response) => {
        setAnswers(response.data);
        setLoading(false);
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
  };

  return (
    <div>
      {loading ? (
        <div>
          <Spinner />
        </div>
      ) : (
        <div className="row">
          <div className="col-md-2" />
          <div className="col-md-8">
            {answers.map((answer, count) => (
              <div key={answer}>
                <div className="row">
                  <Answer answer={answer} count={count + 1} />
                </div>
              </div>
            ))}
            <div className="row">
              <div className={classes.View}>
                <div className="col-md-2">
                  {count !== 0 ? (
                    <Button
                      btnType="Success"
                      clicked={() => pageChangeHandler(count - 1)}
                    >
                      Prev
                    </Button>
                  ) : null}
                </div>
                <div className="col-md-8" />
                <div className="col-md-2">
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
          <div className="col-md-2" />
        </div>
      )}
    </div>
  );
};

export default withRouter(ViewAnswer);
