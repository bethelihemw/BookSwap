const Trade = require('../models/trade');
const Book = require('../models/books');
const User = require('../models/user');

const VALID_STATUSES = ['accepted', 'rejected', 'proposed'];

const initiateTrade = async (req, res) => {
  try {
    const { offeredBookId, requestedBookId, notes } = req.body;
    const requesterId = req.user._id;

    const offeredBook = await Book.findById(offeredBookId);
    const requestedBook = await Book.findById(requestedBookId);

    if (!offeredBook || !requestedBook) {
      return res.status(404).json({ success: false, message: 'One or both books not found' });
    }

    if (offeredBook.owner.toString() !== requesterId.toString()) {
      return res.status(400).json({ success: false, message: 'You cannot offer a book that is not yours' });
    }

    if (requestedBook.owner.toString() === requesterId.toString()) {
      return res.status(400).json({ success: false, message: 'You cannot request a book you already own' });
    }

    const newTrade = new Trade({
      requester: requesterId,
      offeredBook: offeredBookId,
      requestedBook: requestedBookId,
      owner: requestedBook.owner,
      notesFromRequester: notes,
    });

    const savedTrade = await newTrade.save();
    const populatedTrade = await Trade.findById(savedTrade._id)
      .populate('requester', 'name email')
      .populate('owner', 'name email')
      .populate('offeredBook', 'title author coverImage')
      .populate('requestedBook', 'title author coverImage');

    res.status(201).json({ success: true, message: 'Trade initiated successfully', data: populatedTrade });
  } catch (error) {
    console.error('Error initiating trade:', error);
    res.status(500).json({
      success: false,
      message: 'Failed to initiate trade',
      details: process.env.NODE_ENV === 'development' ? error.message : undefined,
    });
  }
};

const getUserTrades = async (req, res) => {
  try {
    const userId = req.user._id;
    const trades = await Trade.find({
      $or: [{ requester: userId }, { owner: userId }],
    })
      .populate('requester', 'name email')
      .populate('owner', 'name email')
      .populate('offeredBook', 'title author coverImage')
      .populate('requestedBook', 'title author coverImage')
      .populate('proposedBookFromOwner', 'title author coverImage');

    res.status(200).json({ success: true, data: trades });
  } catch (error) {
    console.error('Error getting user trades:', error);
    res.status(500).json({
      success: false,
      message: 'Failed to retrieve trades',
      details: process.env.NODE_ENV === 'development' ? error.message : undefined,
    });
  }
};

const getSingleTrade = async (req, res) => {
  try {
    const { id } = req.params;
    const trade = await Trade.findById(id)
      .populate('requester', 'name email')
      .populate('owner', 'name email')
      .populate('offeredBook', 'title author coverImage')
      .populate('requestedBook', 'title author coverImage')
      .populate('proposedBookFromOwner', 'title author coverImage');

    if (!trade) {
      return res.status(404).json({ success: false, message: 'Trade not found' });
    }
    res.status(200).json({ success: true, data: trade });
  } catch (error) {
    console.error('Error getting trade:', error);
    res.status(500).json({
      success: false,
      message: 'Failed to retrieve trade',
      details: process.env.NODE_ENV === 'development' ? error.message : undefined,
    });
  }
};

const respondToTrade = async (req, res) => {
  try {
    const { tradeId } = req.params;
    const { status, proposedBookId, notes } = req.body;
    const ownerId = req.user._id;

    const trade = await Trade.findById(tradeId);
    if (!trade) {
      return res.status(404).json({ success: false, message: 'Trade request not found' });
    }

    if (trade.owner.toString() !== ownerId.toString()) {
      return res.status(403).json({ success: false, message: 'You are not authorized to respond to this trade' });
    }

    if (!VALID_STATUSES.includes(status)) {
      return res.status(400).json({ success: false, message: 'Invalid trade status' });
    }

    const updateData = { status, notesFromOwner: notes };

    if (status === 'proposed' && proposedBookId) {
      const proposedBook = await Book.findById(proposedBookId);
      if (!proposedBook || proposedBook.owner.toString() !== ownerId.toString()) {
        return res.status(400).json({ success: false, message: 'Invalid proposed book' });
      }
      updateData.proposedBookFromOwner = proposedBookId;
    }

    const updatedTrade = await Trade.findByIdAndUpdate(tradeId, updateData, { new: true })
      .populate('requester', 'name email')
      .populate('owner', 'name email')
      .populate('offeredBook', 'title author coverImage')
      .populate('requestedBook', 'title author coverImage')
      .populate('proposedBookFromOwner', 'title author coverImage');

    res.status(200).json({ success: true, message: 'Trade response recorded', data: updatedTrade });
  } catch (error) {
    console.error('Error responding to trade:', error);
    res.status(500).json({
      success: false,
      message: 'Failed to respond to trade',
      details: process.env.NODE_ENV === 'development' ? error.message : undefined,
    });
  }
};

const cancelTrade = async (req, res) => {
  try {
    const { tradeId } = req.params;
    const userId = req.user._id;

    const trade = await Trade.findById(tradeId);
    if (!trade) {
      return res.status(404).json({ success: false, message: 'Trade request not found' });
    }

    if (trade.requester.toString() !== userId.toString() && trade.owner.toString() !== userId.toString()) {
      return res.status(403).json({ success: false, message: 'You are not authorized to cancel this trade' });
    }

    if (['accepted', 'completed'].includes(trade.status)) {
      return res.status(400).json({ success: false, message: 'Cannot cancel a trade that has been accepted or completed' });
    }

    const updatedTrade = await Trade.findByIdAndUpdate(tradeId, { status: 'cancelled' }, { new: true })
      .populate('requester', 'name email')
      .populate('owner', 'name email')
      .populate('offeredBook', 'title author coverImage')
      .populate('requestedBook', 'title author coverImage')
      .populate('proposedBookFromOwner', 'title author coverImage');

    res.status(200).json({ success: true, message: 'Trade cancelled successfully', data: updatedTrade });
  } catch (error) {
    console.error('Error cancelling trade:', error);
    res.status(500).json({
      success: false,
      message: 'Failed to cancel trade',
      details: process.env.NODE_ENV === 'development' ? error.message : undefined,
    });
  }
};

const completeTrade = async (req, res) => {
  try {
    const { tradeId } = req.params;
    const userId = req.user._id;

    const trade = await Trade.findById(tradeId);
    if (!trade) {
      return res.status(404).json({ success: false, message: 'Trade request not found' });
    }

    if (trade.requester.toString() !== userId.toString() && trade.owner.toString() !== userId.toString()) {
      return res.status(403).json({ success: false, message: 'You are not authorized to complete this trade' });
    }

    if (!['accepted', 'proposed'].includes(trade.status)) {
      return res.status(400).json({ success: false, message: 'Trade must be accepted or proposed before completion' });
    }

    if (!trade.counterAcceptedByRequester && trade.owner.toString() === userId.toString()) {
      trade.counterAcceptedByRequester = true;
      await trade.save();
      return res.status(200).json({ success: true, message: 'Counter acceptance recorded', data: trade });
    }

    if (trade.counterAcceptedByRequester && trade.requester.toString() === userId.toString()) {
      trade.status = 'completed';
      trade.tradeDate = new Date();
      await trade.save();

      // Update book ownership
      const offeredBook = await Book.findById(trade.offeredBook);
      const requestedBook = await Book.findById(trade.requestedBook);

      offeredBook.owner = trade.owner;
      offeredBook.ownerId = trade.owner.toString();
      requestedBook.owner = trade.requester;
      requestedBook.ownerId = trade.requester.toString();

      await offeredBook.save();
      await requestedBook.save();

      const populatedTrade = await Trade.findById(tradeId)
        .populate('requester', 'name email')
        .populate('owner', 'name email')
        .populate('offeredBook', 'title author coverImage')
        .populate('requestedBook', 'title author coverImage')
        .populate('proposedBookFromOwner', 'title author coverImage');

      return res.status(200).json({ success: true, message: 'Trade completed successfully', data: populatedTrade });
    }

    return res.status(400).json({ success: false, message: 'Both parties must agree to complete the trade' });
  } catch (error) {
    console.error('Error completing trade:', error);
    res.status(500).json({
      success: false,
      message: 'Failed to complete trade',
      details: process.env.NODE_ENV === 'development' ? error.message : undefined,
    });
  }
};

module.exports = {
  initiateTrade,
  getUserTrades,
  getSingleTrade,
  respondToTrade,
  cancelTrade,
  completeTrade,
};