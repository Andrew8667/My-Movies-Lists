import {
  Alert,
  Box,
  Button,
  Container,
  Modal,
  TextField,
  Typography,
} from "@mui/material";
import axios from "axios";
import React from "react";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
/**
 * Screen for user sign in and registration
 * @returns A screen where users can login or register
 */
const LoginRegistration = function LoginRegistration() {
  const [isLogin, setIsLogin] = useState(true); //screen for either login or registration
  const [email, setEmail] = useState(null); //email of the login details
  const [password, setPassword] = useState(null); //password of the login details
  const [name, setName] = useState(null); //name of user trying to register
  const [errorOpen, setErrorOpen] = useState(false); //controls if error message is visible or not
  const [successOpen, setSuccessOpen] = useState(false); //controls if success message is visible
  const navigate = useNavigate() //provides navigation to other screens

  /**
   *Sends login credentials to be checked in the backend
   */
  const handleLogin = () => {
    axios
      .post("http://localhost:8080/user/login", {
        email: email,
        password: password,
      })
      .then((response) => {
        localStorage.setItem("token", response.data)
        navigate("/home")
      })
      .catch((error) => {
        setErrorOpen(true);
        console.log(error);
      });
  };

  /**
   * Sends registration credentials to be checked in the backend
   */
  const handleRegistration = () => {
    axios
      .post("http://localhost:8080/user/register", {
        email: email,
        password: password,
        name: name,
      })
      .then((response) => {
        setSuccessOpen(true)
        setIsLogin(true)
      })
      .catch((error) => {
        setErrorOpen(true);
        console.log(error);
      });
  };

  return (
    <Container
      maxWidth={false}
      sx={{
        backgroundColor: "#000000",
        margin: 0,
        padding: 0,
        width: "100vw",
        height: "100vh",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <Typography
        sx={{
          fontFamily: "sans-serif",
          color: "#db0000",
          fontWeight: 600,
          fontSize: 64,
        }}
      >
        MY MOVIES LISTS
      </Typography>
      {isLogin ? (
        <Box
          sx={{
            width: "100vw",
            height: "200px",
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            justifyContent: "space-evenly",
          }}
        >
          <TextField
            label="Email"
            variant="outlined"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            InputProps={{
              style: { color: "#FFFFFF" },
            }}
            InputLabelProps={{
              style: { color: "#FFFFFF" },
            }}
            sx={{
              "& .MuiOutlinedInput-root fieldset": {
                borderColor: "#FFFFFF",
              },
              border: 1,
              borderColor: "#FFFFFF",
              borderRadius: 2,
              width: "400px",
            }}
            required
          />
          <TextField
            label="Password"
            variant="outlined"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            InputProps={{
              style: { color: "#FFFFFF" },
            }}
            InputLabelProps={{
              style: { color: "#FFFFFF" },
            }}
            sx={{
              "& .MuiOutlinedInput-root fieldset": {
                borderColor: "#FFFFFF",
              },
              border: 1,
              borderColor: "#FFFFFF",
              borderRadius: 2,
              width: "400px",
            }}
            required
          />
        </Box>
      ) : (
        <Box
          sx={{
            width: "100vw",
            height: "250px",
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            justifyContent: "space-evenly",
          }}
        >
          <TextField
            label="Email"
            variant="outlined"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            InputProps={{
              style: { color: "#FFFFFF" },
            }}
            InputLabelProps={{
              style: { color: "#FFFFFF" },
            }}
            sx={{
              "& .MuiOutlinedInput-root fieldset": {
                borderColor: "#FFFFFF",
              },
              border: 1,
              borderColor: "#FFFFFF",
              borderRadius: 2,
              width: "400px",
            }}
            required
          />
          <TextField
            label="Password"
            variant="outlined"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            InputProps={{
              style: { color: "#FFFFFF" },
            }}
            InputLabelProps={{
              style: { color: "#FFFFFF" },
            }}
            sx={{
              "& .MuiOutlinedInput-root fieldset": {
                borderColor: "#FFFFFF",
              },
              border: 1,
              borderColor: "#FFFFFF",
              borderRadius: 2,
              width: "400px",
            }}
            required
          />
          <TextField
            label="Name"
            variant="outlined"
            value={name}
            onChange={(e) => setName(e.target.value)}
            InputProps={{
              style: { color: "#FFFFFF" },
            }}
            InputLabelProps={{
              style: { color: "#FFFFFF" },
            }}
            sx={{
              "& .MuiOutlinedInput-root fieldset": {
                borderColor: "#FFFFFF",
              },
              border: 1,
              borderColor: "#FFFFFF",
              borderRadius: 2,
              width: "400px",
            }}
            required
          />
        </Box>
      )}
      <Box
        sx={{
          display: "flex",
          height: "75px",
          width: "100vw",
          flexDirection: "column",
          justifyContent: "space-between",
          alignItems: "center",
        }}
      >
        <Button
          variant="contained"
          sx={{ backgroundColor: "#db0000" }}
          onClick={() => {
            isLogin ? handleLogin() : handleRegistration();
          }}
        >
          {isLogin ? "Login" : "Register"}
        </Button>
        <Modal
          open={successOpen}
          sx={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
          onClose={() => setSuccessOpen(false)}
        >
          <Alert severity="success">
            Account successfully created! Please login
          </Alert>
        </Modal>
        <Modal
          open={errorOpen}
          sx={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
          onClose={() => setErrorOpen(false)}
        >
          <Alert severity="error">
            {isLogin
              ? "Error logging in with provided credentials"
              : "Error registering with provided credentials"}
          </Alert>
        </Modal>
        <Typography onClick={() => setIsLogin(!isLogin)}>
          {isLogin
            ? "Don't have an account? Click to register"
            : "Have an account? Click to login"}
        </Typography>
      </Box>
    </Container>
  );
};

export default LoginRegistration;
