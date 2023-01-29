import React, { Component, Fragment } from "react";
import { connect } from "react-redux";
import { BrowserRouter as Router} from "react-router-dom";
import Navigationbar from "./Navigationbar"
import "../index.css";
import "bootstrap/dist/css/bootstrap.css";

class App extends Component {
  componentDidMount() {
    axios.post('http://localhost:8090/auth/login',
    { email: 'csseifms@gmail.com', password: 'post1234' })
    .then(function (response) {
        console.log("superUser is logged-in successfully!")
    }).catch(function (error) {
        alert("superUser could not login, Please turn on the backend server!");
    });
  }
  render() {
    return (
      <div>
        <Router>
          <Fragment>
            <div>
              <Navigationbar />
            </div>
          </Fragment>
        </Router>
      </div>
    );
  }
}

function mapStateToProps(loggedUser) {
  return {
    loggedUser,
  };
}

export default connect(mapStateToProps)(App);
