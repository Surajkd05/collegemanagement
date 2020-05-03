import React from 'react';
import DropdownButton from 'react-bootstrap/DropdownButton';
import Dropdown from 'react-bootstrap/Dropdown';
import logo from '../../assets/images/header_logo.png';
import avatar from '../../assets/images/avatar.png';

const headerView = (props) =>{
    const emailToName = localStorage.getItem('email');
    const name = emailToName.substring(0, emailToName.indexOf("."));
    return(
        <div className="header">
            <div className='container-fluid'>
                <div className='row justify-content-center'>
                    <div className='col col-3'>
                        <div className='header__logo'>
                            <img src={logo} alt="Logo" />
                        </div>
                    </div>
                    <div className='col col-7'></div>
                    <div className='col col-2'>
                        <div className="header__right">
                            <img src={avatar} alt="Logo" />
                            <DropdownButton variant="secondary" className="header__dropdown" title={ `${name}  ` }>
                                <Dropdown.Item onClick={props.logOut}>Logout</Dropdown.Item>
                            </DropdownButton>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default headerView;