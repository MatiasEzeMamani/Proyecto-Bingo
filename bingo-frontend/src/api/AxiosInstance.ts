import axios from 'axios';

const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080', // La URL de tu Spring Boot
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json',
    },
});

// Esto será clave cuando implementes el JWT
axiosInstance.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export default axiosInstance;