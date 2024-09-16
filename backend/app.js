const express = require('express');
const cors = require('cors'); // Importar el middleware de CORS
const app = express();
const userRoutes = require('./routes/userRoutes');
const db = require('./config/db');

// Configurar CORS
const corsOptions = {
    origin: '*', // Reemplaza con la URL de tu aplicación
    optionsSuccessStatus: 200 // Para navegadores legacy como IE11
};
app.use(cors(corsOptions));

// Middleware para parsear JSON
app.use(express.json());

// Rutas
app.use('/api/users', userRoutes);

// Conectar a la base de datos
db.authenticate()
  .then(() => console.log('Conectado a la base de datos'))
  .catch(err => console.log('Error: ' + err));

// Iniciar el servidor
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Servidor corriendo en el puerto ${PORT}`);
});