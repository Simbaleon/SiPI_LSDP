import {observer} from "mobx-react-lite";
import {Button, Grid, TextField} from "@mui/material";
import AddTaskIcon from '@mui/icons-material/AddTask';
import {useFormik} from "formik";
import * as yup from "yup";
import SnackbarConstructor from "../common/snackbarConstructor/SnackbarConstructor";
import {useContext, useEffect, useState} from "react";
import {Context} from "../../index";
import {useNavigate} from "react-router";

const CreateOrderPage = observer(() => {
    const { orderStore } = useContext(Context)
    const navigate = useNavigate()
    const [orderTypes, setOrders] = useState([])

    useEffect(() => {
            orderStore.getOrderTypes().then(r => {
                const types = r.data;
                setOrders(types)
            })
        }, []
    )

    const formik = useFormik({
        initialValues: {
            subject: "",
            description: "",
            orderType: "Тексты",  //Пока что захардкодил, потому что не знаю, как решить
            deadline: new Date(),
            price: ""
        },
        validateOnChange: true,
        validationSchema: yup.object({
            subject: yup.string()
                .required("Это поле обязательно"),
            description: yup.string()
                .required("Это поле обязательно"),
            deadline: yup.date()
                .min(new Date(), "Некорректная дата сдачи работ")
                .required("Это поле обязательно"),
            price: yup.number()
                .min(100, "Стоимость выполнения работы не может быть меньше 100 руб.")
                .required("Это поле обязательно")
        }),
        onSubmit: (values => {
            // console.log('type' + values.orderType)
            orderStore?.createOrder(values.subject, values.description, values.orderType, new Date(values.deadline), values.price)
                .then(() => {
                    SnackbarConstructor("alertAfterCreatingOrder", "success", "Заказ успешно создан")
                    navigate("/personalAccount")
                }).catch(e => SnackbarConstructor("alertAfterCreatingOrder", "error", "Что-то пошло не так"))
        })
    })

    return (
        <Grid
            container
            rowSpacing={1}
            direction="column"
            alignItems={"center"}
            justifyContent={"center"}
            style={{minHeight: '60vh'}}
        >
            <div id={"alertAfterCreatingOrder"} />
            <Grid item xs={5}>
                <TextField
                    id="subject"
                    name="subject"
                    error={formik.errors.subject != null}
                    helperText={formik.errors.subject}
                    label="Тема"
                    type={"text"}
                    value={formik.values.subject}
                    onChange={formik.handleChange}
                    style={{marginTop: "20px"}}
                />
            </Grid>
            <Grid item xs={5}>

                <select
                    id={"orderType"}
                    name={"orderType"}
                    value={formik.values.orderType}
                    // error={formik.errors.orderType != null}
                    onChange={formik.handleChange}
                >
                    {
                        orderTypes.map(x => {
                            return <option key={x} value={x}>{x}</option>
                        })
                    }
                </select>
            </Grid>
            <Grid item xs={5}>
                <TextField
                    id="deadline"
                    name="deadline"
                    error={formik.errors.deadline != null}
                    helperText={formik.errors.deadline}
                    label="Срок сдачи"
                    type={"date"}
                    value={formik.values.deadline}
                    onChange={formik.handleChange}
                />
            </Grid>
            <Grid item xs={5}>
                <TextField
                    id="price"
                    name="price"
                    error={formik.errors.price != null}
                    helperText={formik.errors.price}
                    label="Стоимость работы"
                    type={"number"}
                    value={formik.values.price}
                    onChange={formik.handleChange}
                />
            </Grid>
            <Grid item xs={5}>
                <TextField
                    id="description"
                    name="description"
                    error={formik.errors.description != null}
                    helperText={formik.errors.description}
                    label="Описание"
                    type={"text"}
                    style={{width: 1000}}
                    rows={16}
                    multiline
                    value={formik.values.description}
                    onChange={formik.handleChange}
                />
            </Grid>
            <Grid item xs={12}>
                <Button
                    startIcon={<AddTaskIcon/>}
                    variant={"contained"}
                    color={"success"}
                    onClick={formik.handleSubmit}
                >
                    Создать
                </Button>
            </Grid>
        </Grid>
    )
})

export default CreateOrderPage;