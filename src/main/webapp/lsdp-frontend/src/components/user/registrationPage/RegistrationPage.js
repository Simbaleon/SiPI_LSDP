import {useContext} from "react";
import {Button, Grid, TextField} from "@mui/material";
import {Context} from "../../../index";
import AppRegistrationIcon from '@mui/icons-material/AppRegistration';
import {useFormik} from "formik";
import * as yup from "yup";
import NumberFormat from "react-number-format";
import {observer} from "mobx-react-lite";
import SnackbarConstructor from "../../common/snackbarConstructor/SnackbarConstructor";
import {useNavigate} from "react-router";

function NumberFormatCustom(props) {
    const {inputRef, ...other} = props;

    return (
        <NumberFormat
            {...other}
            getInputRef={inputRef}
            // format="+7 (###) ###-####"
            mask="_"
        />
    );
}

const RegistrationPage = observer(() => {
    const {userStore} = useContext(Context)
    const navigate = useNavigate();

    const formik = useFormik({
        initialValues: {
            fullName: "",
            email: "",
            telephoneNumber: "",
            password: ""
        },
        validateOnChange: true,
        validationSchema: yup.object({
            email: yup.string()
                .email("Неправильный формат эл.почты")
                .required("Это поле обязательно"),
            password: yup.string()
                .required("Это поле обязательно")
                .min(8, "Пароль должен содержать не меньше 8 символов"),
            fullName: yup.string()
                .required("Это поле обязательно"),
            telephoneNumber: yup.string().required("Это поле обязательно"),
        }),
        onSubmit: (values => {
            userStore?.registration(values.fullName, values.email, values.telephoneNumber, values.password)
                .then(() => {
                    SnackbarConstructor("alertAfterRegistration", "success", "Успешная регистрация")
                    navigate("/signin")
                }).catch(() => {
                SnackbarConstructor("alertAfterRegistration", "error", "Пользователь с таким адресом эл.почты или номером телефона уже существует")
            })
        })
    })

    return (
        <Grid
            container
            rowSpacing={1}
            direction="column"
            alignItems={"center"}
            justifyContent={"center"}
            style={{minHeight: '60vh'}}
        >
            <div id={"alertAfterRegistration"} />
            <Grid item xs={5}>
                <TextField
                    id="fullName"
                    name="fullName"
                    error={formik.errors.fullName != null}
                    helperText={formik.errors.fullName}
                    label="Фамилия и имя"
                    type={"text"}
                    value={formik.values.fullName}
                    onChange={formik.handleChange}
                />
            </Grid>
            <Grid item xs={5}>
                <TextField
                    id="email"
                    name="email"
                    error={formik.errors.email != null}
                    helperText={formik.errors.email}
                    label="Эл. почта"
                    type={"text"}
                    value={formik.values.email}
                    onChange={formik.handleChange}
                />
            </Grid>
            <Grid item xs={5}>
                <TextField
                    value={formik.values.telephoneNumber}
                    onChange={formik.handleChange}
                    error={formik.errors.telephoneNumber != null}
                    helperText={formik.errors.telephoneNumber}
                    id="telephoneNumber"
                    name="telephoneNumber"
                    label="Номер телефона"
                    type={"tel"}

                    // InputProps={{
                    //     inputComponent: NumberFormatCustom,
                    // }}
                />
            </Grid>
            <Grid item xs={5}>
                <TextField
                    id="password"
                    name="password"
                    error={formik.errors.password != null}
                    helperText={formik.errors.password}
                    label="Пароль"
                    type={"password"}
                    value={formik.values.password}
                    onChange={formik.handleChange}
                />
            </Grid>
            <Grid item xs={12}>
                <Button
                    startIcon={<AppRegistrationIcon/>}
                    variant={"contained"}
                    color={"warning"}
                    onClick={formik.handleSubmit}
                >
                    Зарегистрироваться
                </Button>
            </Grid>
        </Grid>
    )
})

export default RegistrationPage;