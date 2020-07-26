import React, { useEffect } from "react";
import Layout from "./hoc/Layout/Layout";
import Home from "./containers/Home/Home";
import { Route, Switch, withRouter, Redirect } from "react-router-dom";
import Logout from "./containers/Auth/Logout/Logout";
import { connect } from "react-redux";
import * as actions from "./store/actions/index";
import asyncComponent from "./hoc/asyncComponent/asyncComponent";
import InventoryComplaint from "./containers/InventoryComplaint/RegisterComplaint/InventoryComplaint";
import Scheduler from "./containers/Scheduler/Scheduler";
import TimeTableView from "./containers/Scheduler/TimeTableView/TimeTableView";
import Admin from "./containers/Admin/Admin";
import DepartmentRoom from "./containers/SeatAllocation/DepartmentRoom";
import GetAllRoom from "./containers/SeatAllocation/GetAllRooms";
import AllocateSeat from "./containers/SeatAllocation/AllocateSeat";
import GetSeat from "./containers/SeatAllocation/GetSeat";
import StudentRegister from "./containers/Auth/Register/StudentRegister";
import EmployeeRegister from "./containers/Auth/Register/EmployeeRegister";
import UserProfile from "./containers/Profile/UserProfile";
import ProfileImage from "./components/profileImage/ProfileImage";
import AddAddress from "./containers/Profile/Address/AddAddress";
import UpdateUserProfile from "./containers/Profile/UpdateProfile/UpdateUserProfile";
import UserAddressUpdate from "./components/user/UserAddressUpdate";
import GetSeatInfo from "./containers/SeatAllocation/GetSeatInfo";
import UpdateStudentProfile from "./containers/Profile/UpdateProfile/UpdateStudentProfile";
import AddBranch from "./containers/Admin/AddBranch/AddBranch";
import Preparation from "./containers/Preparation/Preparation";
import ViewAnswer from "./containers/Preparation/Answer/ViewAnswer/ViewAnswer";
import Placement from "./containers/Placement/Placement";
import AddPlacement from "./containers/Placement/AddPlacement/AddPlacement";
import ViewPlacement from "./containers/Placement/ViewPlacement/ViewPlacement";
import Interview from "./containers/Preparation/Interview/Interview";
import AddSubject from "./containers/Admin/AddSubject/AddSubject";
import ResendActivation from "./containers/Auth/Login/Resend/ResendActivation";
import ResetPassword from "./containers/Auth/Password/ResetPassword";
import ForgotPassword from "./containers/Auth/Password/ForgotPassword";
import UpdateUserPassword from "./containers/Auth/Password/UpdateUserPassword";
import Subject from "./containers/Employee/Subject/Subject";
import AllocateSubject from "./containers/Admin/AllocateSubject/AllocateSubject";
import SubjectInfo from "./containers/Employee/Subject/SubjectInfo";
import ViewComplaint from "./containers/InventoryComplaint/ViewComplaint/ViewComplaint";
import ViewQuestion from "./containers/Preparation/Question/ViewQuestion/ViewQuestion";
import StudentView from "./containers/Employee/Student/StudentView";
import ViewProfile from "./containers/Profile/ViewProfile";

const asyncAuth = asyncComponent(() => {
  return import("./containers/Auth/Auth");
});

const asyncLogin = asyncComponent(() => {
  return import("./containers/Auth/Login/Login");
});

const App = (props) => {
  const { onTryAutoSignup } = props;

  useEffect(() => {
    onTryAutoSignup();
  }, [onTryAutoSignup]);

  let routes = (
    <Switch>
      <Route path="/auth" component={asyncAuth} />
      <Route path="/" exact component={Home} />
      <Route path="/studentRegister" component={StudentRegister} />
      <Route path="/preparation" component={Preparation} />
      <Route path="/answer" component={ViewAnswer} />
      <Route path="/placement" component={Placement} />
      <Route path="/interview" component={Interview} />
      <Route path="/resendCode" component={ResendActivation} />
      <Route path="/resetPassword" component={ResetPassword} />
      <Route path="/forgotPassword" component={ForgotPassword} />
      <Route path="/viewPlacement" component={ViewPlacement} />
      <Route path="/allocateSubject" component={AllocateSubject} />
      <Route path="/employeeRegister" component={EmployeeRegister} />
      <Redirect to="/" />
    </Switch>
  );

  if (props.isAuthenticated) {
    routes = (
      <Switch>
        <Route path="/logout" component={Logout} />
        <Route path="/schedule" component={Scheduler} />
        <Route path="/auth" component={asyncLogin} />
        <Route path="/admin" component={Admin} />
        <Route path="/createBranch" component={AddBranch} />
        <Route path="/placement" component={Placement} />
        <Route path="/viewPlacement" component={ViewPlacement} />
        <Route path="/addPlacement" component={AddPlacement} />
        <Route path="/updatePassword" component={UpdateUserPassword} />
        <Route path="/addSubject" component={AddSubject} />
        <Route path="/addSubjectInfo" component={Subject} />
        <Route path="/interview" component={Interview} />
        <Route path="/createRoom" component={DepartmentRoom} />
        <Route path="/getAllRoom" component={GetAllRoom} />
        <Route path="/getSeat" component={GetSeat} />
        <Route path="/allocateSeat" component={AllocateSeat} />
        <Route path="/answer" component={ViewAnswer} />
        <Route path="/complaint" component={InventoryComplaint} />
        <Route path="/time" component={TimeTableView} />
        <Route path="/question" component={ViewQuestion} />
        <Route path="/complaint" component={InventoryComplaint} />
        <Route path="/profile" component={UserProfile} />
        <Route path="/userProfile" component={ViewProfile} />
        <Route path="/upload" component={ProfileImage} />
        <Route path="/addAddress" component={AddAddress} />
        <Route path="/editEmployeeProfile" component={UpdateUserProfile} />
        <Route path="/editStudentProfile" component={UpdateStudentProfile} />
        <Route path="/preparation" component={Preparation} />
        <Route path="/updateAddress" component={UserAddressUpdate} />
        <Route path="/getSeatInfo" component={GetSeatInfo} />
        <Route path="/allocateSubject" component={AllocateSubject} />
        <Route path="/getSubjectInfo" component={SubjectInfo} />
        <Route path="/viewComplaint" component={ViewComplaint} />
        <Route path="/registeredStudents" component={StudentView} />
        <Route path="/" exact component={Home} />
        <Redirect to="/" />
      </Switch>
    );
  }

  // if (props.isAuthenticated && localStorage.getItem("role") === "std") {
  //   routes = (
  //     <Switch>
  //       <Route path="/logout" component={Logout} />
  //       <Route path="/auth" component={asyncLogin} />

  //       <Route path="/" exact component={Home} />
  //       <Redirect to="/" />
  //     </Switch>
  //   );
  // }

  // if (props.isAuthenticated && localStorage.getItem("role") === "emp") {
  //   routes = (
  //     <Switch>
  //       <Route path="/logout" component={Logout} />
  //       <Route path="/auth" component={asyncLogin} />
  //       <Route path="/time" component={TimeTableView} />
  //       <Route path="/complaint" component={InventoryComplaint} />
  //       <Route path="/profile" component={UserProfile} />
  //       <Route path="/upload" component={ProfileImage} />
  //       <Route path="/addAddress" component={AddAddress} />
  //       <Route path="/getAllRoom" component={GetAllRoom} />
  //       <Route path="/editProfile" component={UpdateUserProfile} />
  //       <Route path="/updateAddress" component={UserAddressUpdate} />
  //       <Route path="/" exact component={Home} />
  //       <Redirect to="/" />
  //     </Switch>
  //   );
  // }

  return (
    <div className="App">
      <Layout>{routes}</Layout>
    </div>
  );
};

const mapStateToProps = (state) => {
  return {
    isAuthenticated: state.auth.token !== null,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    onTryAutoSignup: () => dispatch(actions.authCheckState()),
  };
};

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(App));
