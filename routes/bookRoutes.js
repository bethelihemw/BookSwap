const express = require("express");
const router = express.Router();
const bookController = require("../controller/bookController")
const authMiddleware = require("../middleware/authMiddleware")

router.post("/book", authMiddleware, bookController.addBook)

router.get("/book", authMiddleware, bookController.getBooks)

router.get("/book/:id", authMiddleware, bookController.getSingleBook)

router.put("/book/:id", authMiddleware, bookController.update)

router.delete("/book/:id", authMiddleware, bookController.deleteBook)


module.exports = router