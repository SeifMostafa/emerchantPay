import React, { Component } from "react";

class Transction extends Component {
    render() {
        const { transaction } = this.props;
        return (
            <div className="card">
                <h5 className="mt-4 mb-2 text-success">Transaction with UUID: {transaction.uuid}</h5>
                <div className="media">

                    <div className="card-content">
                        <span>has status: {transaction.status}, </span>
                        <span> and Type: {transaction.type}, </span>
                        <span className="h5"> and amount: {transaction.amount}, </span>
                        <div className="card-content">
                            <span>is submitted by customer with the following data:</span>
                            <span> phone: </span>
                            <span>{transaction.customer_phone},</span>
                            <span> email: </span>
                            <span>{transaction.customer_email}</span>
                        </div>
                        <span className="active"> and belong to merchant: {transaction.merchant.name}</span>
                        <div className="mt-2 text-secondary">is created on {transaction.creationTimestamp}</div>
                    </div>
                </div>
            </div>
        );
    }
}
export default Transction