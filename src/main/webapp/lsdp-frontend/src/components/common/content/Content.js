import s from "./Content.module.css";
import {Route, Routes,} from "react-router-dom";
import AllOrders from "../../orders/AllOrders";
import PersonalAccount from "../../user/personalAccount/PersonalAccount";
import LoginPage from "../../user/loginPage/LoginPage";
import RegistrationPage from "../../user/registrationPage/RegistrationPage";
import CreateOrderPage from "../../orders/CreateOrderPage";
import OrderManagementPage from "../../orders/OrderManagementPage";
import HomePage from "./HomePage";

function Content() {
    return (
        <div className={s.content}>
            <Routes>
                <Route path="/" element={<HomePage/>}/>
                <Route path="/signin" element={<LoginPage/>}/>
                <Route path="/orders" element={<AllOrders/>}/>
                <Route path="/personalAccount" element={<PersonalAccount/>}/>
                <Route path="/signUp" element={<RegistrationPage/>}/>
                <Route path="/orders/create" element={<CreateOrderPage/>}/>
                <Route path="/orders/manageOrder" element={<OrderManagementPage/>}/>
            </Routes>
        </div>
    )
}

export default Content;