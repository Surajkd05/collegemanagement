import React, { useEffect, useState } from "react";
import { withRouter } from "react-router-dom";
import axios from "../../../../axios-college";
import Spinner from "../../../../components/UI/Spinner/Spinner";
import Button from "../../../../components/UI/Button/Button";
import classes from "./ViewTip.module.css";

const ViewTip = (props) => {
  const [tips, setTips] = useState();
  const [loading, setLoading] = useState(true);
  const [count, setCount] = useState(0);

  useEffect(() => {
    axios
      .get("preparation/interview?branchId=" + props.branchId)
      .then((response) => {
        setTips(response.data);
        setLoading(false);
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
  }, []);

  const pageChangeHandler = (page) => {
    setCount(page);
    axios
      .get("preparation/interview?branchId=" + props.branchId + "&page=" + page)
      .then((response) => {
        setTips(response.data);
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
          {tips.map((tip, count) => (
            <div key={tip.id}>
              <div className="row">
                <div className={classes.Box}>
                  <div className={classes.displayLineBreak}>
                    T{count}
                    {".  "}
                    {tip.tip}
                  </div>
                </div>
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
      )}
    </div>
  );
};

export default withRouter(ViewTip);
