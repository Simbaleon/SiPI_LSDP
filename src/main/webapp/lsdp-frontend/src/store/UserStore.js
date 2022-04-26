import {makeAutoObservable} from "mobx";
import AuthService from "../services/AuthService";

export default class UserStore {

    user = {}
    isAuth = false

    constructor() {
        makeAutoObservable(this)
    }

    setAuth(bool) {
        this.isAuth = bool
    }

    setUser(user) {
        this.user = user
    }

    login(email, password) {
        try {
            const response = AuthService.login(email, password)
            console.log(response)
            localStorage.setItem("accesstoken", response.headers.AccessToken)
            localStorage.setItem("refreshtoken", response.headers.RefreshToken)
            this.setAuth(true)
            this.setUser(response.data)
        } catch (e) {
            console.log(e.response?.data?.message)
        }
    }

    registration(fullName, email, telephoneNumber, password) {
        try {
            const response = AuthService.registration(fullName, email, telephoneNumber, password)
            console.log(response)
        } catch (e) {
            console.log(e.response?.data?.message)
        }
    }

    logout() {
        localStorage.removeItem("accesstoken")
        localStorage.removeItem("refreshtoken")
        this.setAuth(false)
        this.setUser({})
    }

}