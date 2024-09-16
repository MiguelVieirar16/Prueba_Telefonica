const mysql = require('mysql2/promise'); //importamos mysql2 con soporte para promesas
const User = require('../models/userModel'); //importamos el modelo de usuario
const Cupo = require('../models/cupoModel'); //importamos el modelo de cupo
const crypto = require('crypto'); //importamos el módulo crypto para generar códigos
const sendCode = require('../config/mailer'); //importamos la función para enviar correos

let connection; //variable para la conexión a la base de datos

async function initializeConnection() {
    if (!connection) {
        connection = await mysql.createConnection({host: 'localhost', user: 'root',  password: 'your_password', database: 'usuarios_saldos_telefonica'}); //inicializamos la conexión si no existe
    }
}

async function saveCodeToMySQL(userId, code) {
    await initializeConnection();
    const expiration = new Date(Date.now() + 5 * 60 * 1000); //5 minutos de expiración
    //elimina los códigos anteriores
    await connection.execute(
        'DELETE FROM verification_codes WHERE user_id = ?',
        [userId]
    );
    //guarda el nuevo código
    await connection.execute(
        'INSERT INTO verification_codes (user_id, code, expiration) VALUES (?, ?, ?)',
        [userId, code, expiration]
    );
}

async function getCodeFromMySQL(userId) {
    await initializeConnection();
    const [rows] = await connection.execute(
        'SELECT code FROM verification_codes WHERE user_id = ? AND expiration > NOW() ORDER BY expiration DESC LIMIT 1', //selecciona el código más reciente que no haya expirado
        [userId]
    );
    return rows.length > 0 ? rows[0].code : null; //devuelve el código si existe
}

exports.validateUser = async (req, res) => {
    try {
        const { documento_identidad, numero_movil } = req.body; //obtenemos los datos del cuerpo de la solicitud
        const user = await User.findOne({ where: { documento_identidad, numero_movil } }); //buscamos el usuario en la base de datos
        if (user) {
            const code = crypto.randomBytes(3).toString('hex'); //genera un código de 6 caracteres
            await saveCodeToMySQL(user.id, code); //guarda el código en la base de datos
            sendCode(user.mail, code); //envía el código por correo
            res.json({ valid: true, message: 'Código de verificación enviado' }); //respuesta exitosa
        } else {
            res.json({ valid: false }); //usuario no encontrado
        }
    } catch (error) {
        console.error('Error en validateUser:', error); //log de error
        res.status(500).json({ message: 'Error del servidor', error }); //respuesta de error del servidor
    }
};

exports.verifyCode = async (req, res) => {
    try {
        const { documento_identidad, code } = req.body; //obtenemos los datos del cuerpo de la solicitud
        const user = await User.findOne({ where: { documento_identidad } }); //buscamos el usuario en la base de datos
        if (user) {
            const storedCode = await getCodeFromMySQL(user.id); //obtenemos el código almacenado
            if (code === storedCode) {
                res.json({ valid: true, message: 'Código correcto. Acceso concedido.' }); //código correcto
            } else {
                res.json({ valid: false, message: 'Código incorrecto. Inténtalo de nuevo.' }); //código incorrecto
            }
        } else {
            res.json({ valid: false, message: 'Usuario no encontrado.' }); //usuario no encontrado
        }
    } catch (error) {
        res.status(500).json({ message: 'Error del servidor', error }); //respuesta de error del servidor
    }
};

exports.getUserCupos = async (req, res) => {
    try {
        const { documento_identidad } = req.params; //obtenemos el documento de identidad de los parámetros de la solicitud
        const user = await User.findOne({ where: { documento_identidad } }); //buscamos el usuario en la base de datos
        if (user) {
            const cupos = await Cupo.findAll({ where: { documento_identidad } }); //buscamos los cupos del usuario
            res.json(cupos); //devolvemos los cupos
        } else {
            res.status(404).json({ message: 'Usuario no encontrado' }); //usuario no encontrado
        }
    } catch (error) {
        res.status(500).json({ message: 'Error del servidor', error }); //respuesta de error del servidor
    }
};

exports.getUserDetails = async (req, res) => {
    try {
        const { documento_identidad } = req.params; //obtenemos el documento de identidad de los parámetros de la solicitud
        const user = await User.findOne({ where: { documento_identidad } }); //buscamos el usuario en la base de datos
        if (user) {
            const cupos = await Cupo.findAll({ where: { documento_identidad } }); //buscamos todos los cupos asociados al documento de identidad
            const telefonos = await Promise.all(cupos.map(async (c) => {
                const phoneDetails = await Cupo.findOne({ where: { numero_movil: c.numero_movil } }); //buscamos los detalles del teléfono
                return {
                    numeroMovil: c.numero_movil, //número de número
                    plataforma: c.plataforma, //plataforma del número
                    saldo: phoneDetails ? phoneDetails.saldo : null, //saldo del número
                    datosRestantes: phoneDetails ? phoneDetails.datos : null, //datos restantes del número
                    plan: phoneDetails ? phoneDetails.plan : null //plan del número
                };
            }));
            res.json({
                nombre: user.nombre, //nombre del usuario
                telefonos: telefonos //lista de teléfonos con sus detalles
            });
        } else {
            res.status(404).json({ message: 'Usuario no encontrado' }); //si no se encuentra el usuario, devolvemos un error 404
        }
    } catch (error) {
        res.status(500).json({ message: 'Error del servidor', error }); //si ocurre un error, devolvemos un error 500
    }
};
