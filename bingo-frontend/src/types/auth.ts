export interface UserRegistration {
  name: string;          
  email: string;
  password: string;        
  confirmPassword: string; 
}

export interface LoginResponse {
  accessToken: string;
  refreshToken: string;
}