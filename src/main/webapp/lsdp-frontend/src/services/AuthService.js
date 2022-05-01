import $api from "../http/AxiosConfig";

export default class AuthService {

    static login(email, password) {
        return $api.post("/login", {email, password})
    }

    static registration(fullName, email, telephoneNumber, password, role) {
        return $api.post("/clients/registration", {fullName, email, telephoneNumber, password, role})
    }

}