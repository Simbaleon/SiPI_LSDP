import {makeAutoObservable} from "mobx";
import OrderService from "../services/OrderService";
import SnackbarConstructor from "../components/common/snackbarConstructor/SnackbarConstructor";

export default class OrderStore {

    constructor() {
        makeAutoObservable(this)
    }

    currentOrderId = 0

    currentManagedOrder = {}

    currentManagedOrderExecutor = {}

    setCurrentManagedOrder(order) {
        this.currentManagedOrder = order
    }

    setCurrentManagedOrderExecutor(executor) {
        this.currentManagedOrderExecutor = executor
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
            .then(r => {
                this.executorRows = r.data.executor
                this.authorRows = r.data.author
                return Promise.resolve(r)
            })
            .catch(() => Promise.reject())
    }

    respondToOrder(id) {
        return OrderService.respondToOrder(id)
            .then(r => Promise.resolve(r))
            .catch(() => Promise.reject())
    }

    deleteOrder(id) {
        return OrderService.deleteOrder(id)
            .then(r => {
                // SnackbarConstructor("alertAfterDeletingOrder", "success", "Заказ успешно удалён")
                return Promise.resolve(r)
            })
            .catch(() => {
                // SnackbarConstructor("alertAfterDeletingOrder", "error", "При удалении заказа что-то пошло не так, попробуйте ещё раз")
                return Promise.reject()
            })
    }

    getOrderById(id) {
        return OrderService.getOrderById(id)
            .then(r => {
                this.setCurrentManagedOrder(r.data)
                this.setCurrentManagedOrderExecutor(r.data.executor)
                console.log(this.currentManagedOrder)
                return Promise.resolve(r)
            })
            .catch(() => Promise.reject())
    }

    getAllResponsesForOrder(id) {
        return OrderService.getAllResponsesForOrder(id)
            .then(r => Promise.resolve(r))
            .catch(() => Promise.reject())
    }

    assignUserToOrder(orderId, userId) {
        return OrderService.assignUserToOrder(orderId, userId)
            .then(r => Promise.resolve(r))
            .catch(() => Promise.reject())
    }

    changeOrderStatus(orderId, status) {
        return OrderService.changeOrderStatus(orderId, status)
            .then(r => Promise.resolve(r))
            .catch(() => Promise.reject())
    }
}