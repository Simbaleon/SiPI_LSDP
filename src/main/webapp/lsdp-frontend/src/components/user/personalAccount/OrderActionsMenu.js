import * as React from "react";
import {IconButton, Menu, MenuItem} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import {observer} from "mobx-react-lite";
import {useContext, useState} from "react";
import {Context} from "../../../index";
import {useNavigate} from "react-router";

const OrderActionsMenu = observer((props) => {
    const {orderStore} = useContext(Context)
    const navigate = useNavigate()
    const [anchorEl, setAnchorEl] = React.useState(null);
    const open = Boolean(anchorEl);
    const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
    };
    const handleClose = () => {
        setAnchorEl(null);
    };

    const [openExecutorModal, setOpenExecutorModal] = useState(false);
    const handleOpenExecutorModal = () => setOpenExecutorModal(true);
    const handleCloseExecutorModal = () => setOpenExecutorModal(false);

    return (
        <div>
            <IconButton
                id="demo-positioned-button"
                aria-controls={open ? 'demo-positioned-menu' : undefined}
                aria-haspopup="true"
                aria-expanded={open ? 'true' : undefined}
                onClick={handleClick}
            >
                <MenuIcon/>
            </IconButton>
            <Menu
                id="demo-positioned-menu"
                aria-labelledby="demo-positioned-button"
                anchorEl={anchorEl}
                open={open}
                onClose={handleClose}
                anchorOrigin={{
                    vertical: 'top',
                    horizontal: 'left',
                }}
                transformOrigin={{
                    vertical: 'top',
                    horizontal: 'left',
                }}
            >
                <MenuItem onClick={handleClose}>Назначить исполнителя</MenuItem>
                <MenuItem onClick={() => {
                    orderStore.deleteOrder(props.orderId).then((r) => {

                    })
                }}>Удалить</MenuItem>
            </Menu>
        </div>
    );
})

export default OrderActionsMenu;