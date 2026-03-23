import axiosInstance from '../api/AxiosInstance';

const ApiService = {
  loginUser: async (email: string, password: string) => {
    const response = await axiosInstance.post('/auth/login', { email, password });
    return response.data;
  },

  registerUser: async (userData: any) => {
    const response = await axiosInstance.post('/auth/register', userData);
    return response.data;
  },

  getUserProfile: async () => {
    const response = await axiosInstance.get('/users/profile');
    return response.data;
  },

};

export default ApiService;