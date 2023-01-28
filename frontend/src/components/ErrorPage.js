// ref: https://www.newline.co/fullstack-react/react-daily-ui/008-404-page/
import React from "react";
import { useNavigate } from 'react-router-dom';

const ErrorPage = () => {
  const navigate = useNavigate();

  return (
    <div className="row justify-content-center">
      <div className="col-md-12 col-sm-12">
        <div className="card shadow-lg border-0 rounded-lg mt-5 mx-auto">
          <h3 className="card-header display-1 text-muted text-center">404</h3>

          <span className="card-subtitle mb-2 text-muted text-center">
            Could not import the data!
          </span>

          <div className="card-body mx-auto">
            <a
              type="button"
              href="/"
              className="btn btn-sm btn-info text-white"
            >
              {" "}
              Back To Home{" "}
            </a>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ErrorPage;
