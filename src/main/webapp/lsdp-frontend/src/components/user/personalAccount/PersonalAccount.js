import {observer} from "mobx-react-lite";
import {
    Box,
    Button,
    Card,
    CardActions,
    CardContent,
    IconButton,
    Menu,
    MenuItem,
    Modal,
    TextField,
    Typography
} from "@mui/material";
import MenuIcon from '@mui/icons-material/Menu';
import {useNavigate} from "react-router";
import * as React from "react";
import {useContext, useEffect, useState} from "react";
import {Context} from "../../../index";
import {DataGrid, GridColDef, GridToolbar, ruRU} from "@mui/x-data-grid";
import SnackbarConstructor from "../../common/snackbarConstructor/SnackbarConstructor";
import OrderActionsMenu from "./OrderActionsMenu";
import {NavLink} from "react-router-dom";

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


const PersonalAccount = observer(() => {
    const navigate = useNavigate()
    const {orderStore, userStore} = useContext(Context)
    const [executorRows, setExecutorRows] = useState([])
    const [authorRows, setAuthorRows] = useState([])
    const [user, setUser] = useState({})
    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    const [render, setRender] = useState(false)

    useEffect(() => {
            orderStore.getAllOrdersByUsername(userStore.user)
                .then(r => {
                    setExecutorRows(r.data.executor)
                    setAuthorRows(r.data.author)
                })
            userStore.getUserInfo(userStore.user).then(r => {
                setUser(r.data)
            })
            userStore.getUserOrderResponses().then()
        }, [render]
    )

    const executorColumns: GridColDef[] = [
        {
            field: 'markAsReadyToCheck', headerName: "", width: 180, renderCell: params => {
                return (
                    <Button color={"primary"} variant={"outlined"} onClick={() => {
                        orderStore.currentOrderId = params.row.id
                        orderStore.changeOrderStatus(orderStore.currentOrderId, "READY_TO_CHECK").then(() => {
                            setRender(!render)
                        })
                    }}>
                        Готов к проверке
                    </Button>
                )
            }
        },
        {field: 'id', headerName: 'id', type: "number", width: 100},
        {field: 'subject', headerName: 'Тема', width: 360},
        {field: 'type', headerName: 'Тип заказа', width: 230},
        {field: 'status', headerName: 'Статус заказа', width: 230},
        {field: 'deadline', headerName: 'Срок сдачи', width: 140, type: "date"},
        {field: 'price', headerName: 'Стоимость', type: 'number', width: 140}
    ]

    const authorColumns: GridColDef[] = [
        // {
        //     field: 'actiona', headerName: 'Действия', align: "center", width: 80, renderCell: (params) => {
        //
        //
        //         return (
        //             // <OrderActionsMenu orderId={params.row.id}/>
        //             <div>
        //                 <IconButton
        //                     id="demo-positioned-button"
        //                     aria-controls={open ? 'demo-positioned-menu' : undefined}
        //                     aria-haspopup="true"
        //                     aria-expanded={open ? 'true' : undefined}
        //                     onClick={handleClick}
        //                 >
        //                     <MenuIcon/>
        //                 </IconButton>
        //                 <Menu
        //                     id="demo-positioned-menu"
        //                     aria-labelledby="demo-positioned-button"
        //                     anchorEl={anchorEl}
        //                     open={openMenu}
        //                     onClose={handleCloseMenu}
        //                     anchorOrigin={{
        //                         vertical: 'top',
        //                         horizontal: 'left',
        //                     }}
        //                     transformOrigin={{
        //                         vertical: 'top',
        //                         horizontal: 'left',
        //                     }}
        //                 >
        //                     <MenuItem onClick={handleClose}>Назначить исполнителя</MenuItem>
        //                     <MenuItem onClick={() => {
        //                         orderStore.deleteOrder(params.row.id).then((r) => {
        //                                 orderStore.getAllOrdersByUsername(userStore.user)
        //                                     .then(r => {
        //                                         setExecutorRows(r.data.executor)
        //                                         setAuthorRows(r.data.author)
        //                                     })
        //                         })
        //                     }}>Удалить</MenuItem>
        //                 </Menu>
        //             </div>
        //         )
        //
        //     }
        // },
        {
            field: 'management', headerName: "", width: 130, renderCell: params => {
                return (
                    <Button color={"primary"} variant={"outlined"} onClick={() => {
                        orderStore.currentOrderId = params.row.id
                        navigate("/orders/manageOrder")
                    }}>
                        Управление
                    </Button>
                )
            }
        },
        {field: 'id', headerName: 'id', type: "number", width: 100},
        {field: 'subject', headerName: 'Тема', width: 360},
        {field: 'type', headerName: 'Тип заказа', width: 230},
        {field: 'status', headerName: 'Статус заказа', width: 230},
        {field: 'deadline', headerName: 'Срок сдачи', width: 140, type: "date"},
        {field: 'price', headerName: 'Стоимость', type: 'number', width: 140}
    ]

    return (
        <div style={{height: '100%', width: '90%', margin: 'auto'}}>
            <Card style={{marginTop: "20px"}} variant={"outlined"}>
                <CardContent>
                    <Typography>
                        <p><h3>{user.fullName}</h3></p>
                    </Typography>
                    <Typography>
                        <p><b>Эл. почта:</b> {user.email}</p>
                    </Typography>
                    <Typography>
                        <p><b>Номер телефона:</b> {user.telephoneNumber}</p>
                    </Typography>
                    <TextField
                        id="description"
                        name="description"
                        label="Описание"
                        type={"text"}
                        value={user.description}
                        disabled
                        fullWidth={true}
                        rows={16}
                        multiline
                        InputLabelProps={{ shrink: true }}
                        style={{marginBottom: "10px"}}
                    />
                    <CardActions>
                        <Button color={"primary"} variant={"outlined"} onClick={handleOpen}>
                            Редактировать
                        </Button>
                    </CardActions>
                </CardContent>
            </Card>
            <div id={"alertAfterChangingDescription"}/>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box sx={style}>
                    <TextField
                        id="changeDescriptionTextField"
                        name="description"
                        label="Описание"
                        type={"text"}
                        defaultValue={user.description}
                        fullWidth={true}
                        rows={16}
                        multiline
                        style={{marginBottom: "10px"}}
                    />
                    <Button style={{marginTop: "10px", margin: "auto"}} color={"primary"} variant={"outlined"}
                            onClick={() => {
                                const newValue = document.getElementById("changeDescriptionTextField").value
                                userStore.changeUserDescription(userStore.user, newValue)
                                    .then(r => {
                                        user.description = newValue
                                        handleClose()
                                    }).catch(() => SnackbarConstructor("alertAfterChangingDescription", "error", "Не удалось сохранить изменения, попробуйте ещё раз"))
                            }}>
                        Сохранить
                    </Button>
                </Box>
            </Modal>

            <div id={"alertAfterCreatingOrder"}/>
            <div id={"alertAfterDeletingOrder"}/>
            <div id={"alertSuccessAfterLogin"}/>

            <h2>Заказы</h2>
            <Button color={"primary"} variant={"outlined"} onClick={() => navigate("/orders/create")}>
                Создать новый заказ
            </Button>
            <h3>Мои заказы:</h3>
            {(authorRows.length !== 0 ?
                    <div style={{height: '424px', width: '100%'}}>
                        <DataGrid
                            rows={authorRows}
                            columns={authorColumns}
                            pageSize={5}
                            rowsPerPageOptions={[5]}
                            components={{
                                Toolbar: GridToolbar,
                            }}
                            localeText={ruRU.components.MuiDataGrid.defaultProps.localeText}
                        />
                    </div>
                    : <div>Вы не создавали заказов</div>
            )}
            <h3>Работаю над:</h3>
            {(executorRows.length !== 0 ?
                    <div style={{height: '424px', width: '100%'}}>
                        <DataGrid
                            rows={executorRows}
                            columns={executorColumns}
                            pageSize={5}
                            rowsPerPageOptions={[5]}
                            components={{
                                Toolbar: GridToolbar,
                            }}
                            localeText={ruRU.components.MuiDataGrid.defaultProps.localeText}
                        />
                    </div>
                    : <div>Нет заказов в работе</div>
            )}
        </div>
    )
})

export default PersonalAccount;