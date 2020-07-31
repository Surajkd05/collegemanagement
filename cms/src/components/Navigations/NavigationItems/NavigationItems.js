import React from "react";
import NavigationItem from "./NavigationItem/NavigationItem";
import classes from "./NavigationItems.module.css";
import classes1 from "./NavigationItem/NavigationItem.module.css";

const navigationItems = (props) => (
  <ul className={classes.NavigationItems}>
    <NavigationItem link="/" active>
      Home <i class="fa fa-home"></i>
    </NavigationItem>

    <NavigationItem link="/paper" active>
      Add Paper <i class="fa fa-home"></i>
    </NavigationItem>

    {props.isAuthenticated && !(localStorage.getItem("role") === "admin") ? (
      <NavigationItem link="/getAllRoom" active>
        Get Room
      </NavigationItem>
    ) : null}

{props.isAuthenticated && (localStorage.getItem("role") === "emp") ? (
      <NavigationItem link="/registeredStudents" active>
        RegisteredStudents
      </NavigationItem>
    ) : null}

    {props.isAuthenticated && localStorage.getItem("role") === "admin" ? (
      <div className={classes1.NavigationItem}>
        <div className={classes.Dropdown}>
          <NavigationItem className={classes.Dropbtn} link="/admin" active>
            Admin <i class="fa fa-caret-down"></i>
          </NavigationItem>
          <div className={classes.DropdownContent}>
            <NavigationItem link="/getAllRoom" active>
              Get Room <i class="fa fa-arrow-right" aria-hidden="true"></i>
            </NavigationItem>

            <NavigationItem link="/createRoom" active>
              Create Room <i class="fa fa-plus" aria-hidden="true"></i>
            </NavigationItem>

            <NavigationItem link="/schedule" active>
              Schedule <i class="fa fa-plus" aria-hidden="true"></i>
            </NavigationItem>

            <NavigationItem link="/admin" active>
              UserInfo <i class="fa fa-arrow-right" aria-hidden="true"></i>
            </NavigationItem>

            <NavigationItem link="/createBranch" active>
              Create Branch <i class="fa fa-plus" aria-hidden="true"></i>
            </NavigationItem>

            <NavigationItem link="/addCourse" active>
              Create Course <i class="fa fa-plus" aria-hidden="true"></i>
            </NavigationItem>
          </div>
        </div>{" "}
      </div>
    ) : null}

    {props.isAuthenticated ? (
      <div className={classes1.NavigationItem}>
        <div className={classes.Dropdown}>
          <NavigationItem className={classes.Dropbtn} link="/inv" active>
            InventoryComplaint <i class="fa fa-caret-down"></i>
          </NavigationItem>
          <div className={classes.DropdownContent}>
            <NavigationItem link="/complaint" active>
              Add Complaint <i class="fa fa-comments" aria-hidden="true"></i>
            </NavigationItem>

            {props.isAuthenticated &&
            localStorage.getItem("role") === "admin" ? (
              <NavigationItem link="/viewComplaint" active>
                View Complaint
                <i class="fa fa-arrow-right" aria-hidden="true"></i>
              </NavigationItem>
            ) : null}
          </div>
        </div>{" "}
      </div>
    ) : null}

    {props.isAuthenticated &&
    (localStorage.getItem("role") === "std" ||
      localStorage.getItem("role") === "emp") ? (
      <NavigationItem link="/time" active>
        Period
      </NavigationItem>
    ) : null}

    <NavigationItem link="/placement">
      Placement <i class="fa fa-trophy" aria-hidden="true"></i>
    </NavigationItem>
    <NavigationItem link="/preparation">
      Preparation <i class="fa fa-newspaper-o" aria-hidden="true"></i>
    </NavigationItem>

    {props.isAuthenticated ? (
      <div className={classes1.NavigationItem}>
        <div className={classes.Dropdown}>
          <NavigationItem className={classes.Dropbtn} link="/sub" active>
            Subject <i class="fa fa-caret-down"></i>
          </NavigationItem>
          <div className={classes.DropdownContent}>
            {props.isAuthenticated &&
            localStorage.getItem("role") === "admin" ? (
              <NavigationItem link="/addSubject" active>
                Add Subject <i class="fa fa-plus" aria-hidden="true"></i>
              </NavigationItem>
            ) : null}

            {props.isAuthenticated &&
            localStorage.getItem("role") === "admin" ? (
              <NavigationItem link="/allocateSubject" active>
                Allocate Subject{" "}
                <i class="fa fa-user-plus" aria-hidden="true"></i>
              </NavigationItem>
            ) : null}

            {props.isAuthenticated && localStorage.getItem("role") === "emp" ? (
              <NavigationItem link="/addSubjectInfo" active>
                Add Info <i class="fa fa-plus" aria-hidden="true"></i>
              </NavigationItem>
            ) : null}

            {props.isAuthenticated ? (
              <NavigationItem link="/getSubjectInfo" active>
                Get Info <i class="fa fa-arrow-right" aria-hidden="true"></i>
              </NavigationItem>
            ) : null}
          </div>
        </div>{" "}
      </div>
    ) : null}

    {!props.isAuthenticated ? (
      <NavigationItem link="/auth">Auth</NavigationItem>
    ) : (
      <div className={classes1.NavigationItem}>
        <div className={classes.Dropdown}>
          <NavigationItem className={classes.Dropbtn} link="/acc" active>
            Account <i class="fa fa-caret-down"></i>
          </NavigationItem>
          <div className={classes.DropdownContent}>
            {props.isAuthenticated &&
            (localStorage.getItem("role") === "std" ||
              localStorage.getItem("role") === "emp") ? (
              <NavigationItem link="/profile" active>
                Profile <i class="fa fa-user"></i>
              </NavigationItem>
            ) : null}

            {props.isAuthenticated ? (
              <NavigationItem link="/updatePassword" active>
                Pasword <i class="fa fa-key"></i>
              </NavigationItem>
            ) : null}

            {
              <NavigationItem link="/logout">
                Logout{" "}
                <i class="fas fa-sign-out-alt" style={{ fontSize: "15px" }}></i>
              </NavigationItem>
            }
          </div>
        </div>{" "}
      </div>
    )}
  </ul>
);

export default navigationItems;
