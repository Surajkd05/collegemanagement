import React, { useEffect, useState } from "react";
import { withRouter } from "react-router-dom";
import Button from "../../../components/UI/Button/Button";
import axios from "../../../axios-college";
import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";
import "react-super-responsive-table/dist/SuperResponsiveTableStyle.css";
import classes from "./ViewComplaint.module.css";

const ComplaintView = (props) => {
  const [complaintData, setComplaintData] = useState(null);

  const [count, setCount] = useState(0);

  const [url, setUrl] = useState("inventory/all?page=");

  const [token, setToken] = useState("Search complaint by token");

  useEffect(() => {
    axios
      .get("inventory/all?page=" + count)
      .then((response) => {
        setComplaintData(response.data);
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
  }, []);

  const onClickHandler = (url) => {
    axios
      .get(url)
      .then((response) => {
        setComplaintData(response.data);
        setToken("Search complaint by token");
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
  };

  const onChangeHandler = (event) => {
    setToken(event.target.value);
  };

  const pageChangeHandler = (page) => {
    setCount(page);
    axios
      .get(url + page)
      .then((response) => {
        setComplaintData(response.data);
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
  };

  const closeComplaintHandler = (token) => {
    axios({
      method: "PATCH",
      url: "inventory/complaintResolved?token=" + token,
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
    })
      .then((response) => {
        alert(response.data);
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
  };

  const reopenComplaintHandler = (token) => {
    axios({
      method: "PATCH",
      url: "inventory/reopenComplaint?token=" + token,
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
    })
      .then((response) => {
        alert(response.data);
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
  };

  let complaintView = null;
  if (complaintData !== null) {
    complaintView = (
      <section className={classes.UserView}>
        <h4>Fetched Complaint Details</h4>
        <Table>
          <Thead>
            <Tr>
              <Th>S.No</Th>
              <Th>Block</Th>
              <Th>Room</Th>
              <Th>Complaint By</Th>
              <Th>Inventory Name</Th>
              <Th>Quantity</Th>
              <Th>Token</Th>
              <Th>Active</Th>
              <Th>Close Complaint</Th>
              <Th>Reopen Complaint</Th>
            </Tr>
          </Thead>
          <Tbody>
            {complaintData.map((data, count) => (
              <Tr key={data.id}>
                <Td key={data.id}>{count + 1}</Td>

                <Td>{data.block}</Td>
                <Td>{data.room}</Td>
                <Td>{data.complaintBy}</Td>
                <Td>{data.inventoryName}</Td>
                <Td>{data.quantity}</Td>
                <Td>{data.token}</Td>
                <Td>{String(data.active)}</Td>
                <Td>
                  <Button
                    clicked={() => closeComplaintHandler(data.token)}
                    btnType="Success"
                  >
                    Close<i class="fa fa-close" style={{ color: "red"}}></i>
                  </Button>
                </Td>
                <Td>
                  <Button
                    clicked={() => reopenComplaintHandler(data.token)}
                    btnType="Danger"
                  >
                    Reopen<i class="fa fa-unlock" style={{ color: "green" }}></i>
                  </Button>
                </Td>
              </Tr>
            ))}
          </Tbody>
        </Table>
      </section>
    );
  }

  return (
    <div>
      <div className="row">
        <div className="col-md-4" />
        <div className="col-md-4">
          <input type="text" value={token} onChange={onChangeHandler} />
          <Button
            btnType="Success"
            clicked={() => onClickHandler("inventory/token?token=" + token)}
          >
            <i className="fa fa-search"></i>
          </Button>
        </div>
        <div className="col-md-4" />
      </div>

      {complaintData !== null ? (
        <div>
          {complaintView}
          <div className="row" style={{ paddingLeft: "30px" }}>
            <div className="col-md-1">
              {count !== 0 ? (
                <Button
                  btnType="Success"
                  clicked={() => pageChangeHandler(count - 1)}
                >
                  Prev
                </Button>
              ) : null}
            </div>
            <div className="col-md-10" />
            <div className="col-md-1">
              <Button
                btnType="Success"
                clicked={() => pageChangeHandler(count + 1)}
              >
                Next
              </Button>
            </div>
          </div>
        </div>
      ) : null}
    </div>
  );
};

export default withRouter(ComplaintView);
