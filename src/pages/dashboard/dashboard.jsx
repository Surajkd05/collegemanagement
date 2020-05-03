import React from 'react';
import { connect } from 'react-redux';
import Row from 'react-bootstrap/Row'; 
import Col from 'react-bootstrap/Col';
import PieChart from '../../components/chart/pieChart';
import BarChart from '../../components/chart/barChart';
import { actions } from '../../store/actions';
import Constants from '../constants/index';
import axios from 'axios';

class dashboardView extends React.Component{
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
                <div className="dashboard">loading...</div>
            );
        }

        return(
            <>
            <div className="dashboard">Dashboard</div>
            <Row>
                <Col xs={6}>
                    <PieChart dropdownOptions={ this.getOptions() } />
                </Col>
                <Col xs={6}>
                    <BarChart dropdownOptions={ this.getOptions() } />
                </Col>
            </Row>
        </>
        );
    }

}

export default connect( ( state ) => {
} )( dashboardView );
