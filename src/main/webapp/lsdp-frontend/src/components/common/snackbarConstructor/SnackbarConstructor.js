import {Alert, Snackbar} from "@mui/material";

function SnackbarConstructor(props) {
    return (
        <Snackbar
            open={true}
            autoHideDuration={6000}
        >
            <Alert
                severity={props.type}
                sx={{width: '100%'}}>
                {props.message}
            </Alert>
        </Snackbar>
    )
}

export default SnackbarConstructor;