import s from "./Content.module.css";
import {Route, Routes} from "react-router-dom";
import AllOrders from "../../orders/Orders";
import PersonalAccount from "../../personalAccount/PersonalAccount";

function Content() {
    return (
        <div className={s.content}>
            <Routes>
                <Route path="/orders" element={<AllOrders/>}/>
                <Route path="/personalAccount" element={<PersonalAccount/>}/>
            </Routes>
        </div>
    )
}

export default Content;