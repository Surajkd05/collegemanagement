import React from 'react';
import TableRow from './tableRow';

const MakeRoom = ( payload ) => {

    const { onChairClick, roomData, selectedSeatId } = payload;

    return(
        <div className='col col-8'>
            {
                roomData.map( ( table, index ) => {
                    return(
                        <TableRow { ...{ key:index, tableData: table, onChairClick, selectedSeatId } } />
                    );
                } )
            }
        </div>
    );
};

export default MakeRoom;