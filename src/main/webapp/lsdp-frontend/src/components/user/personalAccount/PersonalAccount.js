import {observer} from "mobx-react-lite";
import {Box, Button, Card, CardActions, CardContent, Modal, TextField, Typography} from "@mui/material";
import {useNavigate} from "react-router";
import * as React from "react";
import {useContext, useEffect, useState} from "react";
import {Context} from "../../../index";
import {DataGrid, GridColDef, GridToolbar, ruRU} from "@mui/x-data-grid";
import SnackbarConstructor from "../../common/snackbarConstructor/SnackbarConstructor";

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
};

const columns: GridColDef[] = [
    {field: 'subject', headerName: 'Тема', width: 360},
    {field: 'type', headerName: 'Тип заказа', width: 230},
    {field: 'status', headerName: 'Статус заказа', width: 230},
    {field: 'deadline', headerName: 'Срок сдачи', width: 230, type: "date"},
    {field: 'price', headerName: 'Стоимость', type: 'number', width: 220}
]

const PersonalAccount = observer(() => {
    const navigate = useNavigate()
    const {orderStore, userStore} = useContext(Context)
    const [executorRows, setExecutorRows] = useState([])
    const [authorRows, setAuthorRows] = useState([])
    const [user, setUser] = useState({})
    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    useEffect(() => {
            orderStore.getAllOrdersByUsername(userStore.user)
                .then(r => {
                    setExecutorRows(r.data.executor)
                    setAuthorRows(r.data.author)
                })
            userStore.getUserInfo(userStore.user).then(r => {
                setUser(r.data)
            })
        }, []
    )

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
                    <Typography>
                        <p><b>Описание:</b> {user.description}</p>
                    </Typography>
                    <CardActions>
                        <Button color={"inherit"} variant={"outlined"} onClick={handleOpen}>
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
                    />
                    <Button style={{marginTop: "10px", margin: "auto"}} color={"inherit"} variant={"outlined"}
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

            <h2>Заказы</h2>
            <Button color={"inherit"} variant={"outlined"} onClick={() => navigate("/orders/create")}>
                Создать новый заказ
            </Button>
            <h3>Мои заказы:</h3>
            {(authorRows.length !== 0 ?
                    <div style={{height: '45.5%', width: '100%'}}>
                        <DataGrid
                            rows={authorRows}
                            columns={columns}
                            pageSize={4}
                            rowsPerPageOptions={[4]}
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
                    <div style={{height: '45.5%', width: '100%'}}>
                        <DataGrid
                            rows={executorRows}
                            columns={columns}
                            pageSize={4}
                            rowsPerPageOptions={[4]}
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