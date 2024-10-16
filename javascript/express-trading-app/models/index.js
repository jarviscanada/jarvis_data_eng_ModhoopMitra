const Quote = require('./Quote');
const Trader = require('./Trader');
const AccountTransaction = require('./AccountTransaction');

Trader.hasMany(AccountTransaction);
AccountTransaction.belongsTo(Trader);


module.exports = { Quote, Trader, AccountTransaction };