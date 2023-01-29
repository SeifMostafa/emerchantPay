import React, { Component } from "react";
import { ButtonGroup, Button } from 'react-bootstrap';
import axios from 'axios'

class Merchant extends Component {
    state = {
        enableEditing: false
    };
    delete = (e) => {
        e.preventDefault();
        const { merchant, token } = this.props;
        axios.delete('http://localhost:8090/merchant', {
            params: {
                'id': merchant.id,
            },
            headers: {
                'Content-Type': 'application/json',
                'Authorization': token
            },
        }).then(function (response) {
            console.log(response)
            if (response.data) {
                alert("merchant is deleted successfully!")
                window.location.reload(false);
            } else {
                alert(" could not delete!")
            }
        }).catch(function (error) {
            console.error(error)
        });
    }
    edit = (e) => {
        e.preventDefault();
        const { merchant } = this.props;
        this.setState(() => ({ enableEditing: true }))
    }
    submitEdit = (e) => {
        e.preventDefault();

        const { merchant, token } = this.props;
        axios.post('http://localhost:8090/merchant',
            JSON.stringify({

                id: merchant.id,
                name: merchant.name,
                description: merchant.description,
                email: merchant.email,
                active: merchant.active,
                total_transaction_sum: merchant.total_transaction_sum,
            })).then(function (response) {
                console.log(response)
                if (response.data) {
                    alert("merchant is updated successfully!")
                    window.location.reload(false);
                } else {
                    alert(" could not edit!")
                }
            }).catch(function (error) {
                console.error(error)
            });
        this.setState(() => ({ enableEditing: false }))
    }
    handleNewStatus = (e) => {
        e.preventDefault();
        const { merchant } = this.props;
        if (merchant.status != null) {
            merchant.status = e.target.value === 1 ? true : false;
        }
    }
    handleNewSum = (e) => {
        e.preventDefault();
        const { merchant } = this.props;
        merchant.total_transaction_sum = e.target.value != null ? e.target.value : merchant.total_transaction_sum;
    }
    handleNewEmail = (e) => {
        e.preventDefault();
        const { merchant } = this.props;
        merchant.email = e.target.value != null ? e.target.value : merchant.email;
    }
    handleNewDescription = (e) => {
        e.preventDefault();
        const { merchant } = this.props;
        merchant.description = e.target.value != null ? e.target.value : merchant.description;
    }
    handleNewName = (e) => {
        e.preventDefault();
        const { merchant } = this.props;
        merchant.name = e.target.value != null ? e.target.value : merchant.name;
    }
    render() {
        const { merchant } = this.props;
        return (
            <div className="card">
                <h5 className="mt-4 mb-2">Merchant: {merchant.name} with id: {merchant.id}</h5>
                <div className="media">
                    <div className="card-content">
                        <span> Description: {merchant.description}, </span>
                        <span> Email: {merchant.email}, </span>
                        <span> Status:
                            {merchant.active ? <span className="text-success"> Active, </span>
                                : <span className="text-danger"> Not Active, </span>}
                        </span>
                        <span className="h5 active underline">Total Transactions Sum: {merchant.total_transaction_sum}</span>
                    </div>
                </div>
                <ButtonGroup aria-label="page">
                    <Button type="button" onClick={this.delete} className="btn-secondary custom-btn-small" >Delete</Button>
                    <Button type="button" onClick={this.edit} className="btn-secondary custom-btn-small" >Edit</Button>
                </ButtonGroup>
                {this.state.enableEditing ?
                    <div>
                        <div className="App-header">
                            <form>
                                <input
                                    type="text"
                                    className="form-control"
                                    placeholder="Enter new name"
                                    name="newName"
                                    onChange={this.handleNewName}
                                />
                                <input
                                    type="text"
                                    className="form-control"
                                    placeholder="Enter new description"
                                    name="newDescription"
                                    onChange={this.handleNewDescription}
                                />
                                <input
                                    type="text"
                                    className="form-control"
                                    placeholder="Enter new email"
                                    name="newEmail"
                                    onChange={this.handleNewEmail}
                                />
                                <input
                                    type="number"
                                    className="form-control"
                                    placeholder="Enter new transaction sum"
                                    name="newSum"
                                    onChange={this.handleNewSum}
                                />
                                <input
                                    type="number"
                                    className="form-control"
                                    placeholder="Enter 1 for active or 0 for not active"
                                    name="newStatus"
                                    onChange={this.handleNewStatus}
                                />
                                <Button className="custom-btn" onClick={this.submitEdit} >submit
                                </Button>

                            </form>
                        </div>
                    </div>
                    : null}
            </div>
        );
    }

}
export default Merchant