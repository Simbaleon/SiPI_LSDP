import {observer} from "mobx-react-lite";
import {Button} from "@mui/material";
import {useNavigate} from "react-router";

const PersonalAccount = observer(() => {
    const navigate = useNavigate()
    return (
        <div>
            <p>Здесь будет располагаться инфа о пользователе</p>
            <h3>Заказы</h3>
            <Button onClick={() => navigate("/orders/create")}>
                Создать новый заказ
            </Button>
            <p>Здесь будет таблица с принятыми заказами с дрилдаунами</p>
        </div>
    )
})

export default PersonalAccount;