import React, { Component } from "react";

import Modal from "../../components/UI/Modal/Modal";
import Aux from "../Aux1/aux1";

const withErrorHandler = (WrappedComponent, axios) => {
  return class extends Component {

    constructor(props){
      super(props);
      this.state = {
        error: null,
      }
      this.reqinterceptors = axios.interceptors.request.use((req) => {
        this.setState({ error: null });
        return req;
      });

      this.respinterceptors = axios.interceptors.response.use(
        (res) => res,
        (error) => {
          this.setState({ error: error });
        }
      );
    }
   


    // static getDeivedStateFromProps(props,state) {
      
    // }

  //   componentWillMount () {
  //     this.reqInterceptor = axios.interceptors.request.use( req => {
  //         this.setState( { error: null } );
  //         return req;
  //     } );
  //     this.resInterceptor = axios.interceptors.response.use( res => res, error => {
  //         this.setState( { error: error } );
  //     } );
  // }

    componentWillUnmount() {
      axios.interceptors.request.eject(this.reqinterceptors);
      axios.interceptors.response.eject(this.respinterceptors);
    }

    errorConfirmedHandler = () => {
      this.setState({ error: null });
    };

    render() {
      return (
        <Aux>
          <Modal
            show={this.state.error}
            modalClosed={this.errorConfirmedHandler}
          >
            {this.state.error ? this.state.error.message : null}
          </Modal>
          <WrappedComponent {...this.props} />
        </Aux>
      );
    }
  };
};

export default withErrorHandler;
