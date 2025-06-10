const express = require('express');
const dotenv = require('dotenv');
const { connectDB } = require('./config/db');
const authRoutes = require('./routes/authRoutes');
const bookRoutes = require('./routes/bookRoutes');
const tradeRoutes = require('./routes/tradeRoutes');

dotenv.config();
const app = express();

// Middleware
app.use(express.json());
app.use('/api/v1', authRoutes);
app.use('/api/v1', bookRoutes);
app.use('/api/v1', tradeRoutes);

// Serve uploaded files statically
app.use('/uploads', express.static('uploads'));

// Global error handler
app.use((err, req, res, next) => {
  console.error(err.stack);
  res.status(500).json({
    success: false,
    message: 'Something went wrong!',
    details: process.env.NODE_ENV === 'development' ? err.message : undefined,
  });
});

// Connect to database and start server
connectDB();

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));