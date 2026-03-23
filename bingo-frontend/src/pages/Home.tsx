import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import LanguageSelector from '../components/common/LanguageSelector';

const Home = () => {
    const { t } = useTranslation(); 
    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        navigate('/login');
    };

    return (
        <div className="max-w-6xl mx-auto p-6">
            <header className="flex justify-between items-center mb-10 border-b border-slate-800 pb-6">
                <div>
                    <h1 className="text-3xl font-black bg-gradient-to-r from-cyan-400 to-blue-500 bg-clip-text text-transparent">
                        {t('home.title')} 
                    </h1>
                    <p className="text-slate-400 text-sm">
                        {t('home.welcome', { name: 'Matias' })}
                    </p>
                </div>
                
                <div className="flex items-center gap-4">
                    <LanguageSelector />
                    <button
                        onClick={handleLogout}
                        className="px-4 py-2 border border-red-500/50 text-red-400 rounded-lg hover:bg-red-500/10 transition-all text-sm font-bold"
                    >
                        {t('home.logout')}
                    </button>
                </div>
            </header>

            <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
                <div className="md:col-span-1 bg-slate-900/50 p-6 rounded-2xl border border-cyan-500/20">
                    <h2 className="text-xl font-bold mb-4">{t('home.new_game')}</h2>
                    <button
                        className="w-full py-4 bg-cyan-600 hover:bg-cyan-500 text-white rounded-xl font-black shadow-lg transition-all transform hover:scale-105"
                    >
                        {t('home.create_room')}
                    </button>
                    <p className="mt-4 text-xs text-slate-500 text-center italic">
                        {t('home.create_room_hint')}
                    </p>
                </div>

                <div className="md:col-span-2 space-y-4">
                    <h2 className="text-xl font-bold flex items-center gap-2">
                        {t('home.active_rooms')} 
                        <span className="text-xs bg-green-500/20 text-green-400 px-2 py-1 rounded-full animate-pulse">
                            {t('home.live')}
                        </span>
                    </h2>

                    <div className="bg-slate-900 p-5 rounded-xl border border-slate-800 flex justify-between items-center hover:border-cyan-500/50 transition-colors">
                        <div>
                            <h3 className="font-bold text-lg text-slate-100">The Boys Room 🎱</h3>
                            <p className="text-sm text-slate-400">
                                {t('home.players_count', { current: 5, max: 20 })}
                            </p>
                        </div>
                        <button
                            onClick={() => navigate('/game')}
                            className="px-6 py-2 bg-slate-800 hover:bg-slate-700 text-cyan-400 rounded-lg font-bold border border-cyan-500/30"
                        >
                            {t('home.join')}
                        </button>
                    </div>

                    <div className="text-center py-10 text-slate-600 italic">
                        {t('home.searching')}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Home;