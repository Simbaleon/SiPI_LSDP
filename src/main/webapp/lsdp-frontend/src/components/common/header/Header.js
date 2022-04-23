import s from "./Header.module.css"
import {NavLink} from "react-router-dom";

function Header() {
    return (
        <div className={s.header}>
            <div className={s.logo}>Insurance-Online</div>
            <div className={s.menu}>
                <NavLink to="/personalAccount">Личный кабинет</NavLink>
                <NavLink to="/orders">Доступные заказы</NavLink>
            </div>
        </div>
    )
}

export default Header;