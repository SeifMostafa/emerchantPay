import '../App.css';
import { Button, ButtonGroup } from 'react-bootstrap';

import FileImporter from './FileImporter';
import React, { Component } from 'react';
import axios from 'axios'
import { Link, Redirect } from "react-router-dom";
import { NavLink } from "react-router-dom";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";

class Home extends Component {
    handleRedirectToTransactions = (e) => {
        e.preventDefault();

    }
    handleRedirectToMerchants = (e) => {
        e.preventDefault();

    }
    render() {
        return (
            <div>
                <header className="card">
                    <img className="App-logo" alt="emerchantpay logo" src="logo.png"></img>
                </header>
                <div className="center">
                    <ButtonGroup size="lg" aria-label="Basic example">
                        <Link type="button" to="/merchantsPage" onClick={(e) => {
                            this.handleRedirectToMerchants(e);
                        }} className="custom-btn" >Display Merchants</Link>
                        <Link type="button" to="/transactionsPage" onClick={(e) => {
                            this.handleRedirectToTransactions(e);
                        }} className="custom-btn" >Display Transactions</Link>

                        <FileImporter expectedResultName="admins" className="custom-btn"></FileImporter>
                        <FileImporter expectedResultName="merchants" className="custom-btn"></FileImporter>
                    </ButtonGroup>
                </div>
            </div>
        );
    }
}
export default connect(mapStateToProps)(Home);
