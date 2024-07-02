import React from 'react'
import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
  RouterProvider
} from 'react-router-dom'
import { Users } from './pages/Users'
import MainLayout from './layouts/MainLayout'

const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path='/' element={<MainLayout />}>
      <Route index element={<div className='flex flex-row w-full'>dsc</div>} />
    </Route>
  )
)

const App = () => {
  return <RouterProvider router={router}/>
}

export default App;