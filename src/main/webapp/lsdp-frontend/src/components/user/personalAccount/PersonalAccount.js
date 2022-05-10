import {observer} from "mobx-react-lite";
import {Button} from "@mui/material";
import {useNavigate} from "react-router";
import {useContext, useEffect, useState} from "react";
import {Context} from "../../../index";
import {DataGrid, GridColDef, GridToolbar, ruRU} from "@mui/x-data-grid";
import * as React from "react";

const columns: GridColDef[] = [
    {field: 'subject', headerName: 'Тема', width: 300},
    {field: 'type', headerName: 'Тип заказа', width: 300},
    {field: 'status', headerName: 'Статус заказа', width: 300},
    {field: 'deadline', headerName: 'Срок сдачи', width: 300, type: "date"},
    {field: 'price', headerName: 'Стоимость', type: 'number', width: 220}
]

const PersonalAccount = observer(() => {
    const navigate = useNavigate()
    const {orderStore, userStore} = useContext(Context)
    const [executorRows, setExecutorRows] = useState([])
    const [authorRows, setAuthorRows] = useState([])

    useEffect(() => {
            orderStore.getAllOrdersByUsername(userStore.user).then(r => {
                setExecutorRows(r.data.executor)
                setAuthorRows(r.data.author)
            })
        }, []
    )

    return (
        <div style={{height: '100%', width: '100%'}}>
            <div id={"alertAfterCreatingOrder"} />

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