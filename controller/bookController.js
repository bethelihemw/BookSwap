const Book = require("../models/books");

const addBook = async (req, res) => {
  try {
    const { title, author, genre, photo } = req.body;
    const owner = req.user._id;

    const newBook = new Book({
      title,
      author,
      genre,
      photo,
      owner,
    });

    const savedBook = await newBook.save();
    res.status(201).json(savedBook);
  } catch (error) {
    res.json({ message: error.message });
  }
};

const getBooks = async (req, res) => {
  try {
    let {search, page, limit} = req.query;
    let filter = {}
    if(search !== undefined) filter.title = new RegExp(search, "i");
    if (genre !== undefined) filter.genre = genre;
    page = parseInt(page) || 1;
    limit = parseInt(limit) || 5;
    const skip = (page - 1) * limit;
    const books = await Book.find(filter).skip(skip).limit(limit);
    res.status(200).json({ Books: books });
  } catch (error) {
    console.log(error)
    res.status(404).json({ message: error.message });
  }
};

const getSingleBook = async (req, res) => {
  try {
    const book = await Book.findById(req.params.id);
    res.status(200).json({ Book: book });
  } catch (error) {
    res.status(404).json({ message: error.message });
  }
};

const update = async (req, res) => {
  try {
    const book = await Book.findById(req.params.id);
    if (!book) {
      return res.status(404).json({ message: "Book not found" });
    }

    if (book.owner.toString() !== req.user._id.toString()) {
      return res
        .status(403)
        .json({ message: "Unauthorized to update this book" });
    }
    const updatedBook = await Book.findOneAndUpdate(
      { _id: req.params.id },
      req.body,
      {
        new: true,
      }
    );
    res.status(200).json({ message: "book updated successfully", updatedBook });
  } catch (error) {
    res.status(404).json({ message: error.message });
  }
};

const deleteBook = async (req, res) => {
  try {
    const book = await Book.findById(req.params.id);
    if (!book) {
      return res.status(404).json({ message: "Book not found" });
    }

    if (book.owner.toString() !== req.user._id.toString()) {
      return res
        .status(403)
        .json({ message: "Unauthorized to delete this book" });
    }

    await Book.findByIdAndDelete(req.params.id);
    res.status(204).send("book deleted successfully!");
  } catch (error) {
    res.status(404).json({ message: error.message });
  }
};

module.exports = {
  addBook,
  deleteBook,
  update,
  getBooks,
  getSingleBook,
};
