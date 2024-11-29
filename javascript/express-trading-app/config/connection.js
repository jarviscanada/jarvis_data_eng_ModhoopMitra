const Sequelize = require('sequelize');

require('dotenv').config();

let sequelize;
sequelize = new Sequelize(
  process.env.DB_NAME, 
  process.env.DB_USER, 
  process.env.DB_PASSWORD, {
    host: 'trading-mysql',//process.env.DB_HOST || 'host.docker.internal',//host: '127.0.0.1',
    dialect: 'mysql',
    port: 3306,
  }
)

module.exports = sequelize;