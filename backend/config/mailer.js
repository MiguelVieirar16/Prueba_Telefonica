const nodemailer = require('nodemailer'); //importamos nodemailer

//configuramos el transporte de nodemailer
const transporter = nodemailer.createTransport({
    host: 'smtp.gmail.com', //servidor SMTP de Gmail
    port: 587, //puerto para TLS
    secure: true, //true para 465, false para otros puertos
    auth: {
        user: 'pruebatelefonica16@gmail.com', //correo electrónico del remitente
        pass: 'ma16012001' //contraseña del remitente
    },
    tls: {
        ciphers: 'SSLv3', //cifrado SSLv3
        rejectUnauthorized: false //permite certificados no autorizados
    }
});

//función para enviar el código de verificación
const sendCode = (mail, code) => {
    const mailOptions = {
        from: 'pruebatelefonica16@gmail.com', //correo del remitente
        to: mail, //correo del destinatario
        subject: 'Código de verificación', //asunto del correo
        text: `Tu código de verificación es: ${code}` //cuerpo del correo
    };

    //enviamos el correo
    transporter.sendMail(mailOptions, (error, info) => {
        if (error) {
            console.error('Error al enviar el correo:', error); //muestra el error si ocurre
        } else {
            console.log('Correo enviado:', info.response); //muestra la respuesta si se envía correctamente
        }
    });
};

module.exports = sendCode; //exportamos la función para usarla en otros archivos
