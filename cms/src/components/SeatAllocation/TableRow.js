import React from "react";
import Getchair from "./getChair";

const TableRow = (payload) => {
  const { tableData, ...rest } = payload;

  return (
    <div className="row roombx__row">
      <div className="col col-12">
        <PrintRow {...rest} rowData={tableData[0]} chairRotate={true} />
        <div className="row">
          <div className="col col-12">
            <hr className="roombx__hr" />
          </div>
        </div>
        <PrintRow {...rest} rowData={tableData[1]} />
      </div>
    </div>
  );
};

export default TableRow;

const PrintRow = (payload) => {
  const { rowData, ...rest } = payload;

  return (
    <div className="row">
      <div className="col col-12 d-flex justify-content-around">
        {rowData.map((seat) => {
          return (
            <Getchair
              {...{
                ...rest,
                key: seat.seatId,
                seat,
              }}
            />
          );
        })}
      </div>
    </div>
  );
};
