import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import ApiService from '../../services/ApiService';
import LanguageSelector from '../common/LanguageSelector'; 

const Login = () => {
  const { t } = useTranslation(); 
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleLogin = async () => {
    if (!email || !password) {
      setError(t('auth.error_fields')); 
      return;
    }

    setError('');
    setLoading(true);

    try {
      const response = await ApiService.loginUser(email, password);
      const { accessToken, refreshToken } = response;

      localStorage.setItem('accessToken', accessToken);
      localStorage.setItem('refreshToken', refreshToken);

      navigate('/home');
    } catch (err: any) {
      const status = err.response?.status;
      if (status === 401) {
        setError(t('auth.error_invalid') || 'Invalid email or password.');
      } else if (status === 500) {
        setError(t('auth.error_server') || 'Internal Server Error.');
      } else {
        setError(t('auth.error_connection') || 'Could not connect to the server.');
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex flex-col justify-center items-center min-h-screen">
      <div className="mb-4">
        <LanguageSelector />
      </div>
      <div className="bg-slate-900 p-8 rounded-2xl shadow-2xl border border-cyan-500/30 w-full max-w-sm backdrop-blur-sm">
        <h1 className="text-4xl font-black text-center mb-8 bg-gradient-to-r from-cyan-400 to-blue-500 bg-clip-text text-transparent italic">
          {t('auth.login_title')}
        </h1>

        <div className="space-y-4">
          <input
            type="email"
            placeholder={t('auth.email')} 
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="w-full p-4 bg-slate-800 border border-slate-700 rounded-xl focus:outline-none focus:ring-2 focus:ring-cyan-500 transition-all placeholder:text-slate-500"
          />

          <input
            type="password"
            placeholder={t('auth.password')}
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="w-full p-4 bg-slate-800 border border-slate-700 rounded-xl focus:outline-none focus:ring-2 focus:ring-cyan-500 transition-all placeholder:text-slate-500"
          />
        </div>

        {error && (
          <div className="text-red-400 text-sm mt-4 text-center font-medium animate-pulse">
            {error}
          </div>
        )}

        <button
          onClick={handleLogin}
          disabled={loading}
          className={`w-full mt-8 p-4 bg-cyan-600 text-white font-bold rounded-xl shadow-lg transition-all ${
            loading ? 'opacity-50 cursor-not-allowed' : 'hover:bg-cyan-500 hover:-translate-y-1'
          }`}
        >
          {loading ? t('auth.logging_in') : t('auth.login_btn')}
        </button>

        <p className="mt-6 text-center text-slate-400 text-sm">
          {t('auth.no_account')}{' '}
          <Link to="/register" className="text-cyan-400 hover:underline font-bold">
            {t('auth.register_link')}
          </Link>
        </p>
      </div>
    </div>
  );
};

export default Login;