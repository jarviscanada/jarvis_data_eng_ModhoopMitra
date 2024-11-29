const { Model, DataTypes } = require('sequelize');
const sequelize = require('../config/connection');

class Quote extends Model {}

Quote.init(
  {
    ticker: {
      type: DataTypes.STRING,
      allowNull: false,
      primaryKey: true,
    },
    last_price: {
      type: DataTypes.DOUBLE,
      allowNull: false
    },
    bid_price: {
      type: DataTypes.DOUBLE,
      allowNull: false
    },
    bid_size: {
      type: DataTypes.DOUBLE,
      allowNull: false
    },
    ask_size: {
      type: DataTypes.DOUBLE,
      allowNull: false
    },
    ask_price: {
      type: DataTypes.DOUBLE,
      allowNull: false
    }
  },
  {
    sequelize,
    freezeTableName: true,
    underscored: true,
    modelName: 'quote'
  }
)

module.exports = Quote