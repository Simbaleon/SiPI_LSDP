import * as React from 'react';
import {useContext, useEffect, useState} from 'react';
import {observer} from "mobx-react-lite";
import {Context} from "../../index";
import {
    Box,
    Button,
    Card,
    CardActions,
    CardContent,
    Grid,
    Modal,
    Pagination,
    Stack,
    TextField,
    Typography
} from "@mui/material";
import SnackbarConstructor from "../common/snackbarConstructor/SnackbarConstructor";

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 1000,
    bgcolor: 'background.paper',
    borderRadius: "6px",
    boxShadow: 24,
    p: 4,
    textAlign: 'center'
};

const AllOrders = observer(() => {
    const {orderStore, userStore} = useContext(Context)
    const [page, setPage] = useState(1)
    const [totalPages, setTotalPages] = useState(0);
    const [totalElements, setTotalElements] = useState(0);
    const [orders, setOrders] = useState([]);
    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    const [currentOrder, setCurrentOrder] = useState({})

    useEffect(() => {
            orderStore.getAllOrders(page - 1, 10).then(r => {
                setTotalPages(r.data.totalPages)
                setTotalElements(r.data.totalElements)
                setPage(r.data.number + 1)
                setOrders(r.data.content)
            })
        }, [page]
    )

    return (
        <div style={{height: '100%', width: '90%', margin: 'auto'}}>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box sx={style}>
                    <p><h2>{currentOrder.subject}</h2></p>
                    <p><b>Категория:</b> {currentOrder.type}</p>
                    <p><b>Срок сдачи:</b> {currentOrder.deadline}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <b>Стоимость:</b> {currentOrder.price}&#x20bd;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <b>Количество откликов:</b> {currentOrder.responsesCount}</p>
                    <TextField
                        id="descriptionTextField"
                        name="description"
                        label="Описание"
                        type={"text"}
                        defaultValue={currentOrder.description}
                        fullWidth={true}
                        rows={16}
                        multiline
                        disabled={true}
                        style={{marginBottom: "10px"}}
                    />
                    {!userStore.userResponses.includes(currentOrder.id) ?
                        <Button color={"primary"} variant={"outlined"} onClick={() => {
                            orderStore.respondToOrder(currentOrder.id)
                                .then(handleClose())
                                .catch(SnackbarConstructor("alertRespondContainer", "error", "Не удалось откликнуться на заказ, попробуйте ещё раз"))
                        }}>
                            Откликнуться
                        </Button>
                        : <b>Вы уже откликнулись на этот заказ</b>
                    }
                </Box>
            </Modal>
            {orders.map((order) => (
                <Card style={{margin: "10px"}} variant={"outlined"}>
                    <Grid container spacing={5}>
                        <Grid item xs={12} sm={10}>
                            <CardContent>
                                <Typography>
                                    <p><h3>{order.subject}</h3></p>
                                </Typography>
                                <Typography>
                                    <p><b>Категория:</b> {order.type}</p>
                                </Typography>
                                <Typography>
                                    <p><b>Срок сдачи:</b> {order.deadline}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <b>Стоимость:</b> {order.price}&#x20bd;</p>
                                </Typography>
                            </CardContent>
                        </Grid>
                        <Grid item xs={12} sm={2} margin={"auto"}>
                            <CardActions>
                                <Button color={"primary"} variant={"outlined"}
                                        onClick={() => {
                                            setCurrentOrder(order)
                                            handleOpen()
                                        }}>
                                    Посмотреть
                                </Button>
                            </CardActions>
                        </Grid>
                    </Grid>
                </Card>
            ))}
            <Stack spacing={2}>
                <Pagination
                    count={totalPages}
                    page={page}
                    onChange={(_, num) => setPage(num)}
                    showFirstButton
                    showLastButton
                    sx={{marginY: 3, marginX: "auto"}}
                    color="primary"
                />
            </Stack>
        </div>
    );
})

export default AllOrders;