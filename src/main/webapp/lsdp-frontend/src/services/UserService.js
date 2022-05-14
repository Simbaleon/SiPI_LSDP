import $api from "../http/AxiosConfig";

export default class UserService {

    static getUserInfo(email) {
        return $api.get("/clients/getUserByUsername", {params: {username: email}})
    }

    static changeUserDescription(username, description) {
        return $api.patch("/clients/changeUserDescription", {username, description})
    }

    static getUserOrderResponses(email) {
        return $api.get("/clients/getUserOrderResponses", {params: {username: email}})
    }

}