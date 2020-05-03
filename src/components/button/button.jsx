import React from 'react';
import Button from 'react-bootstrap/Button';

const button = (props) =>{
    
    return <Button 
                type={props.type}
                className={props.className}
                onClick={props.onClick}
                {...props}
            >
                {props.name}
            </Button>;
}

export default button;