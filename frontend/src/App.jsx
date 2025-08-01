import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import {BrowserRouter,Routes,Route} from 'react-router-dom'
import Home from './screens/Home'
import Lists from './screens/Lists'
import LoginRegistration from './screens/LoginRegistration'

/**
 * Contains the routing to the screens 
 * @returns routing configuration to screens of app
 */
function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<LoginRegistration/>}></Route>
        <Route path='/home' element={<Home/>}></Route>
        <Route path='/lists' element={<Lists/>}></Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App
