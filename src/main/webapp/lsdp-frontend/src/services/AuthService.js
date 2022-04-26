import $api from "../http/AxiosConfig";

export default class AuthService {
    static login(email, password) {
        return $api.post("/login", {email, password})
    }

    static registration(fullName, email, telephoneNumber, password) {
        return $api.post("/registration", {fullName, email, telephoneNumber, password})
    }
}