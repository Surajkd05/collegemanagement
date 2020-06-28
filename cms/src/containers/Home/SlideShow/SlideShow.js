import React from "react";
import { Slide } from "react-slideshow-image";
import img1 from "../../../assets/images/college2.jpeg";
import img2 from "../../../assets/images/college3.jpeg";
import img3 from "../../../assets/images/college1.jpeg";
import classes from "./SlideShow.module.css";

const properties = {
  duration: 5000,
  transitionDuration: 500,
  infinite: true,
  indicators: true,
  arrows: true,
};

const Slideshow = () => {
  return (
    <div className={classes.containerSlide}>
      <Slide {...properties}>
        <div className="each-slide">
          <div>
            <img src={img1} alt="img1" />
          </div>
        </div>
        <div className="each-slide">
          <div>
            <img src={img2} alt="img2" />
          </div>
        </div>
        <div className="each-slide">
          <div>
            <img src={img3} alt="img3" />
          </div>
        </div>
      </Slide>
    </div>
  );
}

export default Slideshow;