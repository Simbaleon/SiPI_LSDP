import $api from "../http/AxiosConfig";

export default class OrderService {

    static createOrder(subject, description, orderType, deadline, price) {
        return $api.post("/orders/create", {subject, description, orderType, deadline, price})
            .then((response) => {
                console.log("Заказ создан")
                return Promise.resolve(response)
            }).catch(() => {
                return Promise.reject()
            })
    }
}