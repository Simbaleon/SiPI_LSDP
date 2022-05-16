import {makeAutoObservable} from "mobx";
import AuthService from "../services/AuthService";
import UserService from "../services/UserService";
import SnackbarConstructor from "../components/common/snackbarConstructor/SnackbarConstructor";


export default class UserStore {

    user = ""
    userRole = []
    isAuth = false
    userResponses = []

    constructor() {
        makeAutoObservable(this)
    }

    setUserResponses(responses) {
        this.userResponses = responses
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

    registration(fullName, email, telephoneNumber, password) {
        return AuthService.registration(fullName, email, telephoneNumber, password)
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

    changeUserDescription(username, description) {
        return UserService.changeUserDescription(username, description)
            .then(r => Promise.resolve(r))
            .catch(() => Promise.reject())
    }

    getUserOrderResponses() {
        return UserService.getUserOrderResponses(this.user)
            .then(r => {
                this.setUserResponses(r.data)
                console.log(this.userResponses)
                return Promise.resolve(r)
            })
            .catch(() => Promise.reject())
    }

}