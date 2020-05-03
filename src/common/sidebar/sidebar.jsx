import React from 'react';
import { NavLink} from "react-router-dom";
import HomeIcon from '../../assets/images/home_nav_icon.svg';
import LocationIcon from '../../assets/images/location_nav_icon.svg';
import ProjectIcon from '../../assets/images/project_nav_icon.svg';
import PremisesIcon from '../../assets/images/premises_Icon.svg';
import SeatmapIcon from '../../assets/images/seatmap_icon.svg';
import ReportIcon from '../../assets/images/report_icon.svg';


const sidebarView = (props) =>{

    const sidebar = 
        <div className="sidebar">
            <NavLink exact activeClassName='active' to="/dashboard"><img src={HomeIcon} alt="" className="sidebar__icon" />Dashboard</NavLink >
            <NavLink exact activeClassName='active' to="/employess"><img src={LocationIcon} alt="" className="sidebar__icon" />Location</NavLink >
            <NavLink exact activeClassName='active' to="/employess"><img src={ProjectIcon} alt="" className="sidebar__icon" />Project</NavLink >
            <NavLink exact activeClassName='active' to="/employess"><img src={PremisesIcon} alt="" className="sidebar__icon" />Premises</NavLink >
            <NavLink exact activeClassName='active' to="/employess"><img src={SeatmapIcon} alt="" className="sidebar__icon" />Seatmap</NavLink >
            <NavLink exact activeClassName='active' to="/report"><img src={ReportIcon} alt="" className="sidebar__icon" />Report</NavLink >        
       
        </div>

    return sidebar;
}

export default sidebarView;