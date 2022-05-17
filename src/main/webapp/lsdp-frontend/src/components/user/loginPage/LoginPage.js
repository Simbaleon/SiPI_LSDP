import {useContext} from "react";
import {Button, Grid, TextField} from "@mui/material";
import {Context} from "../../../index";
import LoginIcon from '@mui/icons-material/Login';
import {useFormik} from "formik";
import * as yup from "yup";
import {useNavigate} from "react-router";
import {observer} from "mobx-react-lite";
import SnackbarConstructor from "../../common/snackbarConstructor/SnackbarConstructor";

const LoginPage = observer(() => {
    const {userStore} = useContext(Context)
    const navigate = useNavigate();

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
            userStore?.login(values.email, values.password)
                .then(() => {
                    if (userStore.isUser()) {
                        navigate("/personalAccount")
                    }
                    if (userStore.isAdmin()) {
                        navigate("/adminPage")
                    }
                    SnackbarConstructor("alertSuccessAfterLogin", "success", "Успешная авторизация")
                }).catch(() => {
                SnackbarConstructor("alertErrorAfterLogin", "error", "Неправильный адрес эл.почты или пароль")
            })

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
                <div id={"alertErrorAfterLogin"} />
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
})

export default LoginPage;