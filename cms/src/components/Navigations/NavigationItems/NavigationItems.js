import React from "react";
import NavigationItem from "./NavigationItem/NavigationItem";
import classes from "./NavigationItems.module.css";

const navigationItems = (props) => (
  <ul className={classes.NavigationItems}>
    <NavigationItem link="/" active>
      Home
    </NavigationItem>

    <NavigationItem link="/seats" active>
      Seats
    </NavigationItem>

    <NavigationItem link="/schedule" active>
      Schedule
    </NavigationItem>

    <NavigationItem link="/complaint" active>
      Inventory Complaint
    </NavigationItem>

    <NavigationItem link="/time" active>
      Period
    </NavigationItem>

    <NavigationItem link="/admin" active>
      UserInfo
    </NavigationItem>

    {!props.isAuthenticated ? (
      <NavigationItem link="/auth">Auth</NavigationItem>
    ) : (
      <NavigationItem link="/logout">Logout</NavigationItem>
    )}
  </ul>
);

export default navigationItems;
