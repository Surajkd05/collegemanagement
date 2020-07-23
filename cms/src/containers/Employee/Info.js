import React from "react";
import { withRouter } from "react-router-dom";
import classes from "./Subject.module.css";

const Info = (props) => {
  return (
    <div className={classes.Box}>
      <div className="row">
        <div className="col-md-2">
          Info{props.count}
          {".  "}
        </div>
        <div className="col-md-6" />
        <div className="col-md-4">{props.data.date}</div>
      </div>
      <div  className="row" />
      <div  className="row">
        <div className={classes.displayLineBreak} style={{paddingLeft:"20px"}}>{props.data.data}</div>
      </div>
    </div>
  );
};

export default withRouter(Info);
