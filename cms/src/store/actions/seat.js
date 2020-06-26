import axios from "../../axios-college"

export const onCreateSeatHandler = (createSeat) => {
    axios({
        method: "POST",
        url: "master/createSeat",
         data: createSeat,
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      })
        .then((response) => {
          console.log("metadata : ", response.data);
          alert(response.data);
        })
        .catch((error) => {
          console.log(" error is : ", error);
          alert(error);
        });
}