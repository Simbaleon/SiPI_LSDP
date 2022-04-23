import {authEndpoints} from "../config/BackendConfig";

class AuthenticationService {
    login(username, password) {
        return authEndpoints.login(username, password)
            .catch(function (error) {
                if (error.response.status === 401) {
                    console.log(error.response)
                }
            })
            .then(response => {
                console.log([
                    response.data,
                    response.headers,
                    response.headers.AccessToken,
                    response.headers.RefreshToken
                ])
                localStorage.setItem("user", JSON.stringify(response.data))
                localStorage.setItem("accesstoken", response.headers.AccessToken)
                localStorage.setItem("refreshtoken", response.headers.RefreshToken)
                return response.data
            })
    }

    logout() {
        localStorage.removeItem("user")
        localStorage.removeItem("accesstoken")
        localStorage.removeItem("refreshtoken")
    }

    registration(fullName, email, telephoneNumber, password) {
        return authEndpoints.registration(fullName, email, telephoneNumber, password)
    }
}

export default new AuthenticationService();