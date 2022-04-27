import s from "./Content.module.css";
import {Route, Routes,} from "react-router-dom";
import AllOrders from "../../orders/Orders";
import PersonalAccount from "../../user/personalAccount/PersonalAccount";
import LoginPage from "../../user/loginPage/LoginPage";
import RegistrationPage from "../../user/registrationPage/RegistrationPage";

function Content() {
    return (
        <div className={s.content}>
            <Routes>
                <Route path="/signin" element={<LoginPage/>}/>
                <Route path="/orders" element={<AllOrders/>}/>
                <Route path="/personalAccount" element={<PersonalAccount/>}/>
                <Route path="/signUp" element={<RegistrationPage/>}/>
            </Routes>
        </div>
    )
}

export default Content;