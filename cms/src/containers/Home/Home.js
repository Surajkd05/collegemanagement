import React, { Component } from "react";
import Aux from "../../hoc/Aux/aux";
import classes from "./Home.module.css";
import Slideshow from "./SlideShow/SlideShow";
import img1 from "../../assets/images/event1.jpeg";
import library from "../../assets/images/library.jpg"
import cafeteria from "../../assets/images/cafeteria.jpeg"
import college from "../../assets/images/college.jpeg"
import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";
import "react-super-responsive-table/dist/SuperResponsiveTableStyle.css";

class Home extends Component {
  render() {
    return (
      <Aux>
        <section className="bg-white ml-5 mr-5">
          <div className={classes.Home}>
            <h1>Welcome to IMSEC</h1>
          </div>
          <div>
            <Slideshow />
          </div>
        </section>
        <section className="bg-white ml-5 mr-5" style={{ paddingLeft: "10px" }}>
          <div>
            <p className="bg-secondary ml-5 mr-5">
              Right now home page is static.
              <br />
              Steps to follow :-
              <br />
              For Students, please register yourself and after login please add
              your placement data using placement link, there you will get a
              addplacement button at right corner of the page.
            </p>
          </div>
        </section>

        <section className="bg-white ml-5 mr-5" style={{ paddingLeft: "20px" }}>
          <div className="row">
            <p className="ml-5 mr-5 mt-5 text-secondary" style={{ paddingLeft: "10px" }}>
              LATEST FROM THE GALLERY
            </p>
          </div>
          <div>
            <div className="row" style={{ paddingLeft: "20px" }}>
              <div className="col-md-3" style={{ padding: "10px" }}>
                <div className={classes.BoxInner}>
                  <img
                    width="215px"
                    height="315px"
                    src={img1}
                    className="part1"
                    alt=""
                  />
                  <figcaption className="img-caption">EVENTS</figcaption>
                </div>
              </div>
              <div className="col-md-3" style={{ padding: "10px" }}>
                <div className={classes.BoxInner}>
                  <img
                    width="215px"
                    height="315px"
                    src={college}
                    className="part1"
                    alt=""
                  />
                  <figcaption className="img-caption">COLLEGE</figcaption>
                </div>
              </div>
              <div className="col-md-3" style={{ padding: "10px" }}>
                <div className={classes.BoxInner}>
                  <img
                    width="215px"
                    height="315px"
                    src={library}
                    className="part1"
                    alt=""
                  />
                  <figcaption className="img-caption">LIBRARY</figcaption>
                </div>
              </div>
              <div className="col-md-3" style={{ padding: "10px" }}>
                <div className={classes.BoxInner}>
                  <img
                    width="215px"
                    height="315px"
                    src={cafeteria}
                    className="part1"
                    alt=""
                  />
                  <figcaption className="img-caption">CAFETERIA</figcaption>
                </div>
              </div>
            </div>
          </div>
        </section>
        <section className="bg-secondary ml-5 mr-5" style={{ height: "50px" }}>
          <div>
            <footer style={{ overflow: "hidden", padding: "10px" }}>
              <div
                className="text-white ml-5"
                id="left"
                style={{ float: "left" }}
              >
                copyright@IMSEC
              </div>
              <div
                className="text-white mr-3"
                id="right"
                style={{ float: "right" }}
              >
                ALL RIGHT RESERVED
              </div>
            </footer>
          </div>
        </section>
      </Aux>
    );
  }
}

export default Home;
