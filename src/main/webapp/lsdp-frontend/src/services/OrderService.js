import $api from "../http/AxiosConfig";

export default class OrderService {

    static createOrder(subject, description, orderType, deadline, price) {
        return $api.post("/orders/create", {subject, description, orderType, deadline, price})
    }
}