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

    static deleteOrder(orderId) {
        return $api.delete("/orders/deleteOrder", {params: {id: orderId}})
    }

    static getOrderById(orderId) {
        return $api.get("/orders/getOrderById", {params: {id: orderId}})
    }

    static getAllResponsesForOrder(orderId) {
        return $api.get("/orders/getAllResponsesForOrder", {params: {id: orderId}})
    }

    static assignUserToOrder(orderId, userId) {
        return $api.patch("/orders/assignUserToOrder", {orderId, userId})
    }

    static changeOrderStatus(orderId, status) {
        return $api.patch("/orders/changeOrderStatus", {orderId, status})
    }
}