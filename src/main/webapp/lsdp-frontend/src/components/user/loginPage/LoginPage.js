import {useContext, useState} from "react";
import {Button, Input} from "@mui/material";
import {Context} from "../../../index";

function LoginPage() {
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")
    const {userStore} = useContext(Context)

    return (
        <div>
            <Input
                type={"text"}
                placeholder={"Эл.почта"}
                value={email}
                onChange={e => setEmail(e.target.value)}
            />
            <Input
                placeholder={"Пароль"}
                type={"password"}
                value={password}
                onChange={e => setPassword(e.target.value)}
            />
            <Button
                variant={"outlined"}
                color={"inherit"}
                onClick={() => userStore?.login(email, password)}
            >
                Войти
            </Button>
            <Button variant={"outlined"} color={"inherit"}>Зарегистрироваться</Button>
        </div>
    )
}

export default LoginPage;