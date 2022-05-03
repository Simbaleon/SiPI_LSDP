import {Alert, Snackbar} from "@mui/material";
import {createRoot} from "react-dom/client";

function SnackbarConstructor(alertContainerName, type, message) {
    const alertContainer = document.getElementById(alertContainerName);
    const root = createRoot(alertContainer);
    root.render(
        <Snackbar open={true} autoHideDuration={6000}>
            <Alert severity={type} sx={{ width: '100%' }} >
                {message}
            </Alert>
        </Snackbar>
    );
}

export default SnackbarConstructor;