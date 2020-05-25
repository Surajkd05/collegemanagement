import React from "react";
import DashboardView from "./dashboard";
import SeatAllocationUserInfo from "./seatAlocation/seatAlocationUserInfo";
import SeatAllocation from "./seatAlocation/seatAlocation";
//import axios from 'axios';
//import Constants from '../constants/index';
//uncomment mockdata when working with API's
//import mockData from './seatAlocation/mockData';

class Dashboard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isRoomScreen: false,
      userInfo: {},
      hasAllowcatedSeat: false,
      actionHasalocated: true,
      seatId: "",
    };
  }

  showSeatScreen = (data) => {
    this.setState({ isRoomScreen: true, ...data });
  };

  render() {
    const {
      isRoomScreen,
      hasAllowcatedSeat,
      userInfo,
      actionHasalocated,
    } = this.state;
    return (
      <>
        <DashboardView />
        {isRoomScreen ? (
          <SeatAllocation
            {...{ hasAllowcatedSeat, userInfo, actionHasalocated }}
          />
        ) : (
          <SeatAllocationUserInfo
            {...{ showSeatScreen: this.showSeatScreen, hasAllowcatedSeat }}
          />
        )}
      </>
    );
  }
}

export default Dashboard;
