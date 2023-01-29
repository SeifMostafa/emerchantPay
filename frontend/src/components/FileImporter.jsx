import "../App.css";
import axios from "axios";

import React, { Component } from 'react';
import { Button } from "react-bootstrap";



class FileImporter extends Component {

  state = {
    file: null
  }
  handleOnChange = (e) => {
    this.setState({ file: e.target.files[0] });
  };
  handleOnSubmit = (e) => {

    e.preventDefault();
    const { expectedResultName, token } = this.props;

    if (this.state.file) {
      var formData = new FormData();
      formData.append("file", this.state.file);
      var urlTail = expectedResultName === 'admins' ? 'auth/' + expectedResultName : expectedResultName;
      axios.post('http://localhost:8090/' + urlTail, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          'Authorization': token
        },
      }).then(function (response) {
        console.log(response)
        if (response.data) {
          alert(expectedResultName + " imported successfully!")
        } else {
          alert(expectedResultName + " could not be imported!")
        }
      }).catch(function (error) {
        console.error(error)
      });
    }
  };
  render() {
    return (
      <div className="card">
        <form>
          <Button
            className="custom-btn"
            onClick={(e) => {
              this.handleOnSubmit(e);
            }}
          >
            IMPORT <span className="custom-span">{this.props.expectedResultName}</span>
          </Button>
          <input
            className="m-2 active"
            type={"file"}
            id={"csvFileInput"}
            accept={".csv"}
            onChange={this.handleOnChange}
          />
        </form>
      </div>
    );
  }
}
export default FileImporter