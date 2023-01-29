import './App.css';
import { ButtonGroup, Button } from 'react-bootstrap';

import FileImporter from './components/FileImporter';
import React, { Component } from 'react';
import axios from 'axios'
import Merchant from './components/Merchant'
import Transaction from './components/Transaction'
class App extends Component {



  state = {
    showTransactions: false,
    showMerchants: false,
    token: "",
    transactions: null,
    merchants: null
  };


  handleShowTransactions = (e) => {
    e.preventDefault();
    let transactions = axios.get('http://localhost:8090/transactions')
      .then(function (response) {
        return response;
      }).catch(function (error) {
        alert("could not fetch transactions!");
        console.log(error);
      });
    transactions.then((value) => {
      this.setState(() => ({ transactions: value.data }))
      this.setState(() => ({ showTransactions: true }))
    });
  };
  handleShowMerchants = (e) => {
    e.preventDefault();
    let merchants = axios.get('http://localhost:8090/merchants')
      .then(function (response) {
        return response;
      }).catch(function (error) {
        alert("could not fetch merchants!");
        console.log(error);
      });
    merchants.then((value) => {
      this.setState(() => ({ merchants: value.data }))
      this.setState(() => ({ showMerchants: true }))

    });

  };
  componentDidMount() {
    async () => {
      const response = await axios.post('http://localhost:8090/auth/login',
        { email: 'csseifms@gmail.com', password: 'post1234' })
        .then(function (response) {
          console.log("superUser is logged-in successfully! " + response.data)
        }).catch(function (error) {
          alert("superUser could not login, Please turn on the backend server!" + error);
        });
      if (response.data != null) {
        this.setToken(response.data)
        console.log('loggedIn')
      } else {
        alert("superUser could not login, Please turn on the backend server!");
      }
    }
  }
  render() {
    const { showTransactions, showMerchants } = this.state;
    return (
      <div>
        <header className="card">
          <img className="App-logo" alt="emerchantpay logo" src="logo.png"></img>
        </header>
        <div className="center">
          <ButtonGroup aria-label="page">
            <FileImporter expectedResultName="admins" token={this.state.token} className="custom-btn"></FileImporter>
            <FileImporter expectedResultName="merchants" token={this.state.token} className="custom-btn"></FileImporter>
            <Button type="button" onClick={this.handleShowTransactions} className="custom-btn" >Display Transactions</Button>
            <Button type="button" onClick={this.handleShowMerchants} className="custom-btn" >Display Merchants</Button>
          </ButtonGroup>
        </div>
        <div>
          <ul className="nav nav-pills nav-fill mt-2 mb-5">
            <li className="nav-item">
              {showTransactions && this.state.transactions != null ? this.state.transactions.map((transaction) => (
                <div key={transaction.uuid}>
                  <Transaction transaction={transaction} />
                </div>
              ))
                : null}
            </li>
            <li className="nav-item">
              {showMerchants && this.state.merchants != null ? this.state.merchants.map((merchant) => (
                <div key={merchant.id}>
                  <Merchant merchant={merchant} token={this.state.token} />
                </div>
              ))
                : null}
            </li>
          </ul>
        </div>
      </div>
    );
  }
}
export default App