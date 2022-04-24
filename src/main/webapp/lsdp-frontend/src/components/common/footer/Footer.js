import s from "./Footer.module.css"
import {Box, Button, Container, Grid} from "@mui/material";

function Footer() {
    return (
        <footer>
            <Box bgcolor={"#1c1427"} color={"white"}>
                <Container maxWidth={"lg"}>
                    <Grid container spacing={5}>
                        <Grid item xs={12} sm={8}>
                            <Box className={s.logo}>
                                <a>Freelance-platform</a>
                            </Box>
                            <Box>
                                <a>Работа выполнена в рамках учебного проекта</a>
                            </Box>
                        </Grid>
                        <Grid item xs={12} sm={1}>
                            <Box>
                                <Button variant={"outlined"} color={"inherit"}>Войти</Button>
                            </Box>
                        </Grid>
                        <Grid item xs={12} sm={1}>
                            <Box>
                                <Button variant={"outlined"} color={"inherit"}>Зарегистрироваться</Button>
                            </Box>
                        </Grid>
                    </Grid>
                    <Box textAlign={"center"} pt={5} pb={5}>
                        LSD'P Software &reg; {new Date().getFullYear()}
                    </Box>
                </Container>
            </Box>
        </footer>
    )
}

export default Footer;