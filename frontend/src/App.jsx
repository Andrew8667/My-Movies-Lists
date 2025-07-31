import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import {BrowserRouter,Routes,Route} from 'react-router-dom'
import Login from './screens/Login'
import Register from './screens/Register'
import Home from './screens/Home'
import Lists from './screens/Lists'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Login/>}></Route>
        <Route path='/register' element={<Register/>}></Route>
        <Route path='/home' element={<Home/>}></Route>
        <Route path='/lists' element={<Lists/>}></Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App
