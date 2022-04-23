import axios from "axios";

const backendHost = "http://localhost:8081"

export const authEndpoints = {
    registration: (data) => axios.post(backendHost + "/registration", data),
    login: (data) => axios.post(backendHost + "/login", data)
}