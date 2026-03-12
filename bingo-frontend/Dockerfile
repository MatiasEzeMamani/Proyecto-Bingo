# Cambiamos de node:18-alpine a node:22-alpine
FROM node:22-alpine

WORKDIR /app

# Copiamos archivos de dependencias
COPY package*.json ./

# Instalamos dependencias
RUN npm install

# Copiamos el resto del código
COPY . .

# Exponemos el puerto de Vite
EXPOSE 5173

# Ejecutamos con host para que sea accesible desde Windows
CMD ["npm", "run", "dev", "--", "--host"]