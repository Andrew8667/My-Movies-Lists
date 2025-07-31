import {Container,Typography,Button,Box,Paper,TextField,Link} from '@mui/material'
import React, { useState } from 'react';
import axios from 'axios'
import { useNavigate } from 'react-router-dom';
const Register = function Register(){
    const [email,setEmail] = useState('')
    const [password,setPassword] = useState('')
    const [name,setName] = useState('')

    const processRegister = ()=>{
        axios.post('http://localhost:8080/auth/register',{
            "email":email,
            "password":password,
            "name":name
        })
        .then(response=>console.log(response))
        .catch(error=>console.log(error))
    }
    return(
        <Container maxWidth={false}>
            <Paper elevation={3} sx={{width:'40vw',height:'50vh',display:'flex',flexDirection:'column',justifyContent:'space-evenly',borderRadius:5,alignItems:'center'}}>
                <Typography sx={{fontFamily:'inter',fontWeight:600,fontSize:32,color:'#db0000'}}>MY MOVIES LISTS</Typography>
                <Box sx={{width:'100%',height:'50%',display:'flex',flexDirection:'column',justifyContent:'space-evenly',alignItems:'center'}}>
                    <TextField required onChange={(e)=>setEmail(e.target.value)} label="Email" variant="outlined" sx={{width:'75%'}}/>
                    <TextField required onChange={(e)=>setPassword(e.target.value)} label="Password" type='password' variant="outlined" sx={{width:'75%'}}/>
                    <TextField required onChange={(e)=>setName(e.target.value)} label="Name" variant="outlined" sx={{width:'75%'}}/>
                </Box>
                <Button variant='contained' onClick={processRegister} sx={{backgroundColor:'#db0000', width: 'auto'}}>Register</Button>
                <Link href='/' underline='none' sx={{color:'#db0000'}}>Have an account? Click to login</Link>
            </Paper>
        </Container>
    )
}

export default Register;