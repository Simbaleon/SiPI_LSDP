import {useContext} from "react";
import {Button, Grid, TextField} from "@mui/material";
import {Context} from "../../../index";
import AppRegistrationIcon from '@mui/icons-material/AppRegistration';
import MuiPhoneInput from 'material-ui-phone-number';
import { useFormik } from "formik";
import * as yup from "yup";

function RegistrationPage() {
    const {userStore} = useContext(Context)

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
                .email("Invalid email")
                .required("This field is required"),
            password: yup.string()
                .required("This field is required")
                .min(8, "Must be 8 characters or more"),
            fullName: yup.string()
                .required("This field is required"),
            telephoneNumber: yup.string()
        }),
        onSubmit: (values => {
            console.log(values.fullName, values.email, values.telephoneNumber, values.password)
            userStore?.registration(values.fullName, values.email, values.telephoneNumber, values.password)
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
            <Grid item xs={5}>
                <MuiPhoneInput
                    defaultCountry='ru'
                    id="telephoneNumber"
                    name="telephoneNumber"
                    error={formik.errors.telephoneNumber != null}
                    helperText={formik.errors.telephoneNumber}
                    label="Номер телефона"
                    value={formik.values.telephoneNumber}
                    onChange={formik.handleChange}
                />
            </Grid>
                <Grid item xs={12}>
                    <Button
                        startIcon={<AppRegistrationIcon />}
                        variant={"contained"}
                        color={"warning"}
                        onClick={formik.handleSubmit}
                    >
                        Зарегистрироваться
                    </Button>
                </Grid>
        </Grid>
    )
}

export default RegistrationPage;