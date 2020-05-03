import React, { Component } from 'react'
import ApiService from "../../service/ApiService";

class AddUserComponent extends Component{

    constructor(props){
        super(props);
        this.state ={
            username: '',
            password: '',
			confirmPassword: '',
            firstName: '',
            lastName: '',
            age: '',
			dateOfBirth: '',
			gender: '',
			email: '',
			mobileNo: '',
            message: null
        }
        this.saveUser = this.saveUser.bind(this);
    }

    saveUser = (e) => {
        e.preventDefault();
        let user = {username: this.state.username, password: this.state.password, confirmPassword: this.state.confirmPassword, firstName: this.state.firstName, lastName: this.state.lastName, age: this.state.age, dateOfBirth: this.state.dateOfBirth, gender: this.state.gender, email: this.state.email, mobileNo: this.state.mobileNo};
        ApiService.addUser(user)
            .then(res => {
                this.setState({message : 'User added successfully.'});
                this.props.history.push('/users');
            });
    }

    onChange = (e) =>
        this.setState({ [e.target.name]: e.target.value });

    render() {
        return(
            <div>
                <h2 className="text-center">Register User</h2>
                <form>
                <div className="form-group">
                    <label>User Name:</label>
                    <input type="text" placeholder="username" name="username" className="form-control" value={this.state.username} onChange={this.onChange}/>
                </div>
				
				<div className="form-group">
                    <label>Email:</label>
                    <input type="text" placeholder="email" name="email" className="form-control" value={this.state.email} onChange={this.onChange}/>
                </div>

                <div className="form-group">
                    <label>Password:</label>
                    <input type="password" placeholder="password" name="password" className="form-control" value={this.state.password} onChange={this.onChange}/>
                </div>
				
				<div className="form-group">
                    <label>Confirm Password:</label>
                    <input type="password" placeholder="confirmPassword" name="confirmPassword" className="form-control" value={this.state.confirmPassword} onChange={this.onChange}/>
                </div>

                <div className="form-group">
                    <label>First Name:</label>
                    <input type ="text" placeholder="First Name" name="firstName" className="form-control" value={this.state.firstName} onChange={this.onChange}/>
                </div>

                <div className="form-group">
                    <label>Last Name:</label>
                    <input type="text" placeholder="Last name" name="lastName" className="form-control" value={this.state.lastName} onChange={this.onChange}/>
                </div>
				
				<div className="form-group">
                    <label>Date Of Birth:</label>
                    <input type="date" placeholder="dateOfBirth" name="dateOfBirth" className="form-control" value={this.state.dateOfBirth} onChange={this.onChange}/>
                </div>

                <div className="form-group">
                    <label>Age:</label>
                    <input type="number" placeholder="age" name="age" className="form-control" value={this.state.age} onChange={this.onChange}/>
                </div>

                <div className="form-group">
                    <label>Gender:</label>
                    <input type="text" placeholder="gender" name="gender" className="form-control" value={this.state.gender} onChange={this.onChange}/>
                </div>
				
				<div className="form-group">
                    <label>Mobile Number:</label>
                    <input type="text" placeholder="mobileNo" name="mobileNo" className="form-control" value={this.state.mobileNo} onChange={this.onChange}/>
                </div>

                <button className="btn btn-success" onClick={this.saveUser}>Save</button>
            </form>
    </div>
        );
    }
}

export default AddUserComponent;