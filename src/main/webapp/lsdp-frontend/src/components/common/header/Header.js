import {AppBar, Button, Toolbar, Typography} from "@mui/material";
import {makeStyles} from "@mui/styles";
import {useContext} from "react";
import {Context} from "../../../index";
import {useNavigate} from "react-router";
import {observer} from "mobx-react-lite";

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

const Header = observer(() => {
    const {header, logo, menuButton, toolbar} = useStyles()
    const {userStore} = useContext(Context)
    const navigate = useNavigate()

    console.log("header render")

    return (
        <header>
            <AppBar position={"static"} className={header}>
                <Toolbar className={toolbar}>
                    <Typography variant="h6" component="h1" className={logo}>
                        Freelance-platform
                    </Typography>
                    {userStore.isUser() ?
                        <div>
                            <Button
                                className={menuButton}
                                color={"inherit"}
                                variant={"outlined"}
                                onClick={() => navigate("/personalAccount")}>
                                Профиль
                            </Button>
                        </div>
                        : null
                    }
                    {userStore.isAdmin() ?
                        <div>
                            <Button
                                className={menuButton}
                                color={"inherit"}
                                variant={"outlined"}
                                onClick={() => navigate("/adminPage")}>
                                Страница администратора
                            </Button>
                        </div> : null
                    }
                    {userStore.isAuth ?
                        <div>
                            <Button
                                className={menuButton}
                                color={"inherit"}
                                variant={"outlined"}
                                onClick={() => navigate("/orders")}>
                                Заказы
                            </Button>
                        </div>
                        : null
                    }
                    {userStore.isAuth ?
                        <div>
                            <Button
                                className={menuButton}
                                color={"inherit"}
                                variant={"outlined"}
                                onClick={() => {
                                    userStore.logout()
                                    navigate("/")
                                }}>
                                Выйти
                            </Button>
                        </div>
                        :
                        <div>
                            <Button
                                className={menuButton}
                                color={"inherit"}
                                variant={"outlined"}
                                onClick={() => navigate("/signin")}>
                                Войти
                            </Button>
                            <Button
                                className={menuButton}
                                color={"inherit"}
                                variant={"outlined"}
                                onClick={() => navigate("/signup")}>
                                Зарегистрироваться
                            </Button>
                        </div>
                    }
                </Toolbar>
            </AppBar>
        </header>
    )
})

export default Header;