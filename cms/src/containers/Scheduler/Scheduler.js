import React, { useState } from "react";
import Schedule from "./Schedule/Schedule";
import TimeTable from "./TimeTable/TimeTable";
import Button from "../../components/UI/Button/Button";
import axios from "../../axios-college";

import TimeTableList from "../../components/timeTable/TimeTableList";
import Spinner from "../../components/UI/Spinner/Spinner";

const Scheduler = React.memo(() => {
  const [periodsData, setPeriodsData] = useState([]);
  const [scheduleData, setScheduleData] = useState();
  const [loading, setLoading] = useState(false);
  const [count, setCount] = useState(0);

  const addPeriodHandler = (period) => {
    setPeriodsData((prevPeriods) => [
      ...prevPeriods,
      { id: count + 1, ...period },
    ]);
    setCount(count + 1);
  };

  const addSchedule = (schedule) => {
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
      for (let key in periodsData[period]) {
        period1[key] = periodData[key].value;
      }
      periods.push(period1);
    }
    const schedule1 = { ...schedule, periods };
    axios
      .post("schedule/addSchedule", schedule1, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        setLoading(false);
        alert(response.data);
      })
      .catch((error) => {
        setLoading(false);
        alert(error.response.data.message)
      });

    setScheduleData(null);
    setPeriodsData([]);
    setCount(0);
  };

  if (loading) {
    console.log("In loading");
    return <Spinner />;
  }

  const timeTableList = (l) => {
    if (l > 0) {
      return (
        <TimeTableList periods={periodsData} onRemoveItem={removePeriod} />
      );
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
