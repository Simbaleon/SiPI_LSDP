import {observer} from "mobx-react-lite";
import {Button, Grid, TextField} from "@mui/material";
import AddTaskIcon from '@mui/icons-material/AddTask';
import {useFormik} from "formik";
import * as yup from "yup";
import SnackbarConstructor from "../common/snackbarConstructor/SnackbarConstructor";
import {useContext} from "react";
import {Context} from "../../index";
import {useNavigate} from "react-router";

const CreateOrderPage = observer(() => {
    const { orderStore } = useContext(Context)
    const navigate = useNavigate()

    const formik = useFormik({
        initialValues: {
            subject: "",
            description: "",
            orderType: "PROGRAMMING",
            deadline: null,
            price: ""
        },
        validateOnChange: true,
        validationSchema: yup.object({
            subject: yup.string()
                .required("Это поле обязательно"),
            description: yup.string()
                .required("Это поле обязательно"),
            orderType: yup.string()
                .required("Это поле обязательно"),
            deadline: yup.date()
                .min(new Date(), "Некорректная дата сдачи работ")
                .required("Это поле обязательно"),
            price: yup.number()
                .min(100, "Стоимость выполнения работы не может быть меньше 100 руб.")
                .required("Это поле обязательно") //оно итак всегда будет заполнено))))
        }),
        onSubmit: (values => {
            console.log(values.subject, values.description, values.orderType, values.deadline, values.price)
            orderStore?.createOrder(values.subject, values.description, values.orderType, values.deadline, values.price)
                .then(() => {
                    SnackbarConstructor("alertAfterCreatingOrder", "success", "Заказ успешно создан")
                    navigate("/personalAccount")
                })
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
                    value={formik.values.description}
                    onChange={formik.handleChange}
                />
            </Grid>
            <Grid item xs={5}>
                <select
                    id={"orderType"}
                    name={"orderType"}
                    value={formik.values.orderType}
                    error={formik.errors.orderType != null}
                    onChange={formik.handleChange}
                >
                    <option value={'1'}>1</option>
                    <option value={'2'}>2</option>
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