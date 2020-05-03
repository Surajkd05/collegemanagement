import React from 'react';
import Button from '../../components/button/button';

const notFoundView = ( props ) => {
    const { history } = props;
    return(
        <div>
            <h2>Page Not Found</h2>
            <Button name="Go Back" onClick={ history.goBack } />
        </div>
    );

};

export default notFoundView;