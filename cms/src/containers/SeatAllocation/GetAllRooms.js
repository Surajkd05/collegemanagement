import React, { useState, useEffect } from "react";
import classes from "./RoomView.module.css";
import Button from "../../components/UI/Button/Button";
import axios from "../../axios-college";
import Spinner from "../../components/UI/Spinner/Spinner";
import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";
import "react-super-responsive-table/dist/SuperResponsiveTableStyle.css";
import GetSeat from "./GetSeat";
import { connect } from "react-redux";
import * as actions from "../../store/actions/index";

const GetAllRoom = (props) => {
  const role = localStorage.getItem("role");
  const [loading, setLoading] = useState(false);

  const [rooms, setRooms] = useState();
  const [view, setView] = useState(false);


  useEffect(() => {
    axios({
      method: "GET",
      url: "master/getAllProjectRooms",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
    })
      .then((response) => {
        setLoading(false);
        setRooms(response.data);
        setView(true);
      })
      .catch((error) => {
        setLoading(false);
        alert(error.response.data.message);
      });
  }, []);

  const getSeatHandler = (id) => {
    props.history.push({
      pathname: "/getSeat",
      state: {
        roomId: id,
      },
    });
  };

  const createSeatHandler = (roomId, deptId) => {
    const createSeat = { roomId: roomId, deptId: deptId };
    axios({
      method: "POST",
      url: "master/createSeat",
      data: createSeat,
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
    })
      .then((response) => {
        console.log("metadata : ", response.data);
        alert(response.data);
      })
      .catch((error) => {
        console.log(" error is : ", error);
        alert(error);
      });
  };

  if (loading) {
    return <Spinner />;
  }

  let roomView = null;

  if (view) {
    roomView = (
      <section className={classes.RoomView}>
        <h4>Fetched Rooms</h4>
        <Table>
          <Thead>
            <Tr>
              <Th>S.No</Th>
              <Th>Room Id</Th>
              <Th>Floor Number</Th>
              <Th>Block</Th>
              <Th>Dept Id</Th>
              <Th>College Id</Th>
              <Th>Total Capacity</Th>
              <Th>Room Name</Th>
              {role === "admin" ? <Th>Create Seat</Th> : null}
              <Th>Get Seat</Th>
            </Tr>
          </Thead>
          <Tbody>
            {rooms.map((room, count) => (
              <Tr key={room.roomId}>
                <Td>{count + 1}</Td>

                <Td>{room.roomId}</Td>
                <Td>{room.floorNo}</Td>
                <Td>{room.block}</Td>
                <Td>{String(room.deptId)}</Td>
                <Td>{room.collegeId}</Td>
                <Td>{room.totalCapacity}</Td>
                <Td>{room.roomName}</Td>
                {role === "admin" ? (
                  <Td>
                    <Button
                      clicked={() =>
                        createSeatHandler(room.roomId, room.deptId)
                      }
                      btnType="Success"
                    >
                      Create
                    </Button>
                  </Td>
                ) : null}
                <Td>
                  <Button
                    clicked={() => getSeatHandler(room.roomId)}
                    btnType="Success"
                  >
                    Get Seat
                  </Button>
                </Td>
              </Tr>
            ))}
          </Tbody>
        </Table>
      </section>
    );
  }
  return <div>{roomView}</div>;
};

const mapDispatchToProps = (dispatch) => {
  return {
    onCreateSeatHandler: (createSeat) =>
      dispatch(actions.onCreateSeatHandler(createSeat)),
  };
};

export default connect(null, mapDispatchToProps)(GetAllRoom);
