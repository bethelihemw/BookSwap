const mongoose = require('mongoose');

const connectDB = async () => {
  try {
    const conn = await mongoose.connect(process.env.MONGO_URI, {
      // Remove deprecated options
      // Add modern options for better control
      serverSelectionTimeoutMS: 5000, // 5 seconds to select server
      socketTimeoutMS: 45000, // 45 seconds socket timeout
      heartbeatFrequencyMS: 10000, // 10 seconds heartbeat
      maxPoolSize: 10, // Adjust based on your app's needs
    });

    console.log(`MongoDB connected successfully to ${conn.connection.host}`);
  } catch (error) {
    console.error('MongoDB connection error:', error.message);
    // Avoid hard exit; let the app handle it or retry
    // Optionally implement retry logic here
  }
};

// Optional: Add event listeners for connection state changes
mongoose.connection.on('disconnected', () => {
  console.log('MongoDB disconnected. Attempting to reconnect...');
});

mongoose.connection.on('reconnected', () => {
  console.log('MongoDB reconnected successfully');
});

mongoose.connection.on('error', (error) => {
  console.error('MongoDB connection error:', error.message);
});

module.exports = { connectDB };