import React, { useEffect, useState, useMemo } from "react";
import classes from "./AddressView.module.css";
import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";
import "react-super-responsive-table/dist/SuperResponsiveTableStyle.css";
import * as actions from "../../../store/actions/index";
import { connect } from "react-redux";
import Aux from "../../../hoc/Aux/aux";
import axios from "../../../axios-college";
import Button from "../../../components/UI/Button/Button";
import UserAddressUpdate from "../../../components/user/UserAddressUpdate";
import { withRouter } from "react-router-dom";

const AddressView = React.memo((props) => {

  const [addresses, setAddresses] = useState();
  const [isAddress, setIsAddress] = useState(false);

  const token = localStorage.getItem("token");
  useEffect(() => {
    //onFetchuserAddress(token);
    axios
      .get("user/address", {
        headers: { Authorization: "Bearer " + token },
      })
      .then((response) => {
        setAddresses(response.data);
        setIsAddress(true);
      })
      .catch((error) => {
        alert(error.response.data.message)
      });
  }, []);

  const deleteAddressHandler = (id) => {
    props.onDeleteUserAddress(id);
  };

  const updateAddressHandler = (id, address) => {
  props.history.push({pathname: "/updateAddress", state: {
    id : id,
    address: address
  }})
  };

  let addressView = null;
  if (isAddress) {
    addressView = (
      <section className={classes.AddressView}>
        <h4>User Address</h4>
        <Table>
          <Thead>
            <Tr>
              <Th>S.No</Th>
              {/* <Th>Address Id</Th>
              <Th>User Id</Th> */}
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
              <Th>Delete</Th>
              <Th>Update</Th>
            </Tr>
          </Thead>
          <Tbody>
            {addresses.map((address, count) => (
              <Tr key={address.id}>
                <Td>{count + 1}</Td>
                {/* <Td>{address.id}</Td>
                <Td>{address.userId}</Td> */}
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
                <Td>
                  <Button
                    clicked={() => deleteAddressHandler(address.addressId)}
                    btnType="Danger"
                  >
                    Delete
                  </Button>
                </Td>
                <Td>
                  <Button
                    clicked={() => updateAddressHandler(address.addressId, address)}
                    btnType="Success"
                  >Update</Button>
                    {/* {!isUpdate ? (
                      <Button clicked={() => updateAddressHandler(address.addressId, address)} btnType="Success">Update</Button>
                    ) : (
                      <Button clicked={() => updateAddressHandler(address.addressId, address)} btnType="Danger">Hide Update</Button>
                    )} */}
                  {/* </Button> */}
                </Td>
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

const mapStateToProps = (state) => {
  return {
    addresses: state.user.addresses,
    loading: state.user.loading,
    error: state.user.error,
    token: state.auth.token,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    onFetchUserAddress: (token) =>
      dispatch(actions.fetchUserAddress(token)),
    onDeleteUserAddress: (id) =>
      dispatch(actions.onDeleteUserAddress(id)),
  };
};

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(AddressView));
