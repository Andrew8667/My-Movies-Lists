import { Box, Button, Container, TextField, Typography } from "@mui/material";
import React from "react";
import { useState } from "react";
import { Link } from "react-router-dom";
/**
 * Screen for user sign in and registration
 * @returns A screen where users can login or register
 */
const LoginRegistration = function LoginRegistration() {
  const [isLogin, setIsLogin] = useState(true); //screen for either login or registration
  const [email, setEmail] = useState(""); //email of the login details
  const [password, setPassword] = useState(""); //password of the login details
  const [name, setName] = useState(""); //name of user trying to register

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
        MY MOVIES LIST
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
            InputLabelProps={{
              style: { color: "#FFFFFF" },
            }}
            sx={{
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
            InputLabelProps={{
              style: { color: "#FFFFFF" },
            }}
            sx={{
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
            InputLabelProps={{
              style: { color: "#FFFFFF" },
            }}
            sx={{
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
            InputLabelProps={{
              style: { color: "#FFFFFF" },
            }}
            sx={{
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
            InputLabelProps={{
              style: { color: "#FFFFFF" },
            }}
            sx={{
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
        <Button variant="contained" sx={{ backgroundColor: "#db0000" }}>{isLogin ? "Login" : "Register"}
        </Button>
        <Typography
        onClick={()=>setIsLogin(!isLogin)}
        >
          {isLogin
            ? "Don't have an account? Click to register"
            : "Have an account? Click to login"}
        </Typography>
      </Box>
    </Container>
  );
};

export default LoginRegistration;
