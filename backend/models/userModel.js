const { Sequelize, DataTypes } = require('sequelize');//importamos Sequelize y DataTypes
const db = require('../config/db');//importamos la configuración de la base de datos

const User = db.define('User', {
  id: {
    type: DataTypes.INTEGER,
    autoIncrement: true,
    primaryKey: true
  },
  nombre: {
    type: DataTypes.STRING,
    allowNull: false
  },
  mail: {
    type: DataTypes.STRING,
    allowNull: false
  },
  numero_movil: {
    type: DataTypes.STRING,
    allowNull: false,
    unique: true,
    validate: {
      is: /^0(424|414)[0-9]{7}$/
    }
  },
  documento_identidad: {
    type: DataTypes.STRING,
    allowNull: false
  }
}, {
  tableName: 'Usuarios',
  timestamps: false
});

module.exports = User;