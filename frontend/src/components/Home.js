import React, { Component } from 'react';

class Home extends Component {
    render() {
        return (
            <div>
                <header className="card">
                    <img className="App-logo" alt="emerchantpay logo" src="logo.png"></img>
                </header>
                <body className="center">
                    <ButtonGroup size="lg" aria-label="Basic example">
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
export default Home