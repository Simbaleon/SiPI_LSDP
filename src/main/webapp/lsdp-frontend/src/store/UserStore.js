import {makeAutoObservable} from "mobx";
import AuthService from "../services/AuthService";

export default class UserStore {

    userRole = {}
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

    registration(fullName, email, telephoneNumber, password) {
        AuthService.registration(fullName, email, telephoneNumber, password).then(response => {
            console.log(response)
        }).catch(() => {
            console.log("что-то сломалось при регистрации")
        })
    }

    logout() {
        localStorage.removeItem("accesstoken")
        localStorage.removeItem("refreshtoken")
        this.setAuth(false)
        this.setUser({})
    }

}