import { useTranslation } from 'react-i18next';

const LanguageSelector = () => {
  const { i18n } = useTranslation();

  const changeLanguage = (lng: string) => {
    i18n.changeLanguage(lng);
  };

  return (
    <div className="flex gap-2">
      <button 
        onClick={() => changeLanguage('es')}
        className={`px-2 py-1 text-xs font-bold rounded ${i18n.language.startsWith('es') ? 'bg-cyan-600' : 'bg-slate-800'}`}
      >
        ES
      </button>
      <button 
        onClick={() => changeLanguage('en')}
        className={`px-2 py-1 text-xs font-bold rounded ${i18n.language.startsWith('en') ? 'bg-cyan-600' : 'bg-slate-800'}`}
      >
        EN
      </button>
    </div>
  );
};

export default LanguageSelector;