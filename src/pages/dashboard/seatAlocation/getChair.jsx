import React from 'react';

const Getchair = ( payload ) => {

    const { seat, onChairClick, selectedSeatId, chairRotate } = payload;
    const { empId, empName, seatId, isOccupied } = seat;

    let pathAttr = {
        fill: isOccupied ? '#07a99c' : seatId === selectedSeatId ? '#ff9416' : '#ffffff'
    }

    if( chairRotate ) {
        pathAttr = {
            ...pathAttr,
            transform: 'translate(-3 -4.5)'
        };
    }

    return(
        <div className='chairbox'>
            <svg onClick={ () => onChairClick( seatId ) } width="30" height="27" viewBox="0 0 30 27">
                <path { ...pathAttr } d="M6,9V4.5h4.5V9h15V4.5H30v9H6ZM28.5,21H33V16.5H28.5ZM3,21H7.5V16.5H3Zm22.5-4.5h-15v12a3.009,3.009,0,0,0,3,3h9a3.009,3.009,0,0,0,3-3Z"  />
            </svg>
            <div className='chairbox__tooltip'>
                Name : { empName }<br />
                Emp Id : { empId }<br />
                Seat No : { seatId } <br />
            </div>
        </div>
    );
};

export default Getchair;

Getchair.defaultProps = {
    chairRotate: false
};