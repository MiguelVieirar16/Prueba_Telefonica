const express = require('express'); //importamos express
const router = express.Router(); //creamos un router de express
const userController = require('../controllers/userController');

//ruta para validar al usuario
router.post('/validate', userController.validateUser);

//ruta para verificar el código
router.post('/verify-code', userController.verifyCode);

//ruta para obtener los detalles del usuario por documento de identidad
router.get('/:documento_identidad/details', userController.getUserDetails);

module.exports = router; //exportamos el router para usarlo en otros archivos
