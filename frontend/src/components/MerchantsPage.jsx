import React, { Component } from 'react';
import axios from 'axios'
import { NavLink } from "react-router-dom";
import { connect } from "react-redux";
import { withRouter } from "react-router-dom";

class MerchantsPage extends Component {
    state = {
        merchants: null
    }
    componentDidMount() {
        console.log('componentDidMount')
        axios.get('http://localhost:8090/merchants')
            .then(function (response) {
                console.log("Merchants: " + response)
            }).catch(function (error) {
                alert("could not fetch merchants!");
            });
    }
    render() {
        return (
            <div>
                <h1>Merchants</h1>
            </div>
        )
    }
}
export default withRouter(connect()(MerchantsPage));
