import React, { useEffect, useState } from "react";
import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";
import "react-super-responsive-table/dist/SuperResponsiveTableStyle.css";
import classes from "./RoomView.module.css";
import Button from "../../components/UI/Button/Button";
import axios from "../../axios-college";
import { withRouter } from "react-router-dom";

const GetSeat = (props) => {
  const role = localStorage.getItem("role");

  const roomId = props.history.location.state.roomId;

  const [seats, setSeats] = useState();
  const [view, setView] = useState(false);

  useEffect(() => {
    axios
      .get("master/getSeatByRoom/" + roomId, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        setSeats(response.data);
        setView(true);
      })
      .catch((error) => {
        console.log("Error is : ", error);
      });
  }, [props.passedseatId]);

  const allocateSeatHandler = (seatId) => {
    props.history.push({
      pathname: "/allocateSeat",
      state: {
        seatId: seatId,
      },
    });
  };

  const deAllocateSeatHandler = (seatId) => {
    axios
      .get("seatAllocation/deallocateSeat?seatId=" + seatId, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        alert(response.data);
      })
      .catch((error) => {
        console.log(error.response);
      });
  };

  const getSeatInfoHandler = (seatId) => {
    props.history.push({
      pathname: "/getSeatInfo",
      state: {
        seatId: seatId,
      },
    });
  };

  let seatView = null;
  if (view) {
    seatView = (
      <section className={classes.RoomView}>
        <h4>Fetched seats</h4>
        <Table>
          <Thead>
            <Tr>
              <Th>S.No</Th>
              <Th>Seat Code</Th>
              <Th>Occupied</Th>
              {role === "admin" ? <Th>Allocate Seat</Th> : null}
              {role === "admin" ? <Th>DeAllocate Seat</Th> : null}
              <Th>GetSeat Info</Th>
            </Tr>
          </Thead>
          <Tbody>
            {seats.map((seat, count) => (
              <Tr key={seat.seatId}>
                <Td>{count + 1}</Td>
                <Td>{seat.seatCode}</Td>
                <Td>{String(seat.occupied)}</Td>
                {role === "admin" ? (
                  <Td>
                    <Button
                      clicked={() => allocateSeatHandler(seat.seatId)}
                      btnType="Success"
                    >
                      Allocate <i class="fa fa-user-plus" aria-hidden="true"></i>
                    </Button>
                  </Td>
                ) : null}
                {role === "admin" ? (
                  <Td>
                    <Button
                      clicked={() => deAllocateSeatHandler(seat.seatId)}
                      btnType="Success"
                    >
                      DeAllocate <i class="fa fa-user-minus" aria-hidden="true"></i>
                    </Button>
                  </Td>
                ) : null}
                <Td>
                  <Button
                    clicked={() => getSeatInfoHandler(seat.seatId)}
                    btnType="Success"
                  >
                    View <i class="fa fa-eye" aria-hidden="true"></i>
                  </Button>
                </Td>
              </Tr>
            ))}
          </Tbody>
        </Table>
      </section>
    );
  }

  return <div>{seatView}</div>;
};

export default withRouter(GetSeat);
