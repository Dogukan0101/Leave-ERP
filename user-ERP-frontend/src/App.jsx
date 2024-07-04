import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { Route, RouterProvider, createBrowserRouter, createRoutesFromElements } from 'react-router-dom'
import Users from './pages/Users'
import { SideBar } from './components/SideBar'

const router = createBrowserRouter(
  createRoutesFromElements(<Route path='/employees' element={<Users/>}/>)
);

const App = () => {
  return <RouterProvider router={router}/>
}

export default App
