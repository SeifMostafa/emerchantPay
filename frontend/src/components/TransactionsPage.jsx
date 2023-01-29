import React, { Component } from 'react';
import axios from 'axios'
import { NavLink } from "react-router-dom";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";

class TransactionsPage extends Component {
    state = {
        transactions: null
    }
    componentDidMount() {
        axios.get('http://localhost:8090/transactions')
            .then(function (response) {
                console.log("Transactions: " + response.data)
            }).catch(function (error) {
                alert("could not fetch transactions!");
            });
    }
    render() {
        return (
            <h1>Transactions</h1>
        )
    }

}
export default connect()(TransactionsPage);
