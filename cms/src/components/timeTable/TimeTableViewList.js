import React from "react";
//import Table from "react-bootstrap/Table";
import classes from "./TimeTableListView.module.css";

const TimeTableViewList = (props) => {
  console.log("Props in list is", props);
  console.log("In Period list");

  return (
    <section className={classes.ScheduleData}>
      <h4>Time Table</h4>
      <table>
        <thead>
          <tr>
            <th>Day</th>
            <th>
              Period1
              <p>9:00 am - 10:00 am</p>
            </th>
            <th>
              Period2
              <p>10:00 am - 11:00 am</p>
            </th>
            <th>
              Period3
              <p>11:00 am - 12:00 pm</p>
            </th>
            <th>
              Period4
              <p>1:00 pm - 2:00 pm</p>
            </th>
            <th>
              Period5
              <p>2:00 pm - 3:00 pm</p>
            </th>
            <th>
              Period6
              <p>3:00 pm - 4:00 pm</p>
            </th>
            <th>
              Period7
              <p>4:00 am - 5:00 pm</p>
            </th>
          </tr>
        </thead>
        <tbody>
          {props.periods.map((period) => (
            <tr key={period.scheduleId}>
              {console.log("Period Id : ", period)}
              <td>{period.day + "day"}</td>

              {period.periods.map((per) => (
                <td key={per.periodId}>
                  <span key={per.subName} style={{ color: "brown" }}>
                    Subject: <p style={{ color: "red" }}>{per.subName}</p>
                  </span>
                  <span key={per.subCode} style={{ color: "brown" }}>
                    Code: <p style={{ color: "red" }}>{per.subCode}</p>
                  </span>
                  <span key={per.facName} style={{ color: "brown" }}>
                    Faculty: <p style={{ color: "red" }}>{per.facName}</p>
                  </span>
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </section>
  );
};

export default TimeTableViewList;
