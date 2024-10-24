const { Model, DataTypes } = require('sequelize');
const sequelize = require('../config/connection');

class AccountTransaction extends Model {}

AccountTransaction.init(
  {
    id: {
      type: DataTypes.INTEGER,
      allowNull: false,
      primaryKey: true,
    },
    amount: {
      type: DataTypes.INTEGER,
      allowNull: false
    },
    trader_id: {
      type: DataTypes.INTEGER,
      allowNull: false,
      references: {
        model: 'Trader',
        key: 'id',
      }
    },
  },
  {
    sequelize,
    freezeTableName: true,
    underscored: true,
    modelName: 'transactions'
  }
)

module.exports = AccountTransaction