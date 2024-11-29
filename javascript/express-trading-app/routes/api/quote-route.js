const router = require('express').Router();
const sequelize = require('../../config/connection');
const { Quote } = require('../../models');

// get a list of all quotes
router.get('/dailyList', (req, res)  => {
  
  Quote.findAll({
    order: [['created_at', 'DESC']],
  })
    .then(data => res.json(data))
    .catch(err => {
      console.log(err)
      res.status(500).json(err)
    })
})

// given a quote id (a ticker), 
// return the quote if it exists
router.get('/:quoteId', (req, res) => {
  sequelize.query(
    'SELECT * FROM quote WHERE ticker = :quoteId', // : is required
    {
      replacements: { quoteId: req.params.quoteId},
      type: sequelize.QueryTypes.SELECT
    }
  )
    .then(data => res.json(data[0])) // .query returns array
    .catch(err => {
      console.log(err)
      res.status(500).json(err)
    })
})

module.exports = router;