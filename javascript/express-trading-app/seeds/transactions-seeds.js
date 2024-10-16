const { AccountTransaction } = require('../models')

const transactionData = [
  {
    "id": 1,
    "amount": 200,
    "trader_id": 1,
  },
  {
    "id": 2,
    "amount": -200,
    "trader_id": 1,
  },
  {
    "id": 3,
    "amount": 100,
    "trader_id": 2,
  },
]

const seedTransactions = () => AccountTransaction.bulkCreate(transactionData)

module.exports = seedTransactions