import {makeAutoObservable} from "mobx";
import OrderService from "../services/OrderService";

export default class OrderStore {

    constructor() {
        makeAutoObservable(this)
    }

    createOrder(subject, description, orderType, deadline, price) {
        return OrderService.createOrder(subject, description, orderType, deadline, price)
            .then((response) => {
                return Promise.resolve(response)
            }).catch(() => {
                return Promise.reject()
            })
    }

    getOrderTypes() {
        return OrderService.getOrderTypes()
            .then(r => Promise.resolve(r))
            .catch(() => Promise.reject())
    }

    getAllOrders(page, size) {
        return OrderService.getAllOrders(page, size)
            .then(r => Promise.resolve(r))
            .catch(() => Promise.reject())
    }

    getAllOrdersByUsername(username) {
        return OrderService.getAllOrdersByUserId(username)
            .then(r => Promise.resolve(r))
            .catch(() => Promise.reject())
    }

    respondToOrder(id) {
        return OrderService.respondToOrder(id)
            .then(r => Promise.resolve(r))
            .catch(() => Promise.reject())
    }
}