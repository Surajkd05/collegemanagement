import React from "react";
import classes from "./TimeTableList.module.css";
import { Table, Thead, Tbody, Tr, Th, Td } from 'react-super-responsive-table'
import 'react-super-responsive-table/dist/SuperResponsiveTableStyle.css'

const TimeTableList = (props) => {
  console.log("Props in list is", props);
  console.log("In Period list");
  return (
    <section className={classes.ScheduleData}>
      <h2>Added Periods</h2>
      <Table>
        <Thead>
          <Tr>
            <Th>Key</Th>
            <Th>Faculty Name</Th>
            <Th>Subject Code</Th>
            <Th>Subject Name</Th>
            <Th>Hour</Th>
            <Th>Start Time</Th>
            <Th>End Time</Th>
          </Tr>
        </Thead>
        <Tbody>
          {props.periods.map((period) => (
            <Tr key={period.id}>
              <Td
                key={period.id}
                onClick={props.onRemoveItem.bind(this, period.id)}
              >
                {period.id}
              </Td>
              {console.log("Period Id : ", period.id)}
              {console.log("Period Faculty : ", period.facName.value)}

              <Td>{period.facName.value}</Td>
              <Td>{period.subCode.value}</Td>
              <Td>{period.subName.value}</Td>
              <Td>{period.hour.value}</Td>
              <Td>{period.startTime.value}</Td>
              <Td>{period.endTime.value}</Td>
            </Tr>
          ))}
        </Tbody>
      </Table>
    </section>
  );
};

export default TimeTableList;
