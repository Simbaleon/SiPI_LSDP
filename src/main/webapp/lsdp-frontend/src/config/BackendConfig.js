import axios from "axios";

export const backendHost = "http://localhost:8081"

const authEndpoints = {
    registration: (data) => axios.post("/registration", data),
    login: (data) => axios.post("/login", data)
}