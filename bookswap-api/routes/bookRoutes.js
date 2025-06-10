const express = require('express');
const router = express.Router();
const { body, param, query } = require('express-validator');
const bookController = require('../controllers/bookController');
const authMiddleware = require('../middleware/authMiddleware');
const upload = require('../middleware/fileUploadMiddleware');
const apiLimiter = require('../middleware/rateLimit');

router.use(apiLimiter);

const validateBookInput = [
  body('title').trim().notEmpty().withMessage('Title is required'),
  body('author').trim().notEmpty().withMessage('Author is required'),
  body('description').trim().notEmpty().withMessage('Description is required'),
  body('genre').trim().notEmpty().withMessage('Genre is required'),
  body('language').trim().notEmpty().withMessage('Language is required'),
  body('edition').trim().notEmpty().withMessage('Edition is required'),
];

router.post('/resources', authMiddleware, upload.single('coverImage'), validateBookInput, bookController.addBook);
router.get(
  '/resources',
  authMiddleware,
  [
    query('page').optional().isInt({ min: 1 }).withMessage('Page must be a positive integer'),
    query('limit').optional().isInt({ min: 1, max: 50 }).withMessage('Limit must be between 1 and 50'),
    query('search').optional().trim(),
    query('genre').optional().trim(),
  ],
  bookController.getBooks
);
router.get(
  '/resources/:id',
  authMiddleware,
  [param('id').isMongoId().withMessage('Invalid book ID')],
  bookController.getSingleBook
);
router.put(
  '/resources/:id',
  authMiddleware,
  upload.single('coverImage'),
  [
    param('id').isMongoId().withMessage('Invalid book ID'),
    body('title').optional().trim().notEmpty().withMessage('Title cannot be empty'),
    body('author').optional().trim().notEmpty().withMessage('Author cannot be empty'),
    body('description').optional().trim().notEmpty().withMessage('Description cannot be empty'),
    body('genre').optional().trim().notEmpty().withMessage('Genre cannot be empty'),
    body('language').optional().trim().notEmpty().withMessage('Language cannot be empty'),
    body('edition').optional().trim().notEmpty().withMessage('Edition cannot be empty'),
  ],
  bookController.updateBook
);
router.delete(
  '/resources/:id',
  authMiddleware,
  [param('id').isMongoId().withMessage('Invalid book ID')],
  bookController.deleteBook
);

router.use((err, req, res, next) => {
  if (err.name === 'ValidationError') {
    return res.status(400).json({
      success: false,
      message: 'Validation error',
      errors: Object.values(err.errors).map(error => error.msg || error.message),
    });
  }
  next(err);
});

module.exports = router;