import * as React from 'react';
import {observer} from "mobx-react-lite";
import {useContext, useEffect, useState} from "react";
import {Context} from "../../index";
import {Card, CardContent, Pagination, PaginationItem, Stack, Typography} from "@mui/material";

const AllOrders = observer(() => {
    const {orderStore} = useContext(Context)
    const [page, setPage] = useState(1)
    const [totalPages, setTotalPages] = useState(0);
    const [totalElements, setTotalElements] = useState(0);
    const [orders, setOrders] = useState([]);

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
            {orders.map((order) => (
                <Card style={{margin: "10px"}} variant={"outlined"}>
                    <CardContent>
                        <Typography>
                            <p><h3>{order.subject}</h3></p>
                        </Typography>
                        <Typography>
                            <p><b>Категория:</b> {order.type}</p>
                        </Typography>
                        <Typography>
                            <p><b>Срок сдачи:</b> {order.deadline}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <b>Стоимость:</b> {order.price}</p>
                        </Typography>
                    </CardContent>
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