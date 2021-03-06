import React, {createContext} from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {BrowserRouter} from "react-router-dom";
import UserStore from "./store/UserStore";
import OrderStore from "./store/OrderStore";


const userStore = new UserStore()

const orderStore = new OrderStore()

export const Context = createContext({userStore , orderStore})

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
        <Context.Provider value={{userStore: userStore, orderStore: orderStore}}>
            <BrowserRouter>
                <App/>
            </BrowserRouter>
        </Context.Provider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
