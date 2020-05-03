import React from 'react';
import Header from '../header/header';
import Sidebar from '../sidebar/sidebar';
import { Redirect } from 'react-router-dom';
import { isEmpty } from 'lodash';

const checkUserLogin = (WrapperComponent) =>{
    return class WrapperData extends React.Component{
        constructor(props){
            super(props)
            this.state = {
                redirect: false,
                redirectPath: ''
            }
        }
        componentDidMount() {
            if( this.userNotLogin() && this.props.match.path !== '/' ) {
                this.goToPage( '/' );
            } else if( ! this.userNotLogin() && this.props.match.path === '/' ) {
                this.goToPage( '/dashboard' );
            }
        }

        userNotLogin = () => {
            const _token = window.localStorage.getItem( '_TOKEN' );
            return isEmpty( _token );
        }

        render(){
            const { redirect, redirectPath } = this.state;

            if( redirect ) {
                return( <Redirect to={ redirectPath } /> );
            } else{
                const { path } = this.props.match;
                if( path === '/' || path === '/not-found' ) {
                    return(
                        <WrapperComponent
                            { ...this.props }
                            goToPage={ this.goToPage }
                        />
                    )
                } else{
                    return(
                        <>
                            <Header logOut={ this.onLogout } />
                            <Sidebar />
                            <div className="content">
                                <WrapperComponent 
                                    { ...this.props }
                                    goToPage={ this.goToPage }
                                />
                            </div>
                        </>
                    )
                }
            }
        }

        onLogout = () => {
            window.localStorage.removeItem( '_TOKEN' );
            window.localStorage.removeItem( 'email' );
            this.goToPage( '/' );
        }

        goToPage = ( path ) => {
            this.setState( { 
                redirect: true,
                redirectPath: path
            } );
        }
    }
}

export default checkUserLogin;