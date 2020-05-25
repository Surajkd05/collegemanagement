import React, { useState } from "react";
import Schedule from "./Schedule/Schedule";
import TimeTable from "./TimeTable/TimeTable";
import Button from "../../components/UI/Button/Button";
import axios from "axios";

import TimeTableList from "../../components/timeTable/TimeTableList";
import Spinner from "../../components/UI/Spinner/Spinner";

const Scheduler = React.memo(() => {
  const [periodsData, setPeriodsData] = useState([]);
  const [scheduleData, setScheduleData] = useState();
  const [loading, setLoading] = useState(false);
  const [count, setCount] = useState(0);

  console.log("Periods added", periodsData);

  console.log("Schedule added", scheduleData);

  const addPeriodHandler = (period) => {
    console.log("Added Period is", period);
    setPeriodsData((prevPeriods) => [
      ...prevPeriods,
      { id: count+1, ...period },
    ]);
    setCount(count+1)
  };

  const addSchedule = (schedule) => {
    console.log("Added schedule is", schedule);
    setScheduleData({ id: schedule.day.value, ...schedule });
  };

  const removePeriod = (periodId) => {
    periodsData.filter((period) => period.id !== periodId);
  };

  const onSubmitHandler = () => {
    setLoading(true);
    const schedule = {};

    for (let key in scheduleData) {
      schedule[key] = scheduleData[key].value;
    }

    const periods = [];
    for (let period in periodsData) {
      const period1 = {};
      const periodData = periodsData[period];
      console.log("Period is", periodsData[period]);
      for (let key in periodsData[period]) {
        period1[key] = periodData[key].value;
      }
      periods.push(period1);
    }
    const schedule1 = { ...schedule, periods };
    console.log("Submitted Periods is : ", schedule1);
    axios
      .post("http://localhost:8080/college/schedule/addSchedule", schedule1)
      .then((response) => {
        console.log("Response is", response);
        setLoading(false);
        alert(response.data);
      })
      .catch((error) => {
        setLoading(false);
        console.log("error is ", error);
      });

    setScheduleData(null);
    setPeriodsData([]);
    setCount(0)
  };

  if (loading) {
    console.log("In loading");
    return <Spinner />;
  }

  const timeTableList = (l) => {
    if (l > 0) {
      return <TimeTableList periods={periodsData} onRemoveItem={removePeriod} />;
    }
  };

  return (
    <div style={{ textAlign: "center" }}>
      <Schedule onAddSchedule={addSchedule} />

      <TimeTable onAddIngredient={addPeriodHandler} />
      {<section>{timeTableList(periodsData.length)}</section>}

      <Button clicked={onSubmitHandler} btnType="Success">
        Submit
      </Button>
    </div>
  );
});

export default Scheduler;
