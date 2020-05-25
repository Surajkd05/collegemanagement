import React, { useEffect } from "react";
import Layout from "./hoc/Layout/Layout";
import Home from "./containers/Home/Home";
import { Route, Switch, withRouter, Redirect } from "react-router-dom";
import Logout from "./containers/Auth/Logout/Logout";
import { connect } from "react-redux";
import * as actions from "./store/actions/index";
import asyncComponent from "./hoc/asyncComponent/asyncComponent";
import Seats from "./containers/SeatAllocation/SeatDashboard"; 
import InventoryComplaint from "./containers/InventoryComplaint/InventoryComplaint";
import Scheduler from "./containers/Scheduler/Scheduler";
import TimeTableView from "./containers/Scheduler/TimeTableView/TimeTableView";
import Admin from "./containers/Admin/Admin";

const asyncAuth = asyncComponent(() => {
  return import("./containers/Auth/Auth");
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
      <Route path="/seats" component={Seats} />
      <Route path="/schedule" component={Scheduler} />
      <Route path="/complaint" component={InventoryComplaint} />
      <Route path="/time" component={TimeTableView} />
      <Route path="/admin" component={Admin} />
      <Redirect to="/" />
    </Switch>
  );

  if (props.isAuthenticated) {
    routes = (
      <Switch>
        <Route path="/logout" component={Logout} />
        <Route path="/auth" component={asyncAuth} />
        <Route path="/" exact component={Home} />
        <Redirect to="/" />
      </Switch>
    );
  }
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
