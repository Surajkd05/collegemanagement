import React from 'react';
import axios from 'axios';
import { isEmpty, toString } from 'lodash';
import Constants from '../../constants/index';
import { connect } from 'react-redux';

class SeatAlocationUserInfoView extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoading: false,
            hasAllocatedSeat: false,
            userInfo: {
                empId: '',
                empName: '',
                room: '',
                remarks: '',
                email: '',
                seatId: ''
            }
        };

    }


    getUserData( empId ) {

        axios(Constants.API_URLS.GET_EMPLOYEE_BY_ID + empId.toUpperCase(), {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(result => {
            this.setState({ isLoading: false, userInfo: {
                ...this.state.userInfo,
                empId: result.data[ 0 ].employeeId,
                empName: result.data[ 0 ].ownerName,
                city: result.data[ 0 ].clientLocation,
                email: result.data[ 0 ].email
            } }, this.getSeatInfoData );
        }).catch( () => {
            this.setState({ isLoading: false, userInfo: {
                ...this.state.userInfo,
                empName: '',
                email: ''
            } });
        } );
    }

    getSeatInfoData() {
        const { userInfo } = this.state;
        axios(Constants.API_URLS.GET_SEAT_INFO_BY_EMPID + userInfo.empId, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then( result => {
            
            this.setState({ isLoading: false, hasAllocatedSeat: ! isEmpty( result.data ), userInfo: {
                ...this.state.userInfo,
                seatId: result.data.seatId || ''
            } } );
        });
    }

    onInputChange = ( payload ) => {

        const { id, value } = payload;
        const _state = {
            ...this.state,
            userInfo: {
                ...this.state.userInfo,
                [ id ]: value
            }
        };
    
        if( 'empId' === id ) {
            this.setState( _state, () => {

                if( this.state.userInfo.empId.length > 5 ) {
                    this.getUserData( this.state.userInfo.empId );
                }
            } );
        } else {
            this.setState( _state  );
        }
    }

    onSubmit = ( event ) => {

        const { type } = event.currentTarget;
        const { userInfo } = this.state;
        this.props.showSeatScreen( { userInfo, actionHasalocated: ( type === 'button' ) } );
    };

    isButtonDisabled = () => {

        const { room, empId, empName } = this.state.userInfo;
        return ( isEmpty( toString( room ) ) || isEmpty( empId ) || isEmpty( empName ) );
    }

    render() {

        const { userInfo, isLoading, hasAllocatedSeat } = this.state;

        return (
            <div className='container-fluid seat'>
                <div className='row'>
                    <div className='col col-12'>
                        <h2 className="seat__title">{ hasAllocatedSeat ? 'Update' : '' } Seat Allocation</h2>
                    </div>
                </div>
            
                { isLoading ? <div className='roombx roombx__modify'>Loading...</div>: 
                    <>
                       
                        <div className='row'>
                            <div className='col col-8'></div>
                            <div className='col col-4'>
                                <div className='row'>
                                    <div className='col col-10 text-right'>
                                        { 
                                            ! hasAllocatedSeat ?
                                            <button type="button" className="btn btn-sm seat__btn" onClick={ this.onSubmit } disabled={ this.isButtonDisabled() }>Let Me Allocate Seat</button>
                                            : 
                                            <>
                                                <button type="reset" className="btn btn-sm btn-outline-danger" onClick={ this.onSubmit } disabled={ this.isButtonDisabled() }>De-Allocate Seat</button> 
                                                <button type="button" className="btn btn-sm btn-outline-secondary" onClick={ this.onSubmit } disabled={ this.isButtonDisabled() }> Re-Allocate Seat</button>
                                            </>
                                        }
                                        
                                    </div>
                                    <div className='col col-2'></div>
                                </div>
                            </div>
                        </div>
                    </>
                }
            </div>
        );
    }

}
export default connect((state) => {
}, null)(SeatAlocationUserInfoView);

const formOrder = ( payload ) => {

    const _data = [
        [
            {
                id: 'empId',
                displayLabel: 'Emp Id',
                placeholder: 'Enter Employee Id',
                type: 'input',
                disabled: false
            },
            {
                id: 'empName',
                displayLabel: 'Name',
                placeholder: '',
                type: 'input',
                disabled: true
            }
        ],
       
        [

            {
                id: 'room',
                displayLabel: 'Project Room',
                placeholder: '',
                type: 'select',
                options: [],
                disabled: false
            }
        ],
        [
            {
                id: 'remarks',
                displayLabel: 'Remarks',
                placeholder: '',
                type: 'input',
                disabled: false
            }
        ]
    ];


const PrintFrom = ( payload ) => {

    const { onInputChange, userInfo } = payload;

    return formOrder( payload ).map((row, index) => {
        return (
            <div className='row seat__seatRow' key={index}>
                {
                    row.map((col) => {
                        return (
                            <React.Fragment key={col.id}>
                                <div className='col col-2 seat__labeltext'>{col.displayLabel}</div>
                                <div className='col col-4'>
                                    <div className='row'>
                                        <div className='col col-10'>
                                            <GetField {...{ ...col, onInputChange, userInfo }} />
                                        </div>
                                        <div className='col col-2'></div>
                                    </div>
                                </div>
                            </React.Fragment>
                        );
                    })
                }
            </div>
        );
    });
};

const GetField = ({ type, placeholder, onInputChange, id, userInfo,
    disabled, options = [] }) => {

    switch (type) {

        case 'select': {
            return ( <select className="custom-select seat__input" onChange={(event) => {
                onInputChange({ id:id, value:event.currentTarget.value });
            }} >
                <option value=''>Select</option>
                { options.map( (opt ) => {
                    return( <option key={ opt.value } defaultValue={ opt.value === userInfo[ id ] } value={ opt.value }>{ opt.displayLabel }</option> );
                } ) }
            </select>);
        }

        default: {
            return (<input type="text" className="form-control seat__input"
                placeholder={placeholder} onChange={(event) => {
                    onInputChange({ id:id, value:event.currentTarget.value });
                }} value={ userInfo[ id ] } disabled={ disabled } />);
        }
    }

};
}