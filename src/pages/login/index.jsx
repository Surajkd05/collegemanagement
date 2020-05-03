import React from 'react';
import PropTypes from 'prop-types';
import LoginView from './LoginComponent';
import { onValidation } from '../../common/utils/utils';
import axios from 'axios';
import Constants from '../constants/index';

class Login extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            email: '',
            password: '',
            isEmailValid: true,
            isPasswordValid: true,
            isLoginSuccess: true,
            isUserAuthenticated: true,
            isLoading: false
        }
    }

    componentDidMount(){
        document.body.classList.add('login__img');
    }

    onChange = ( event ) => {
        const { value, name } = event.currentTarget;
        this.setState( { [ name ]: value } );
    }

    onHandleLogin = () => {
        const { email, password } = this.state;
        const isValidEmail = onValidation(email, 'email');
        const isValidPsw = onValidation(password, 'password');

        this.setState({
            isEmailValid: isValidEmail,
            isPasswordValid: isValidPsw,
            isLoading: true
        });

        if(isValidEmail && isValidPsw){
            
            axios.post( Constants.API_URLS.LOGIN, {
                email,
                password
            })
            .then(response => {
                this.setState({isLoading: false});
                document.body.classList.remove('login__img');
                window.localStorage.setItem('_TOKEN', response.data.token);
                window.localStorage.setItem('email', email);
                this.props.goToPage("/dashboard");
            })
            .catch(err => this.setState({
                isUserAuthenticated: false,
                isLoading: false
            }) );
            
        }
    }

    render(){
        
        const { isEmailValid, isPasswordValid, isUserAuthenticated, isLoading, email, password} = this.state;
        return(
            <>
                <LoginView
                    onChange={this.onChange}
                    onClick={this.onHandleLogin}
                    isUserAuthenticated={isUserAuthenticated}
                    isLoading={isLoading}
                    email={email}
                    password={password}
                    isEmailValid={isEmailValid}
                    isPasswordValid={isPasswordValid}
                />
            </>
        )
    }


}

Login.propTypes = {
    onHandleLogin: PropTypes.func,
    onChange: PropTypes.func,
    email: PropTypes.string,
    password: PropTypes.string,
    isLoginSuccess: PropTypes.bool,
    isEmailValid: PropTypes.bool,
    isPasswordValid: PropTypes.bool,
}

export default Login;