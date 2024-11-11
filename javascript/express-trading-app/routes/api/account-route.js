const router = require('express').Router()
const sequelize = require('../../config/connection')
const { AccountTransaction } = require('../../models')

// takes a traderId as a query param and 
// returns all acount_transactions for the specified trader
router.get('/transactionHistory/:traderId', (req, res)  => {
  AccountTransaction.findAll({
    where: {trader_id: req.params.traderId},
    attributes: { exclude: ['traderId'] }
  })
    .then(data => res.json(data))
    .catch(err => {
      console.log(err)
      res.status(500).json(err)
    })
})

// takes a traderId as a query param and 
// returns the specified traders account balance 
router.get('/accountBalance/:traderId', (req, res) => {
  sequelize.query(
    'SELECT * FROM transactions WHERE trader_id = :traderId',
    {
      replacements: {traderId: req.params.traderId},
      type: sequelize.QueryTypes.SELECT
    }
  )
    .then(data => {
      console.log(data)
      let bal = 0;
      data.forEach(transaction => {
        bal += transaction.amount
      })
      res.json({
        trader_id: req.params.traderId,
        total_amount: bal
      })
    })
})

// takes a traderId as a query param and an ‘amount’ in the body of the request and 
// returns the newly created account_transaction. 
// Note that the ‘amount’ must be a positive number 
router.post('/deposit/:traderId', (req, res) => {
  AccountTransaction.create({
    id: req.body.id,
    trader_id: req.params.traderId,
    amount: req.body.amount,
  })
    .then(data => {
      const transaction = data.toJSON()
      delete transaction.traderId
      res.status(201).json(transaction)
    })
    .catch(err => {
      console.log(err, req.body)
      res.status(500).json(err)
    })
})

// takes a traderId as a query param and an ‘amount’ in the body of the request and 
// returns the newly created account_transaction. 
// Note that the ‘amount’ must be a negative number and 
// cannot result in a trader having a negative account balance. 
router.post('/withdraw/:traderId', async (req, res) => {
  try {
    const transactionList = await sequelize.query(
      'SELECT * FROM transactions WHERE trader_id = :traderId',
      {
        replacements: {traderId: req.params.traderId},
        type: sequelize.QueryTypes.SELECT
      }
    )
    
    let bal = 0;
    transactionList.forEach(transaction => {
      bal += transaction.amount
    })
    
    if((req.body.amount + bal) < 0){
      return res.status(400).json({message: 'Insufficient funds'})
    }
    
    const transactionData = { 
      id: req.body.id,
      amount: req.body.amount,
      trader_id: req.params.traderId,
      created_at: new Date(),
      updated_at: new Date()
    }

    await sequelize.query(
      `INSERT INTO Transactions (id, amount, trader_id, created_at, updated_at) VALUES (:id, :amount, :trader_id, :created_at, :updated_at)`,
      {
        replacements: transactionData
      }
    )

    res.status(201).json(transactionData)

  } catch (err) {
    console.log(err)
    res.status(500).json(err)
  }
})

module.exports = router;