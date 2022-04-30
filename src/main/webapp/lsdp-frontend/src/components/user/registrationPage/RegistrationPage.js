import {useContext, useState} from "react";
import {Button, Grid, TextField} from "@mui/material";
import {Context} from "../../../index";
import LoginIcon from '@mui/icons-material/Login';
import AppRegistrationIcon from '@mui/icons-material/AppRegistration';
import {NavLink} from "react-router-dom";

function RegistrationPage() {
    const [fullName, setFullName] = useState("")
    const [email, setEmail] = useState("")
    const [telephoneNumber, setTelephoneNumber] = useState("")
    const [password, setPassword] = useState("")
    const {userStore} = useContext(Context)

    return (
        <Grid
            container
            rowSpacing={1}
            direction="column"
            alignItems={"center"}
            justifyContent={"center"}
            style={{minHeight: '50vh'}}
        >
            <Grid item xs={12} sm={6} md={3}>
                <TextField
                    helperText="Пожалуйста, введите фамилию и имя"
                    label="Фамилия и имя"
                    type={"text"}
                    value={fullName}
                    onChange={e => setFullName(e.target.value)}
                />
            </Grid>
            <Grid item xs={5}>
                <TextField
                    helperText="Пожалуйста, введите почту"
                    label="Эл. почта"
                    type={"text"}
                    value={email}
                    onChange={e => setEmail(e.target.value)}
                />
            </Grid>
            <Grid item xs={5}>
                <TextField
                    helperText="Пожалуйста, введите номер телефона"
                    label="Номер телефона"
                    type={"tel"}
                    value={telephoneNumber}
                    onChange={e => setTelephoneNumber(e.target.value)}
                />
            </Grid>
            <Grid item xs={5}>
                <TextField
                    helperText="Пожалуйста, введите пароль"
                    label="Пароль"
                    type={"password"}
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                />
            </Grid>
            <Grid item xs={12}>
                <Button
                    startIcon={<AppRegistrationIcon />}
                    variant={"contained"}
                    color={"warning"}
                    onClick={() => userStore?.registration(fullName, email, telephoneNumber, password)}
                >
                    Зарегистрироваться
                </Button>
            </Grid>
            <Grid item xs={5}>
                <Button
                    variant={"contained"}
                    color={"success"}
                    startIcon={<LoginIcon />}
                    {...{
                        to: "/signIn",
                        component: NavLink
                    }}
                >
                    Войти
                </Button>
            </Grid>
        </Grid>
    )
}

export default RegistrationPage;