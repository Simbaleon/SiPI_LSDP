import {makeAutoObservable} from "mobx";
import AuthService from "../services/AuthService";
import {createRoot} from "react-dom/client";
import {Alert, Snackbar} from "@mui/material";


export default class UserStore {

    userRole = []
    isAuth = false

    constructor() {
        makeAutoObservable(this)
    }

    setAuth(bool) {
        this.isAuth = bool
    }

    setUserRole(userRole) {
        this.userRole = userRole
    }

    isUser() {
        return this.userRole.includes('USER')
    }

    login(email, password) {
        return AuthService.login(email, password)
            .then((response) => {
                localStorage.setItem("accesstoken", response.headers.accesstoken)
                localStorage.setItem("refreshtoken", response.headers.refreshtoken)
                this.setAuth(true)
                this.setUserRole(response.data.role)
                console.log(this.userRole.includes('USER'))
                return Promise.resolve(response)
            }).catch(() => {
                return Promise.reject()
            })
    }

    registration(fullName, email, telephoneNumber, password, role) {
        const alertContainer = document.getElementById('alertAfterRegistration');
        const root = createRoot(alertContainer);
        AuthService.registration(fullName, email, telephoneNumber, password, role).then(response => {
            if (response.status === 201) {
                root.render(
                    <Snackbar open={true} autoHideDuration={6000}>
                        <Alert severity="success" sx={{ width: '100%' }} >
                            Успешная регистрация.
                        </Alert>
                    </Snackbar>
                );
            }
        }).catch(() => {
            root.render(
                <Snackbar open={true} autoHideDuration={6000} >
                    <Alert severity="error" sx={{ width: '100%' }} >
                        Пользователь с таким email уже существует.
                    </Alert>
                </Snackbar>
            );
        })
    }

    logout() {
        localStorage.removeItem("accesstoken")
        localStorage.removeItem("refreshtoken")
        this.setAuth(false)
        this.setUser({})
    }

}