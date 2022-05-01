import {AppBar, Button, Toolbar, Typography} from "@mui/material";
import {makeStyles} from "@mui/styles";
import {NavLink} from "react-router-dom";
import {useContext} from "react";
import {Context} from "../../../index";

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

const authButtonData = [
    {
        label: "Войти",
        href: "/signin",
    },
    {
        label: "Зарегистрироваться",
        href: "/signup",
    }
];

const isAuthButtonForUser = [
    {
        label: "Профиль",
        href: "/personalAccount"
    },
    {
        label: "Выйти",
        href: "/logout"
    }
]

const getMenuButtons = (menuButton, headersData) => {

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
    const {userStore} = useContext(Context)
    return (
        <header>
            <AppBar position={"static"} className={header}>
                <Toolbar className={toolbar}>
                    <Typography variant="h6" component="h1" className={logo}>
                        Freelance-platform
                    </Typography>
                    { userStore.isUser() === true ?
                        (<div>
                            {getMenuButtons(menuButton, isAuthButtonForUser)}
                        </div>) : null
                    }
                    { userStore.isAuth === true ?
                        null : (<div>
                            {getMenuButtons(menuButton, authButtonData)}
                        </div>)
                    }
                </Toolbar>
            </AppBar>
        </header>
    )
}

export default Header;