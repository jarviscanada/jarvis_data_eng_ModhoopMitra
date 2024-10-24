const seedQuotes = require('./quote-seeds');
const seedTraders = require('./trader-seeds');
const seedTransactions = require('./transactions-seeds');

const sequelize = require('../config/connection');

const seedAll =  async () => {
  await sequelize.sync({ force: true})
  console.log('-------------')

  await seedQuotes()
  console.log('-------------')
  await seedTraders()
  console.log('-------------')
  await seedTransactions()
  console.log('-------------')
  process.exit(0)
}

seedAll();