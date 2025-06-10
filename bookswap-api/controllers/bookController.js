const Book = require('../models/books');
const qrcode = require('qrcode');

const addBook = async (req, res) => {
  try {
    const { title, author, description, genre, language, edition, coverImage } = req.body;
    const owner = req.user._id;
    const ownerId = req.user._id.toString();

    const newBook = new Book({
      title,
      author,
      description,
      genre,
      language,
      edition,
      coverImage: coverImage || (req.file ? req.file.path : undefined),
      owner,
      ownerId,
      status: 'available',
    });

    await newBook.save();

    const qrcodeData = newBook._id.toString();
    qrcode.toDataURL(qrcodeData, (err, url) => {
      if (err) {
        console.error('Error generating QR code:', err);
        return res.status(500).json({ success: false, message: 'Failed to generate QR code' });
      }
      res.status(201).json({
        success: true,
        message: 'Book added successfully',
        data: newBook,
        qrCode: url,
      });
    });
  } catch (error) {
    console.error('Error adding book:', error);
    res.status(500).json({
      success: false,
      message: 'Failed to add book',
      details: process.env.NODE_ENV === 'development' ? error.message : undefined,
    });
  }
};

const getBooks = async (req, res) => {
  try {
    let { search, genre, page, limit } = req.query;
    let filter = { status: 'available' };

    if (search) {
      filter.$or = [
        { title: new RegExp(search, 'i') },
        { author: new RegExp(search, 'i') },
        { description: new RegExp(search, 'i') },
      ];
    }

    if (genre) filter.genre = new RegExp(genre, 'i');

    page = Math.max(1, parseInt(page) || 1);
    limit = Math.min(50, Math.max(1, parseInt(limit) || 10));
    const skip = (page - 1) * limit;

    const [books, total] = await Promise.all([
      Book.find(filter)
        .sort({ createdAt: -1 })
        .skip(skip)
        .limit(limit)
        .populate('owner', 'name email'),
      Book.countDocuments(filter),
    ]);

    res.status(200).json({
      success: true,
      count: books.length,
      total,
      page,
      pages: Math.ceil(total / limit),
      data: books,
    });
  } catch (error) {
    console.error('Error fetching books:', error);
    res.status(500).json({
      success: false,
      message: 'Failed to fetch books',
      details: process.env.NODE_ENV === 'development' ? error.message : undefined,
    });
  }
};

const getSingleBook = async (req, res) => {
  try {
    const book = await Book.findById(req.params.id).populate('owner', 'name email');
    if (!book) {
      return res.status(404).json({ success: false, message: 'Book not found' });
    }
    res.status(200).json({ success: true, data: book });
  } catch (error) {
    console.error('Error fetching book:', error);
    res.status(500).json({
      success: false,
      message: 'Failed to fetch book',
      details: process.env.NODE_ENV === 'development' ? error.message : undefined,
    });
  }
};

const updateBook = async (req, res) => {
  try {
    const book = await Book.findById(req.params.id);
    if (!book) {
      return res.status(404).json({ success: false, message: 'Book not found' });
    }

    if (book.owner.toString() !== req.user._id.toString()) {
      return res.status(403).json({ success: false, message: 'Unauthorized to update this book' });
    }

    const { _id, owner, ownerId, status, ...updates } = req.body;
    if (req.file) updates.coverImage = req.file.path;

    const updatedBook = await Book.findByIdAndUpdate(
      req.params.id,
      { $set: updates },
      { new: true, runValidators: true }
    );

    res.status(200).json({
      success: true,
      message: 'Book updated successfully',
      data: updatedBook,
    });
  } catch (error) {
    console.error('Error updating book:', error);
    res.status(500).json({
      success: false,
      message: 'Failed to update book',
      details: process.env.NODE_ENV === 'development' ? error.message : undefined,
    });
  }
};

const deleteBook = async (req, res) => {
  try {
    const book = await Book.findById(req.params.id);
    if (!book) {
      return res.status(404).json({ success: false, message: 'Book not found' });
    }

    if (book.owner.toString() !== req.user._id.toString()) {
      return res.status(403).json({ success: false, message: 'Unauthorized to delete this book' });
    }

    book.status = 'unavailable';
    await book.save();

    res.status(200).json({ success: true, message: 'Book deleted successfully' });
  } catch (error) {
    console.error('Error deleting book:', error);
    res.status(500).json({
      success: false,
      message: 'Failed to delete book',
      details: process.env.NODE_ENV === 'development' ? error.message : undefined,
    });
  }
};

module.exports = {
  addBook,
  getBooks,
  getSingleBook,
  updateBook,
  deleteBook,
};