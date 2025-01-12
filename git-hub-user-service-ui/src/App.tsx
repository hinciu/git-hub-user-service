import React from "react";
import './App.css';
import {BrowserRouter as Router, Redirect, Route, Switch} from "react-router-dom";
import MainPage from "./components/MainPage";
import LoginPage from "./components/LoginPage";
import {LOGIN_PAGE, MAIN_PAGE} from "./constants/path";

const App = () => {
  return (
    <Router>
        <Switch>
            <Redirect exact from="/" to={MAIN_PAGE}/>
            <Route path={LOGIN_PAGE}>
                <LoginPage/>
            </Route>
            <Route path={MAIN_PAGE}>
                <MainPage/>
            </Route>
        </Switch>
    </Router>
  );
}

export default App;
