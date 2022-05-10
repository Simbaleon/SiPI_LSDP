import $api from "../http/AxiosConfig";

export default class UserService {

    static getUserInfo(email) {
        return $api.get("/clients/getUserByUsername", {params: {username: email}})
    }

}