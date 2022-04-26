import $api from "./AxiosConfig";

export const authEndpoints = {
    registration: (data) => $api().post("/registration", data),
    login: (data) => $api().post("/login", data)
}