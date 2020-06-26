import React from "react";
import classes from "./TimeTableListView.module.css";
import { Table, Thead, Tbody, Tr, Th, Td } from 'react-super-responsive-table'
import 'react-super-responsive-table/dist/SuperResponsiveTableStyle.css'

const TimeTableViewList = (props) => {
  console.log("Props in list is", props);
  console.log("In Period list");

  return (
    <section className={classes.TimeTableListData}>
      <h4>Time Table</h4>
      <Table>
        <Thead>
          <Tr>
            <Th>Day</Th>
            <Th>
              Period1
              <p>9:00 am - 10:00 am</p>
            </Th>
            <Th>
              Period2
              <p>10:00 am - 11:00 am</p>
            </Th>
            <Th>
              Period3
              <p>11:00 am - 12:00 pm</p>
            </Th>
            <Th>
              Period4
              <p>1:00 pm - 2:00 pm</p>
            </Th>
            <Th>
              Period5
              <p>2:00 pm - 3:00 pm</p>
            </Th>
            <Th>
              Period6
              <p>3:00 pm - 4:00 pm</p>
            </Th>
            <Th>
              Period7
              <p>4:00 am - 5:00 pm</p>
            </Th>
          </Tr>
        </Thead>
        <Tbody>
          {props.periods.map((period) => (
            <Tr key={period.scheduleId}>
              {console.log("Period Id : ", period)}
              <Td>{period.day + "day"}</Td>

              {period.periods.map((per) => (
                <Td key={per.periodId}>
                  <span key={per.subName} style={{ color: "brown" }}>
                    Subject: <p style={{ color: "red" }}>{per.subName}</p>
                  </span>
                  <span key={per.subCode} style={{ color: "brown" }}>
                    Code: <p style={{ color: "red" }}>{per.subCode}</p>
                  </span>
                  <span key={per.facName} style={{ color: "brown" }}>
                    Faculty: <p style={{ color: "red" }}>{per.facName}</p>
                  </span>
                </Td>
              ))}
            </Tr>
          ))}
        </Tbody>
      </Table>
    </section>
  );
};

export default TimeTableViewList;
