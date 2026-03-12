# 🎰 Proyecto Bingo Full-Stack

Este es un sistema de Bingo profesional desarrollado con **Java (Spring Boot)**, **React** y **MySQL**. El proyecto está completamente **dockerizado**, lo que permite levantarlo en cualquier máquina sin necesidad de instalar Java, Node o bases de datos manualmente.

## 🚀 Instalación Rápida (Con Docker)

Esta es la forma recomendada. Solo necesitás tener instalado **Docker Desktop**.

1. **Clonar el repositorio:**

```bash
git clone https://github.com/MatiasEzeMamani/Proyecto-Bingo.git
cd Proyecto-Bingo
```
   
### 🚀 Levantar el sistema completo
Desde la raíz del proyecto, ejecutá:

```bash
docker compose up -d --build
```


### 🔗 Acceder a las aplicaciones

* **Frontend (React):** [http://localhost:5173](http://localhost:5173)
* **Backend (API):** [http://localhost:8080](http://localhost:8080)
* **Base de Datos (MySQL):** Puerto `3306`

### 🛠️ Tecnologías y Arquitectura


* **Backend:** Java 21 & Spring Boot (corriendo en el contenedor `bingo_backend`).
* **Frontend:** React + Vite (corriendo en el contenedor `bingo_frontend`).
* **Database:** MySQL 8.0 (corriendo en el contenedor `bingo_db`).

## 🛠️ Stack Tecnológico Detallado

### **Backend (Core & Security)**
* **Lenguaje:** Java 21 (LTS)
* **Framework:** Spring Boot 4.0.3
* **Seguridad Avanzada:** - **Spring Security** con arquitectura Stateless.
  - **Doble Token:** Implementación de **Access Tokens** y **Refresh Tokens** con **JWT**.
  - Configuración de políticas **CORS** para comunicación segura con el Frontend.
* **Productividad:** **Lombok**.
* **Validaciones:** Bean Validation (**Hibernate Validator**) para integridad de datos en DTOs.
* **Mapeo:** **MapStruct** para una conversión eficiente entre Entidades y DTOs.
* **Persistencia:** Spring Data JPA + Hibernate.

### **Frontend & Multimedia**
* **Framework:** React 18+ (Vite)
* **Lenguaje:** **TypeScript**.
* **Gestión de Medios:** Integración con **Cloudinary API** para la gestión y almacenamiento de imágenes.
* **Comunicación:** **Axios** para consumo de servicios REST.

### **Infraestructura y Herramientas**
* **Base de Datos:** **MySQL 8.0**.
* **DevOps:** **Docker & Docker Compose** (Sistema totalmente dockerizado).
* **API Testing:** **Postman** para validación de endpoints y flujos de autenticación.

### 🛠️ Desarrollo Manual (Opcional)


Si preferís trabajar sin Docker para debuggear el código directamente:

### **Backend**
* Requiere **JDK 21** y **Maven**.

* Configurar el `application.properties` para apuntar a `localhost:3306`.

### **Frontend**
* Requiere **Node.js 22+**.
* Ejecutar `npm install` y luego `npm run dev`.

---




### 📩 Contacto
**Desarrollado por Matias Ezequiel Mamaní** [LinkedIn Profile](https://www.linkedin.com/in/matias-ezequiel-mamaní-66640a1b7)
