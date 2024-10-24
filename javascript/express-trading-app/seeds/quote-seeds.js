const { Quote } = require('../models')

const quoteData = [
  {
    ticker: 'MEOW',
    last_price: 12.2,
    bid_price: 12.3,
    bid_size: 20,
    ask_price: 12.1,
    ask_size: 15
  },
  {
    ticker: 'WOOF',
    last_price: 2.3,
    bid_price: 14,
    bid_size: 4,
    ask_price: 12,
    ask_size: 6
  },
]

const seedQuotes = () => Quote.bulkCreate(quoteData)

module.exports = seedQuotes