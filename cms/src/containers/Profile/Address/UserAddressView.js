import React, { useEffect, useState} from "react";
import classes from "./AddressView.module.css";
import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";
import "react-super-responsive-table/dist/SuperResponsiveTableStyle.css";
import Aux from "../../../hoc/Aux1/aux1";
import axios from "../../../axios-college";
import { withRouter } from "react-router-dom";

const AddressView = React.memo((props) => {

  const [addresses, setAddresses] = useState();
  const [isAddress, setIsAddress] = useState(false);

  useEffect(() => {
    axios
      .get("user/address1?userId="+props.userId.userId, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        setAddresses(response.data);
        setIsAddress(true);
      })
      .catch((error) => {
        alert(error.response.data.message)
      });
  }, []);

  let addressView = null;
  if (isAddress) {
    addressView = (
      <section className={classes.AddressView}>
        <h4>User Address</h4>
        <Table>
          <Thead>
            <Tr>
              <Th>S.No</Th>
              <Th>Block</Th>
              <Th>Plot Number</Th>
              <Th>Sector Number</Th>
              <Th>Street Name</Th>
              <Th>City</Th>
              <Th>District</Th>
              <Th>State</Th>
              <Th>Country</Th>
              <Th>Label</Th>
              <Th>ZipCode</Th>
            </Tr>
          </Thead>
          <Tbody>
            {addresses.map((address, count) => (
              <Tr key={address.id}>
                <Td>{count + 1}</Td>
                <Td>{address.block}</Td>
                <Td>{address.plotNumber}</Td>
                <Td>{address.sectorNumber}</Td>
                <Td>{address.streetName}</Td>
                <Td>{address.city}</Td>
                <Td>{address.district}</Td>
                <Td>{address.state}</Td>
                <Td>{address.country}</Td>
                <Td>{address.label}</Td>
                <Td>{address.zipCode}</Td>
              </Tr>
            ))}
          </Tbody>
        </Table>
      </section>
    );
  }

  return (
    <Aux>
      <div>{addressView}</div>
    </Aux>
  );
});


export default withRouter(AddressView);
