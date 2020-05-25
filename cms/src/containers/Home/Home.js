import React, { Component } from "react";
import Aux from "../../hoc/Aux/aux";
import classes from "./Home.module.css"

class Home extends Component {
  render() {
    return (
      <Aux>
        <div className = {classes.Home}>
            <h1>Welcome to my app</h1>
        </div>
      </Aux>
    );
  }
}

export default Home;
