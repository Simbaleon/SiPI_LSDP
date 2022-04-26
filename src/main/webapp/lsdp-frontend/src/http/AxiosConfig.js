import axios from "axios";

export const backendHost = "http://localhost:8081"

const accessTokenPrefix = "Bearer_"

const $api = axios.create({
    withCredentials: false,
    baseURL: backendHost
})

$api.interceptors.request.use((config) => {
    config.headers.AccessToken = accessTokenPrefix + localStorage.getItem("accesstoken")
    return config
})

export default $api;