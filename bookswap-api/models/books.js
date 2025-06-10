const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const bookSchema = new mongoose.Schema(
  {
    title: {
      type: String,
      required: [true, 'Title is required'],
      trim: true,
    },
    author: {
      type: String,
      required: [true, 'Author is required'],
      trim: true,
    },
    description: {
      type: String,
      required: [true, 'Description is required'],
      trim: true,
    },
    coverImage: {
      type: String,
      default: '',
    },
    owner: {
      type: Schema.Types.ObjectId,
      ref: 'User',
      required: [true, 'Owner is required'],
    },
    ownerId: {
      type: String,
      required: [true, 'Owner ID is required'],
    },
    genre: {
      type: String,
      required: [true, 'Genre is required'],
      trim: true,
    },
    language: {
      type: String,
      required: [true, 'Language is required'],
      trim: true,
    },
    edition: {
      type: String,
      required: [true, 'Edition is required'],
      trim: true,
    },
    status: {
      type: String,
      enum: ['available', 'pending', 'unavailable'],
      default: 'available',
    },
  },
  {
    timestamps: true,
    toJSON: {
      virtuals: true,
      transform: function (doc, ret) {
        ret.id = ret._id;
        delete ret._id;
        delete ret.__v;
        return ret;
      },
    },
    toObject: {
      virtuals: true,
      transform: function (doc, ret) {
        ret.id = ret._id;
        delete ret._id;
        delete ret.__v;
        return ret;
      },
    },
  }
);

bookSchema.index({ status: 1, createdAt: -1 });

const Book = mongoose.model('Book', bookSchema);

module.exports = Book;