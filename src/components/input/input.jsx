import React from 'react';
import Form from 'react-bootstrap/Form';

const input = (props) =>{
    const inputField =
        <Form.Group controlId={props.id}>
            {(props.label) ? <Form.Label>{props.label}</Form.Label> : null}
            <Form.Control
                type={props.type}
                placeholder={props.placeholder}
                name={props.name}
                className={props.className}
                value={props.value}
                disabled={props.disabled}
                onChange={props.onChange}
            />
        </Form.Group>
    
    return inputField;
}

export default input;