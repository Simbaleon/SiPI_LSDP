import {useContext, useState} from "react";
import {Alert, Button, Grid, Snackbar, TextField} from "@mui/material";
import {Context} from "../../../index";
import LoginIcon from '@mui/icons-material/Login';
import {useFormik} from "formik";
import * as yup from "yup";
import {useNavigate} from "react-router";

function LoginPage() {
    const {userStore} = useContext(Context)
    const navigate = useNavigate();
    const [open, setOpen] = useState(false)


    const formik = useFormik({
        initialValues: {
            email: "",
            password: ""
        },
        validateOnChange: true,
        validationSchema: yup.object({
            email: yup.string()
                .email("Неправильный формат эл.почты")
                .required("Это поле обязательно"),
            password: yup.string()
                .required("Это поле обязательно")
                .min(8, "Пароль должен содержать не меньше 8 символов")
        }),
        onSubmit: (values => {
            console.log(values.email, values.password)
            userStore?.login(values.email, values.password)
            navigate("/personalAccount")
        })
    })

    return (
        <form>
            <Grid
                container
                rowSpacing={1}
                direction="column"
                alignItems={"center"}
                justifyContent={"center"}
                style={{minHeight: '50vh'}}
            >
                <Grid item xs={12} sm={6} md={3}>
                    <TextField
                        error={formik.errors.email != null}
                        helperText={formik.errors.email}
                        id="email"
                        name="email"
                        label="Эл. почта"
                        type={"text"}
                        value={formik.values.email}
                        onChange={formik.handleChange}
                    />
                </Grid>
                <Grid item xs={5}>
                    <TextField
                        error={formik.errors.password != null}
                        id="password"
                        name="password"
                        label="Пароль"
                        type={"password"}
                        helperText={formik.errors.password}
                        value={formik.values.password}
                        onChange={formik.handleChange}
                    />
                </Grid>
                <Grid item xs={12}>
                    <Button
                        startIcon={<LoginIcon/>}
                        variant={"contained"}
                        color={"success"}
                        onClick={formik.handleSubmit}
                    >
                        Войти
                    </Button>
                </Grid>
            </Grid>
        </form>
    )
}

export default LoginPage;