import './App.css'
import { Route, RouterProvider, createBrowserRouter, createRoutesFromElements } from 'react-router-dom'
import { Employees } from './pages/Employees'
import Leaves from './pages/Leaves'
import MainLayout from './layouts/MainLayout';
import { Departments } from './pages/Departments';

const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path='/' element={<MainLayout/>}>  
      <Route path='/employees' element={<Employees/>}/>
      <Route path='/leaves' element={<Leaves/>}/>
      <Route path='/departments' element={<Departments/>}/>
    </Route>
)
);

const App = () => {
  return <RouterProvider router={router}/>
}

export default App
