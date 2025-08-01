import {Container,Typography,Button,Box,Paper,TextField,Link} from '@mui/material'
import React, { useState } from 'react';
import axios from 'axios'
import { useNavigate } from 'react-router-dom';
const Login = function Login(){
    const [email,setEmail] = useState('')
    const [password,setPassword] = useState('')
    const navigate = useNavigate()

    const processLogin = ()=>{
        axios.post('http://localhost:8080/user/login',{
            "email":email,
            "password":password
        })
        .then((response)=>{
            localStorage.setItem('token',response.data)
            navigate('/home')
        })
        .catch((error)=>console.log(error))
    } 

    return(
        <Container maxWidth={false}>
            <Paper elevation={3} sx={{width:'40vw',height:'50vh',display:'flex',flexDirection:'column',justifyContent:'space-evenly',borderRadius:5,alignItems:'center'}}>
                <Typography sx={{fontFamily:'Sans-serif',fontWeight:600,fontSize:32,color:'#db0000'}}>MY MOVIES LISTS</Typography>
                <Box sx={{width:'100%',height:'50%',display:'flex',flexDirection:'column',justifyContent:'space-evenly',alignItems:'center'}}>
                    <TextField required onChange={(e)=>setEmail(e.target.value)} label="Email" variant="outlined" sx={{width:'75%'}}/>
                    <TextField required onChange={(e)=>setPassword(e.target.value)} type='password' label="Password" variant="outlined" sx={{width:'75%'}}/>
                </Box>
                <Button variant='contained' onClick={processLogin}  sx={{backgroundColor:'#db0000', width: 'auto'}}>Login</Button>
                <Link href='/register' underline='none' sx={{color:'#db0000'}}>Don't have an account? Click to register</Link>
            </Paper>
        </Container>
    )
}

export default Login;