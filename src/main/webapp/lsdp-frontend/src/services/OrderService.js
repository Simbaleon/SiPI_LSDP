import $api from "../http/AxiosConfig";

export default class OrderService {

    static createOrder(subject, description, orderType, deadline, price) {
        return $api.post("/orders/create", {subject, description, orderType, deadline, price})
    }

    static getOrderTypes() {
        return $api.get("/orders/getTypes")
    }

    static getAllOrders() {
        return $api.get("/orders/getAll")
    }

    static getAllOrdersByUserId(email) {
        return $api.get("/orders/getAllByUsername", {params: {username: email}})
    }
}