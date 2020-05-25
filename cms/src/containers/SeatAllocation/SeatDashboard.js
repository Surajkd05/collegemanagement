import React, { Component } from 'react';
import { connect } from 'react-redux';
import Row from 'react-bootstrap/Row'; 
import Col from 'react-bootstrap/Col';
import PieChart from '../../components/chart/PieChart';
import Spinner from "../../components/UI/Spinner/Spinner"
// import { actions } from '../../store/actions';
// import Constants from '../constants/index';
// import axios from 'axios';

class dashboardView extends Component{
    constructor( props ) {
        super( props );
        this.state = {
            isloading: true
        }
    }

    
    getOptions = () => {
        return this.props.allProjectRoom.map( ( room ) => {
            return {
                roomName: room.projectRoomName,
                roomId: room.roomId
            };
        } );
    }

    render() {

        if( this.state.isloading ) {
            return(
                <Spinner />
            );
        }

        return(
            <>
            <div className="dashboard">Dashboard</div>
            <Row>
                <Col xs={6}>
                    <PieChart dropdownOptions={ this.getOptions() } />
                </Col>
            </Row>
        </>
        );
    }

}

export default connect( ( state ) => {
} )( dashboardView );
