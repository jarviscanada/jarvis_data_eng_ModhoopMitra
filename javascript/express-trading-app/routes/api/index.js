const router = require('express').Router();

const traderRoutes = require('./trader-route');
const quoteRoutes = require('./quote-route');
const accountRoute = require('./account-route');

router.use('/traders', traderRoutes);
router.use('/quote', quoteRoutes);
router.use('/account', accountRoute);

module.exports = router;