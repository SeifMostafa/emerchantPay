import './App.css';
import 'bootstrap/dist/css/bootstrap.css';
import {ButtonGroup,Button} from 'react-bootstrap';
import React, { Component } from 'react';


class App extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div>
        <header class="card">
          <img src="logo.png"></img>
        </header>
        <body class="center">
          <ButtonGroup size="lg"  aria-label="Basic example">
            <Button type="button" className="custom-btn" >Display Merchants</Button>
            <Button type="button" className="custom-btn" >Display Transactions</Button>
          </ButtonGroup>
        </body>
      </div>
    );
  }
  
}

export default App;
