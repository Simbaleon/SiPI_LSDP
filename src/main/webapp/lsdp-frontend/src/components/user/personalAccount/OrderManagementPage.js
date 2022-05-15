import {observer} from "mobx-react-lite";
import {useContext, useEffect, useState} from "react";
import {Context} from "../../../index";
import {Box, Button, Container, Grid, TextField} from "@mui/material";
import AddTaskIcon from "@mui/icons-material/AddTask";
import {useFormik} from "formik";
import * as yup from "yup";
import SnackbarConstructor from "../../common/snackbarConstructor/SnackbarConstructor";

const OrderManagementPage = observer(() => {
    const {orderStore} = useContext(Context)
    const [orderTypes, setOrders] = useState([])
    const [order, setOrder] = useState({})

    useEffect(() => {
            orderStore.getOrderTypes().then(r => {
                const types = r.data;
                setOrders(types)
            })
            orderStore.getOrderById(orderStore.currentOrderId).then(r => {
                setOrder(r.data)
                console.log(r.data)
                console.log(order)
            })
        }, []
    )

    const formik = useFormik({
        initialValues: {
            subject: order.subject,
            description: order.description,
            orderType: order.type,
            deadline: order.deadline,
            price: order.price
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

        })
    })

    return (
        <div style={{height: '100%', width: '90%', margin: 'auto'}}>
            <Box>
                <Container maxWidth={"lg"}>
                    <Grid container spacing={5}>
                        <Grid item xs={12} sm={8}>
                            <Grid
                                container
                                rowSpacing={1}
                                direction="column"
                                alignItems={"center"}
                                justifyContent={"center"}
                                style={{minHeight: '60vh'}}
                            >
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
                        </Grid>
                    </Grid>
                </Container>
            </Box>
        </div>
    )
})

export default OrderManagementPage;