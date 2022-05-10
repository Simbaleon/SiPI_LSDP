import {makeAutoObservable} from "mobx";
import AuthService from "../services/AuthService";
import UserService from "../services/UserService";


export default class UserStore {

    user = ""
    userRole = []
    isAuth = false

    constructor() {
        makeAutoObservable(this)
    }

    setUser(user) {
        this.user = user
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
                this.setUser(response.data.username)
                console.log(response.data.username)
                this.setUserRole(response.data.role)
                console.log(response.data.role)
                return Promise.resolve(response)
            }).catch(() => {
                return Promise.reject()
            })
    }

    registration(fullName, email, telephoneNumber, password, role) {
        return AuthService.registration(fullName, email, telephoneNumber, password, role)
            .then(response => {
                return Promise.resolve(response)
            })
            .catch((err) => {
                return Promise.reject()
            })
    }

    logout() {
        localStorage.removeItem("accesstoken")
        localStorage.removeItem("refreshtoken")
        this.setAuth(false)
        this.setUserRole([])
    }

    getUserInfo(username) {
        return UserService.getUserInfo(username)
            .then(r => Promise.resolve(r))
            .catch(() => Promise.reject())
    }

}