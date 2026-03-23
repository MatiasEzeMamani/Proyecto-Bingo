import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import ApiService from '../../services/ApiService';
import type { UserRegistration } from '../../types/auth';
import LanguageSelector from '../common/LanguageSelector';

const Register = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();

  const [formData, setFormData] = useState<UserRegistration>({
    name: '',
    email: '',
    password: '',
    confirmPassword: ''
  });
  
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleRegister = async () => {
    const { name, email, password, confirmPassword } = formData;

    if (!name || !email || !password || !confirmPassword) {
      setError(t('auth.error_fields'));
      return;
    }

    if (password !== confirmPassword) {
      setError(t('auth.error_match'));
      return;
    }

    setError('');
    setLoading(true);

    try {
      await ApiService.registerUser({ name, email, password, confirmPassword });
      navigate('/login');
    } catch (err: any) {
      setError(err.response?.data?.message || t('auth.error_register'));
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex flex-col justify-center items-center min-h-screen bg-slate-950 p-4">
      <div className="mb-6">
        <LanguageSelector />
      </div>

      <div className="bg-slate-900 p-8 rounded-2xl shadow-2xl border border-cyan-500/30 w-full max-w-sm">
        <h2 className="text-3xl font-black text-center mb-6 text-white italic tracking-tighter">
          {t('auth.register_title')}
        </h2>

        <div className="space-y-4">
          <input
            type="text"
            name="name"
            placeholder={t('auth.full_name')}
            value={formData.name}
            onChange={handleChange}
            className="w-full p-4 bg-slate-800 border border-slate-700 rounded-xl focus:ring-2 focus:ring-cyan-500 outline-none text-white transition-all placeholder:text-slate-500"
          />
          
          <input
            type="email"
            name="email"
            placeholder={t('auth.email')}
            value={formData.email}
            onChange={handleChange}
            className="w-full p-4 bg-slate-800 border border-slate-700 rounded-xl focus:ring-2 focus:ring-cyan-500 outline-none text-white transition-all placeholder:text-slate-500"
          />

          <input
            type="password"
            name="password"
            placeholder={t('auth.password')}
            value={formData.password}
            onChange={handleChange}
            className="w-full p-4 bg-slate-800 border border-slate-700 rounded-xl focus:ring-2 focus:ring-cyan-500 outline-none text-white transition-all placeholder:text-slate-500"
          />

          <input
            type="password"
            name="confirmPassword"
            placeholder={t('auth.confirm_password')}
            value={formData.confirmPassword}
            onChange={handleChange}
            className="w-full p-4 bg-slate-800 border border-slate-700 rounded-xl focus:ring-2 focus:ring-cyan-500 outline-none text-white transition-all placeholder:text-slate-500"
          />
        </div>

        {error && (
          <div className="text-red-400 text-sm mt-4 text-center font-medium animate-pulse">
            {error}
          </div>
        )}

        <button
          onClick={handleRegister}
          disabled={loading}
          className={`w-full mt-8 p-4 bg-blue-600 text-white font-bold rounded-xl shadow-lg transition-all transform ${
            loading ? 'opacity-50 cursor-not-allowed' : 'hover:bg-blue-500 hover:-translate-y-1 active:scale-95'
          }`}
        >
          {loading ? t('auth.registering') : t('auth.register_btn')}
        </button>

        <p className="mt-6 text-center text-slate-400 text-sm">
          {t('auth.have_account')}{' '}
          <Link to="/login" className="text-cyan-400 font-bold hover:underline">
            {t('auth.login_link')}
          </Link>
        </p>
      </div>
    </div>
  );
};

export default Register;