import React, { useState } from "react";
import { withRouter } from "react-router-dom";
import classes from "./ProfileImage.module.css";
import axios from "../../axios-college";
import avatar from "../../assets/images/avatar.webp";
import { ProgressBar } from "react-bootstrap";

const ProfileImage = (props) => {
  const [image, setImage] = useState({ preview: "", raw: "" });
  const [uploadPercentage, setUploadPercent] = useState();

  const handleChange = (e) => {
    if (e.target.files.length) {
      setImage({
        preview: URL.createObjectURL(e.target.files[0]),
        raw: e.target.files[0],
      });
    }
  };

  const handleUpload = async (e) => {
    e.preventDefault();

    const formData = new FormData();
    formData.append("image", image.raw);

    const options = {
      onUploadProgress: (progressEvent) => {
        const { loaded, total } = progressEvent;
        let percent = Math.floor((loaded * 100) / total);
        console.log(`${loaded}kb of ${total}kb | ${percent}%`);

        if (percent < 100) {
          setUploadPercent(percent);
        }
      },
    };

    // axios({
    //   method: "POST",
    //   url: "image/",
    //   data: formData,
    //   headers: {
    //     Authorization: "Bearer " + localStorage.getItem("token"),
    //   },
    //   options: options
    // })
    axios
      .post("image/", formData, options, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
      .then((response) => {
        setUploadPercent(100, () => {
          setTimeout(() => {
            setUploadPercent(0);
          }, 1000);
        });

        props.history.push("/profile");
      })
      .catch((error) => {
        console.log(error.response);
      });
  };

  return (
    <div className={classes.container}>
      <label htmlFor="upload-button">
        {image.preview ? (
          <img src={image.preview} alt="dummy" width="300" height="200" />
        ) : (
          <>
            <span className="fa-stack fa-2x mt-3 mb-2">
              <i className="fas fa-store fa-stack-1x fa-inverse" />
            </span>
            <h5 className={classes.uploadImage}>
              <img src={avatar} alt="dummy" width="300" height="200" />
            </h5>
          </>
        )}
      </label>
      <input
        type="file"
        id="upload-button"
        style={{ display: "none" }}
        onChange={handleChange}
      />
      <br />
      {uploadPercentage > 0 && (
        <ProgressBar
          now={uploadPercentage}
          active
          label={`${uploadPercentage}%`}
        />
      )}
      <button onClick={handleUpload}>Upload</button>
    </div>
  );
};

export default withRouter(ProfileImage);
