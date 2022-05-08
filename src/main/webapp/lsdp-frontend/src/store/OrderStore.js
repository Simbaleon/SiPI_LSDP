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
}