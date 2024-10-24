const sequelize = require('../../config/connection')
const { Trader } = require('../../models')

const router = require('express').Router()

// return a list of all traders 
router.get('/', (req, res)  => {
  Trader.findAll({
    order: [['id']]
  })
    .then(data => res.json(data))
    .catch(err => {
      console.log(err)
      res.status(500).json(err)
    })
})

// given a trader in the body of the request, 
// create a new trader and return the newly created trader 
router.post('/', (req, res) => {
  Trader.create(req.body)
    .then(data => res.status(201).json(data))
    .catch(err => {
      console.log(err, req.body)
      res.status(500).json(err)
    })
})

// given a traderId as a query param, 
// delete the trader in the db and return the deleted trader
router.delete('/:traderId', async (req, res) => {
  try {
    const trader = await sequelize.query(
      'SELECT * FROM Trader WHERE id = :traderId',
      {
        replacements: {traderId: req.params.traderId},
        type: sequelize.QueryTypes.SELECT
      }
    )

    if (!trader) {
      return res.status(404).json({message: `Trader ${req.params.traderId} not found`})
    }

    await sequelize.query(
      'DELETE FROM Trader WHERE id = :traderId',
      {
        replacements: {traderId: req.params.traderId},
        type: sequelize.QueryTypes.DELETE
      }
    )

    res.json(trader[0])

  } catch (err) {
    console.log(err)
    res.status(500).json(err)
  }
})

module.exports = router;