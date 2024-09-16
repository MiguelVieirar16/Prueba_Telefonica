const { Sequelize } = require('sequelize');

// inicializa la conexión con la base de datos 'usuarios_saldos_telefonica'
const db = new Sequelize('usuarios_saldos_telefonica', 'root', 'your_password', {
    host: 'localhost', //especifica el host de la base de datos
    dialect: 'mysql' //define el tipo de base de datos que se está utilizando
});

// autentica la conexión con la base de datos
db.authenticate()
  .then(() => console.log('Conectado a la base de datos')) //si la conexión es exitosa, muestra este mensaje
  .catch(err => console.log('Error: ' + err)); //si hay un error, muestra el mensaje de error

module.exports = db; // exporta la conexión para usarla en otros archivos