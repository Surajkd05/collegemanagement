import React from 'react';
import Form from '../../components/form/form';
import Input from '../../components/input/input';
import Button from '../../components/button/button';
import Spinner from '../../components/spinner/spinner';
import LoginLogo from '../../assets/images/login_logo.png';
import LoginFooterLogo from '../../assets/images/footer_login_logo.png';

const loginView = (props) =>{
    const { email, password, onChange, onClick, isEmailValid, isPasswordValid, isUserAuthenticated, isLoading } = props;
    return (
        <div className='login container-fluid'>
            <div className='row justify-content-center '>
                <div className='col col-3'>
                    <div className='row'>
                        <div className='col col-12 justify-content-center d-flex flex-column' style={ { 'height': '90vh' } }>
                            <img src={LoginLogo}  alt='logo' /><br />
                            <h2 className='login__heading'>Imsec Login</h2> 
                        {isLoading ? 
                                <Spinner /> :
                                <Form>
                                    {(!isUserAuthenticated) ? <p className="login__validation">Please enter valid Email or Passwod</p> : ''}
                                    {(!isEmailValid) ? <p className="login__validation">Please enter valid email</p> : ''}
                                    <Input type="text" name="email" value={ email } placeholder="Email" className="login__input" onChange={onChange} />
                                    {(!isPasswordValid) ? <p className="login__validation">Please enter valid password</p> : ''}
                                    <Input type="password" name="password" value={ password } placeholder="Password" className="login__input" onChange={onChange} />
                                    <Button disabled={!(password && email)} className="login__btn" name="LOGIN" onClick={onClick} block />
                                </Form> 
                            }
                        </div>
                        <div className='col col-12  text-center'>
                            <div><img src={LoginFooterLogo} alt='logo' /></div>
                            <div className="login__footertext">imsec.ac.in</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default loginView;