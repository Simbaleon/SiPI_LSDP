import {makeAutoObservable} from "mobx";
import AuthService from "../services/AuthService";
import SnackbarConstructor from "../components/common/snackbarConstructor/SnackbarConstructor";


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

    isAdmin() {
        return this.userRole.includes('ADMIN')
    }

    login(email, password) {
        return AuthService.login(email, password)
            .then((response) => {
                localStorage.setItem("accesstoken", response.headers.accesstoken)
                localStorage.setItem("refreshtoken", response.headers.refreshtoken)
                this.setAuth(true)
                this.setUserRole(response.data.role)
                return Promise.resolve(response)
            }).catch(() => {
                return Promise.reject()
            })
    }

    registration(fullName, email, telephoneNumber, password, role) {
        return AuthService.registration(fullName, email, telephoneNumber, password, role)
            .then(response => {
                // if (response.status === 201) {
                //     SnackbarConstructor("alertAfterRegistration", "success", "Успешная регистрация")
                // }
                return Promise.resolve(response)
            })
            .catch((err) => {
                return Promise.reject()
                // console.log(err)
                // SnackbarConstructor("alertAfterRegistration", "error", "Пользователь с таким email уже существует")
            })
    }

    logout() {
        localStorage.removeItem("accesstoken")
        localStorage.removeItem("refreshtoken")
        this.setAuth(false)
        this.setUserRole([])
    }

}