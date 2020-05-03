import React from 'react';
import Spinner from 'react-bootstrap/Spinner';

const spinner = (props) =>{
    const { className } = props;
    const styles = `spinner ${className}`
    return <Spinner animation="border" role="status" className={styles} >
                <span className="sr-only">Loading...</span>
            </Spinner>
}

export default spinner;