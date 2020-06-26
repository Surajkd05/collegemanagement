import React from "react";
import NavigationItem from "./NavigationItem/NavigationItem";
import classes from "./NavigationItems.module.css";

const navigationItems = (props) => (
  <ul className={classes.NavigationItems}>
    <NavigationItem link="/" active>
      Home
    </NavigationItem>

    {props.isAuthenticated ? (
      <NavigationItem link="/getAllRoom" active>
        Get Room
      </NavigationItem>
    ) : null}

    {props.isAuthenticated && localStorage.getItem("role") === "admin" ? (
      <NavigationItem link="/schedule" active>
        Schedule
      </NavigationItem>
    ) : null}

    {props.isAuthenticated && localStorage.getItem("role") === "admin" ? (
      <NavigationItem link="/admin" active>
        UserInfo
      </NavigationItem>
    ) : null}

    {props.isAuthenticated && localStorage.getItem("role") === "admin" ? (
      <NavigationItem link="/createRoom" active>
        Create Room
      </NavigationItem>
    ) : null}

    {props.isAuthenticated && localStorage.getItem("role") === "admin" ? (
      <NavigationItem link="/createBranch" active>
        Create Branch
      </NavigationItem>
    ) : null}

    {props.isAuthenticated ? (
      <NavigationItem link="/complaint" active>
        Inventory Complaint
      </NavigationItem>
    ) : null}

    {props.isAuthenticated &&
    (localStorage.getItem("role") === "std" ||
      localStorage.getItem("role") === "emp") ? (
      <NavigationItem link="/time" active>
        Period
      </NavigationItem>
    ) : null}

    {props.isAuthenticated &&
    (localStorage.getItem("role") === "std" ||
      localStorage.getItem("role") === "emp") ? (
      <NavigationItem link="/profile" active>
        Profile
      </NavigationItem>
    ) : null}

<NavigationItem link="/placement">Placement</NavigationItem>
    <NavigationItem link="/preparation">Preparation</NavigationItem>
    {!props.isAuthenticated ? (
      <NavigationItem link="/auth">Auth</NavigationItem>
    ) : (
      <NavigationItem link="/logout">Logout</NavigationItem>
    )}
  </ul>
);

export default navigationItems;
