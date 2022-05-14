import $api from "../http/AxiosConfig";

export default class OrderService {

    static createOrder(subject, description, orderType, deadline, price) {
        return $api.post("/orders/create", {subject, description, orderType, deadline, price})
    }

    static getOrderTypes() {
        return $api.get("/orders/getTypes")
    }

    static getAllOrders(pageN, sizeN) {
        return $api.get("/orders/getAll", {params: {page: pageN, size: sizeN}})
    }

    static getAllOrdersByUserId(email) {
        return $api.get("/orders/getAllByUsername", {params: {username: email}})
    }

    static respondToOrder(id) {
        return $api.patch("/orders/respondToOrder", {id})
    }
}