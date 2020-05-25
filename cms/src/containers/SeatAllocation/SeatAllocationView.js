import React from "react";
import axios from "axios";
import SystemIcom from "../../../assets/images/system_icon.svg";
import WaveIcon from "../../../assets/images/wave_square_icon.svg";
import { connect } from "react-redux";
import { toString, isEmpty } from "lodash";
import MakeRoom from "./makeRoom";
import Constants from "../../constants/index";
import { roomData } from "./roomdata";

class SeatAlocationView extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isLoading: true,
      seatList: [],
      selectedSeatId: "",
      seatAlocated: false,
    };
  }

  componentDidMount() {
    if (!this.props.actionHasalocated) {
      this.onDoneClick();
    } else {
      this.getRoomData(this.props.userInfo.room);
    }
  }

  getRoomData = (roomId) => {
    axios(Constants.API_URLS.GET_ALL_SEATS + roomId, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    }).then((result) => {
      this.setState({ isLoading: false, seatList: result.data });
    });
  };

  getProjectName = () => {
    return this.props.allProjectRoom.find((room) => {
      return toString(this.props.userInfo.room) === toString(room.roomId);
    }).projectRoomName;
  };

  onChairClick = (seatId) => {
    this.setState({ selectedSeatId: seatId });
  };

  getSeatCode = () => {
    const { seatList, selectedSeatId } = this.state;
    return seatList.find((seat) => {
      return toString(selectedSeatId) === toString(seat.seatId);
    }).seatCd;
  };

  onDoneClick = () => {
    const { actionHasalocated, userInfo } = this.props;
    const { city, country, empId, empName, email, seatId } = userInfo;

    const url = actionHasalocated
      ? Constants.API_URLS.GET_ALL_LOC_SEAT
      : Constants.API_URLS.GET_ALL_DELOC_SEAT;

    axios
      .post(url, {
        city,
        country,
        empId,
        empName,
        seatId: actionHasalocated ? this.state.selectedSeatId : seatId,
      })
      .then(() => {
        this.setState({ seatAlocated: true, isLoading: false }, () => {
          const _msg = actionHasalocated
            ? `Seat Allocated to the Employee Name: ${empName} Is ${this.getSeatCode()} Location as Gurgaon`
            : `Seat De-allocated to the Employee Name: ${empName} Location as Gurgaon`;

          axios.post(Constants.API_URLS.MAIL_SEND, {
            emailAddress: email,
            empName: empName,
            msg: _msg,
          });
        });
      });
  };

  render() {
    const { isLoading, seatAlocated, selectedSeatId, seatList } = this.state;
    const { userInfo, actionHasalocated } = this.props;
    const { empName, empId, room } = userInfo;
    let view = <div className="roombx roombx__modify">Loading...</div>;
    if (!isLoading) {
      if (seatAlocated) {
        view = (
          <div className="roombx roombx__modify">
            <p>
              Seat {!actionHasalocated ? "De-" : ""}allocated to {empName}{" "}
              {empId}. <br />
              Employee will receive update on his mail shortly!.
            </p>
          </div>
        );
      } else {
        const _data = roomData.composeData({ seatList, room });

        view = (
          <React.Fragment>
            <div className="roombx">
              <div className="roombx__doors">
                {new Array(_data.doors).fill(undefined).map((_d, index) => (
                  <div key={index} className="roombx__door" />
                ))}
              </div>
              <div className="container-fluid">
                <div className="row align-items-center justify-content-center">
                  <div className="col col-1 d-flex justify-content-center">
                    <img src={WaveIcon} alt="waive" />
                  </div>
                  <div className="col col-1 d-flex justify-content-center">
                    <img src={SystemIcom} alt="SystemIcom" />
                  </div>
                  <MakeRoom
                    {...{
                      onChairClick: this.onChairClick,
                      selectedSeatId,
                      roomData: _data.roomData,
                    }}
                  />
                  <div className="col col-1 d-flex justify-content-center">
                    <img src={SystemIcom} alt="SystemIcom" />
                  </div>
                  <div className="col col-1 d-flex justify-content-center">
                    <img src={WaveIcon} alt="waive" />
                  </div>
                </div>
              </div>
            </div>
            <div className="row">
              <div className="col col-12 text-right">
                <button
                  type="button"
                  className="btn btn-sm btn-outline-secondary roombx__btn"
                  disabled={isEmpty(selectedSeatId)}
                  onClick={this.onDoneClick}
                >
                  Done
                </button>
              </div>
            </div>
          </React.Fragment>
        );
      }
    }

    return (
      <div className="container-fluid seat">
        <div className="row">
          <div className="col col-12">
            <h2 className="seat__title">
              Seat Allocation - {this.getProjectName()}
            </h2>
          </div>
        </div>
        {view}
      </div>
    );
  }
}

export default connect((state) => {
  const { allProjectRoom } = state.ruducers;

  return {
    allProjectRoom,
  };
}, null)(SeatAlocationView);
