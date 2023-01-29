import "../App.css";
import axios from "axios";

import React, { Component } from 'react';
import { Button } from "react-bootstrap";
import Alert from 'react-bootstrap/Alert';
import Popup from 'reactjs-popup';


class FileImporter extends Component {

  state = {
    file: null
  }
  handleOnChange = (e) => {
    this.setState({ file: e.target.files[0] });
  };
  handleOnSubmit = (e) => {

    e.preventDefault();
    const { expectedResultName } = this.props;

    if (this.state.file) {
      var formData = new FormData();
      formData.append("file", this.state.file);
      axios.post('http://localhost:8090/auth/' + expectedResultName, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        },
      }).then(function (response) {
        console.log(response)
        if (response.data) {

        } else {

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