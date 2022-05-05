import {makeAutoObservable} from "mobx";
import OrderService from "../services/OrderService";

export default class OrderStore {

    constructor() {
        makeAutoObservable(this)
    }

    createOrder(subject, description, orderType, deadline, price) {
        return OrderService.createOrder(subject, description, orderType, deadline, price)
    }
}