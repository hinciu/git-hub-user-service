import React, {useState} from "react";
import {TextField, Button, Box, Typography, Container} from "@mui/material";
import {setAuthToken} from "../utils/auth-utils";
import {authWithPassword} from "../api/loginApi";
import {MAIN_PAGE} from "../constants/path";

const LoginPage: React.FC = () => {
    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [errorMessage, setErrorMessage] = useState<string>("");

    const handleLogin = () => {
        authWithPassword({username, password}).then((response) => {
            setAuthToken(response.data.jwt);
            window.location.href = MAIN_PAGE;
        }).catch(({response: {data, status}}) => {
            if (status === 401) {
                setErrorMessage("Invalid username or password");
            } else {
                setErrorMessage("An error occurred. Please try again later.");
            }
        })

    };

    return (
        <Container maxWidth="sm">
            <Box display="flex" flexDirection="column" alignItems="center" mt={2}>
                <Typography variant="h4" gutterBottom>
                    Login
                </Typography>
                <TextField
                    label="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    margin="normal"
                    fullWidth
                />
                <TextField
                    label="Password"
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    margin="normal"
                    fullWidth
                />
                {errorMessage && (
                    <Typography color="error" mt={2}>
                        {errorMessage}
                    </Typography>
                )}
                <Button variant="contained" color="primary" onClick={handleLogin} sx={{mt: 2}}>
                    Log In
                </Button>
            </Box>
        </Container>
    );
};

export default LoginPage;