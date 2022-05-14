import {Alert, Snackbar} from "@mui/material";
import {useState} from "react";

function SnackbarComponent(props) {
    const [isOpen, setOpen] = useState(true)

    const handleClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }

        setOpen(false);
    };

    return (
        <Snackbar open={isOpen} autoHideDuration={6000} onClose={handleClose}>
            <Alert severity={props.type} sx={{width: '100%'}}>
                {props.message}
            </Alert>
        </Snackbar>
    )
}

export default SnackbarComponent;