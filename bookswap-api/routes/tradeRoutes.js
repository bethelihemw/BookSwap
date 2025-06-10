const express = require('express');
const router = express.Router();
const tradeController = require('../controllers/tradeController');
const authMiddleware = require('../middleware/authMiddleware');
const apiLimiter = require('../middleware/rateLimit');

router.use(apiLimiter);

router.post('/trades', authMiddleware, tradeController.initiateTrade);
router.get('/trades', authMiddleware, tradeController.getUserTrades);
router.get('/trades/:id', authMiddleware, tradeController.getSingleTrade);
router.put('/trades/:id', authMiddleware, tradeController.respondToTrade);
router.delete('/trades/:id', authMiddleware, tradeController.cancelTrade);
router.put('/trades/:id/complete', authMiddleware, tradeController.completeTrade);

module.exports = router;