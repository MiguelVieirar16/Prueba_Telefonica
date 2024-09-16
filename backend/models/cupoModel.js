const { Sequelize, DataTypes } = require('sequelize');//importamos Sequelize y DataTypes
const db = require('../config/db');//importamos la configuración de la base de datos
const User = require('./userModel');//importamos el modelo de usuario

const Cupo = db.define('Cupo', {
  id: {
    type: DataTypes.INTEGER,
    autoIncrement: true,
    primaryKey: true
  },
  numero_movil: {
    type: DataTypes.STRING,
    allowNull: false,
    references: {
      model: User,
      key: 'numero_movil'
    }
  },
  saldo: {
    type: DataTypes.STRING(20),
    allowNull: false
  },
  datos: {
    type: DataTypes.STRING(20),
    allowNull: false
  },
  plataforma: {
    type: DataTypes.ENUM('prepago', 'postpago'),
    allowNull: false
  },
  documento_identidad: {
    type: DataTypes.STRING,
    allowNull: false
  },
  plan: {
    type: DataTypes.STRING,
    allowNull: false
  }
}, {
  tableName: 'Cupos',
  timestamps: false
});

// Definir la relación
Cupo.belongsTo(User, { foreignKey: 'documento_identidad', targetKey: 'documento_identidad' });

module.exports = Cupo;