import React from "react";
//import Table from "react-bootstrap/Table";
import classes from "./TimeTableList.module.css";


const TimeTableList = (props) => {
  console.log("Props in list is", props);
  console.log("In Period list");
  return (
    <section className={classes.TimeTableListData}>
      <h2>Added Periods</h2>
      <table>
        <thead>
          <tr>
            <th>Key</th>
            <th>Faculty Name</th>
            <th>Subject Code</th>
            <th>Subject Name</th>
            <th>Hour</th>
            <th>Start Time</th>
            <th>End Time</th>
          </tr>
        </thead>
        <tbody>
          {props.periods.map((period) => (
            <tr key={period.id}>
              <td
                key={period.id}
                onClick={props.onRemoveItem.bind(this, period.id)}
              >
                {period.id}
              </td>
              {console.log("Period Id : ", period.id)}
              {console.log("Period Faculty : ", period.facName.value)}

              <td>{period.facName.value}</td>
              <td>{period.subCode.value}</td>
              <td>{period.subName.value}</td>
              <td>{period.hour.value}</td>
              <td>{period.startTime.value}</td>
              <td>{period.endTime.value}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </section>
  );
};

export default TimeTableList;
