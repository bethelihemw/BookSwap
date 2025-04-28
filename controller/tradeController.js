const Trade = require("../models/trade");
const Book = require("../models/books");
const User = require("../models/user");

// Controller function to initiate a new trade request
const initiateTrade = async (req, res) => {
  try {
    const { offeredBookId, requestedBookId, notes } = req.body;
    const requesterId = req.user._id;

    const offeredBook = await Book.findById(offeredBookId);
    const requestedBook = await Book.findById(requestedBookId);

    if (!offeredBook || !requestedBook) {
      return res.status(404).json({ message: "One or both books not found." });
    }

    if (offeredBook.owner.toString() !== requesterId.toString()) {
      return res
        .status(400)
        .json({ message: "You cannot offer a book that is not yours." });
    }

    if (requestedBook.owner.toString() === requesterId.toString()) {
      return res
        .status(400)
        .json({ message: "You cannot request a book you already own." });
    }

    const newTrade = new Trade({
      requester: requesterId,
      offeredBook: offeredBookId,
      requestedBook: requestedBookId,
      owner: requestedBook.owner,
      notesFromRequester: notes,
    });

    const savedTrade = await newTrade.save();
    res.status(201).json(savedTrade);
  } catch (error) {
    console.error("Error initiating trade:", error);
    res.status(500).json({ message: "Failed to initiate trade." });
  }
};

// Controller function to get all trade requests for the authenticated user (both as requester and owner)
const getUserTrades = async (req, res) => {
  try {
    const userId = req.user._id;
    const trades = await Trade.find({
      $or: [{ requester: userId }, { owner: userId }],
    })
      .populate("requester", "name email")
      .populate("owner", "name email")
      .populate("offeredBook", "title author photo")
      .populate("requestedBook", "title author photo")
      .populate("proposedBookFromOwner", "title author photo");
    res.status(200).json(trades);
  } catch (error) {
    console.error("Error getting user trades:", error);
    res.status(500).json({ message: "Failed to retrieve trades." });
  }
};

// Controller function to get a single trade by ID
const getSingleTrade = async (req, res) => {
  try {
    const { id } = req.params;
    const trade = await Trade.findById(id)
      .populate("requester", "name email")
      .populate("owner", "name email")
      .populate("offeredBook", "title author photo")
      .populate("requestedBook", "title author photo")
      .populate("proposedBookFromOwner", "title author photo");
    if (!trade) {
      return res.status(404).json({ message: "Trade not found." });
    }
    res.status(200).json(trade);
  } catch (error) {
    console.error("Error getting trade:", error);
    res.status(500).json({ message: "Failed to retrieve trade." });
  }
};

// Controller function for the owner of the requested book to respond to a trade request
const respondToTrade = async (req, res) => {
  try {
    const { tradeId } = req.params;
    const { status, proposedBookId, notes } = req.body;
    const ownerId = req.user._id;

    const trade = await Trade.findById(tradeId);

    if (!trade) {
      return res.status(404).json({ message: "Trade request not found." });
    }

    if (trade.owner.toString() !== ownerId.toString()) {
      return res
        .status(403)
        .json({ message: "You are not authorized to respond to this trade." });
    }

    if (!["accepted", "rejected", "proposed"].includes(status)) {
      return res.status(400).json({ message: "Invalid trade status." });
    }

    const updateData = { status, notesFromOwner: notes };
    if (status === "proposed" && proposedBookId) {
      const proposedBook = await Book.findById(proposedBookId);
      if (
        !proposedBook ||
        proposedBook.owner.toString() !== ownerId.toString()
      ) {
        return res.status(400).json({ message: "Invalid proposed book." });
      }
      updateData.proposedBookFromOwner = proposedBookId;
    }

    const updatedTrade = await Trade.findByIdAndUpdate(tradeId, updateData, {
      new: true,
    })
      .populate("requester", "name email")
      .populate("owner", "name email")
      .populate("offeredBook", "title author photo")
      .populate("requestedBook", "title author photo")
      .populate("proposedBookFromOwner", "title author photo");

    res.status(200).json(updatedTrade);
  } catch (error) {
    console.error("Error responding to trade:", error);
    res.status(500).json({ message: "Failed to respond to trade." });
  }
};

// Controller function for either party to cancel a trade (before acceptance/completion)
const cancelTrade = async (req, res) => {
  try {
    const { tradeId } = req.params;
    const userId = req.user._id;

    const trade = await Trade.findById(tradeId);

    if (!trade) {
      return res.status(404).json({ message: "Trade request not found." });
    }

    if (
      trade.requester.toString() !== userId.toString() &&
      trade.owner.toString() !== userId.toString()
    ) {
      return res
        .status(403)
        .json({ message: "You are not authorized to cancel this trade." });
    }

    if (["accepted", "completed"].includes(trade.status)) {
      return res
        .status(400)
        .json({
          message: "Cannot cancel a trade that has been accepted or completed.",
        });
    }

    const updatedTrade = await Trade.findByIdAndUpdate(
      tradeId,
      { status: "cancelled" },
      { new: true }
    )
      .populate("requester", "name email")
      .populate("owner", "name email")
      .populate("offeredBook", "title author photo")
      .populate("requestedBook", "title author photo")
      .populate("proposedBookFromOwner", "title author photo");

    res.status(200).json(updatedTrade);
  } catch (error) {
    console.error("Error cancelling trade:", error);
    res.status(500).json({ message: "Failed to cancel trade." });
  }
};

// Controller function to mark a trade as completed (potentially requires agreement from both parties)
const completeTrade = async (req, res) => {
  try {
    const { tradeId } = req.params;
    const userId = req.user._id; // Assuming only involved users can complete

    const trade = await Trade.findById(tradeId);

    if (!trade) {
      return res.status(404).json({ message: "Trade request not found." });
    }

    if (
      trade.requester.toString() !== userId.toString() &&
      trade.owner.toString() !== userId.toString()
    ) {
      return res
        .status(403)
        .json({ message: "You are not authorized to complete this trade." });
    }

    if (trade.status !== "accepted" && trade.status !== "proposed") {
      return res
        .status(400)
        .json({
          message: "Trade must be accepted or proposed before completion.",
        });
    }

    const updatedTrade = await Trade.findByIdAndUpdate(
      tradeId,
      { status: "completed", tradeDate: new Date() },
      { new: true }
    )
      .populate("requester", "name email")
      .populate("owner", "name email")
      .populate("offeredBook", "title author photo")
      .populate("requestedBook", "title author photo")
      .populate("proposedBookFromOwner", "title author photo");

    res.status(200).json(updatedTrade);
  } catch (error) {
    console.error("Error completing trade:", error);
    res.status(500).json({ message: "Failed to complete trade." });
  }
};

module.exports = {
  initiateTrade,
  getUserTrades,
  respondToTrade,
  cancelTrade,
  completeTrade,
  getSingleTrade,
};
