import * as React from 'react';
import {DataGrid, GridColDef, GridValueGetterParams} from '@mui/x-data-grid';
import {observer} from "mobx-react-lite";
import {useContext, useEffect, useState} from "react";
import {Context} from "../../index";

const columns: GridColDef[] = [
    {field: 'subject', headerName: 'Тема', width: 300},
    {field: 'type', headerName: 'Тип заказа', width: 300},
    {field: 'status', headerName: 'Статус заказа', width: 300},
    {field: 'deadline', headerName: 'Срок сдачи', width: 300},
    {field: 'price', headerName: 'Стоимость', type: 'number', width: 200}
]

const AllOrders = observer(() => {
    const {orderStore} = useContext(Context)
    const [rows, setRows] = useState([])

    useEffect(() => {
        orderStore.getAllOrders().then(r => {
            setRows(r.data)
            console.log(rows)
        })
        }, []
    )

    return (
        <div style={{height: 400, width: '100%'}}>
            <DataGrid
                rows={rows}
                columns={columns}
                pageSize={5}
                rowsPerPageOptions={[5]}
            />
        </div>
    );
})

export default AllOrders;