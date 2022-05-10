import * as React from 'react';
import {DataGrid, GridColDef, GridToolbar, GridValueGetterParams, ruRU} from '@mui/x-data-grid';
import {observer} from "mobx-react-lite";
import {useContext, useEffect, useState} from "react";
import {Context} from "../../index";

const columns: GridColDef[] = [
    {field: 'subject', headerName: 'Тема', width: 300},
    {field: 'type', headerName: 'Тип заказа', width: 300},
    {field: 'status', headerName: 'Статус заказа', width: 300},
    {field: 'deadline', headerName: 'Срок сдачи', width: 300, type: "date"},
    {field: 'price', headerName: 'Стоимость', type: 'number', width: 220}
]

const AllOrders = observer(() => {
    const {orderStore} = useContext(Context)
    const [rows, setRows] = useState([])

    useEffect(() => {
        orderStore.getAllOrders().then(r => {
            setRows(r.data)
        })
        }, []
    )

    return (
        <div style={{height: '100%', width: '100%'}}>
            <DataGrid
                rows={rows}
                columns={columns}
                pageSize={10}
                rowsPerPageOptions={[10]}
                components={{
                    Toolbar: GridToolbar,
                }}
                localeText={ruRU.components.MuiDataGrid.defaultProps.localeText}
            />
        </div>
    );
})

export default AllOrders;