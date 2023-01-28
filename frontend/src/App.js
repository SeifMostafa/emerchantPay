import './App.css';
import {ButtonGroup,Button} from 'react-bootstrap';

import FileImporter from './components/FileImporter';
import React, {Component} from 'react';
import axios from 'axios'
import Alert from 'react-bootstrap/Alert';

class App extends Component{
  componentDidMount() {
    axios.post('http://localhost:8090/auth/login', 
    { email: 'csseifms@gmail.com', password: 'post1234'})
    .then(function(response) {
  console.log("superUser is logged-in successfully!")
}).catch(function(error) {
  Alert.error("superUser could not login, Please turn on the backend server!");
});
}
  render(){
    return (
      <div>
        <header className="card">
          <img className="App-logo" alt="emerchantpay logo" src="logo.png"></img>
        </header>
        <body className="center">
          <ButtonGroup size="lg"  aria-label="Basic example">
            <Button type="button" className="custom-btn" >Display Merchants</Button>
            <Button type="button" className="custom-btn" >Display Transactions</Button>
          
            <FileImporter expectedResultName="admins" className="custom-btn"></FileImporter>
            <FileImporter expectedResultName="merchants" className="custom-btn"></FileImporter>
          </ButtonGroup>
        </body>
      </div>
    ); 
  } 
}
export default App