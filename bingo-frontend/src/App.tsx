import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/auth/Login';
import Register from './components/auth/Register';
import Home from './pages/Home';
import SalaDeJuego from './pages/SalaDeJuego';
import ProtectedRoute from './components/auth/ProtectedRoute';

function App() {
  return (
    <Router>
      <div className="min-h-screen bg-slate-950 text-slate-100 selection:bg-cyan-500 selection:text-white">

        <Routes>

            {/* Rutas Públicas */}
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/" element={<Navigate to="/login" />} />

            {/* Rutas Privadas */}
            <Route element={<ProtectedRoute />}>
              <Route path="/home" element={<Home />} />
              <Route path="/juego" element={<SalaDeJuego />} />
            </Route>

          {/* 404 - Por si se pierden en el local */}
          <Route path="*" element={
            <div className="flex flex-col items-center justify-center h-screen">
              <h1 className="text-6xl font-black text-cyan-500">404</h1>
              <p className="text-xl mt-4">¿Te perdiste, Matías? Acá no hay cartones.</p>
              <a href="/login" className="mt-6 px-4 py-2 bg-cyan-600 rounded-lg hover:bg-cyan-500 transition-colors"> Volver al inicio </a>
            </div>
          } />
        </Routes>

      </div>
    </Router>
  );
}

export default App;