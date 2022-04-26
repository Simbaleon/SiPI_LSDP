import {AppBar, Button, Toolbar, Typography} from "@mui/material";
import {makeStyles} from "@mui/styles";
import {NavLink} from "react-router-dom";

const useStyles = makeStyles(() => ({
    header: {
        backgroundColor: "#1c1427",
        paddingRight: "79px",
        paddingLeft: "118px",
    },
    logo: {
        fontFamily: "Suez One, serif",
        fontWeight: 600,
        color: "white",
        textAlign: "left",
    },
    menuButton: {
        fontFamily: "Open Sans, sans-serif",
        fontWeight: 700,
        size: "18px",
        marginLeft: "38px",
    },
    toolbar: {
        display: "flex",
        justifyContent: "space-between",
    }
}));

const headersData = [
    {
        label: "Войти",
        href: "/signin",
    },
    {
        label: "Зарегистрироваться",
        href: "/signup",
    }
];

const getMenuButtons = (menuButton) => {

    return headersData.map(({ label, href }) => {
        return (
            <Button
                {...{
                    key: label,
                    color: "inherit",
                    to: href,
                    component: NavLink,
                    className: menuButton
                }}
            >
                {label}
            </Button>
        );
    });
};

function Header() {
    const { header, logo, menuButton, toolbar } = useStyles()

    return (
        // <div className={s.header}>
        //     <div className={s.logo}>Freelance-platform</div>
        //     <div className={s.menu}>
        //         <NavLink to="/personalAccount">Личный кабинет</NavLink>
        //         <NavLink to="/orders">Доступные заказы</NavLink>
        //     </div>
        // </div>
        <header>
            <AppBar position={"static"} className={header}>
                <Toolbar className={toolbar}>
                    <Typography variant="h6" component="h1" className={logo}>
                        Freelance-platform
                    </Typography>
                    <div>{getMenuButtons(menuButton)}</div>
                </Toolbar>
            </AppBar>
        </header>
    )
}

export default Header;