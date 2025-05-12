const express = require("express")
const mongoose = require("mongoose")
const dotenv = require('dotenv');
const cors = require('cors');
const path = require('path');

dotenv.config();

const authRouter = require("./routes/authRoutes")
const bookRouter = require("./routes/bookRoutes")
const tradeRouter = require("./routes/tradeRoutes")

const app = express()

app.use(cors({
  origin: ['http://localhost:3000', 'http://10.0.2.2:4000'],
  methods: ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS'],
  allowedHeaders: ['Content-Type', 'Authorization'],
  credentials: true
}));
app.use(express.json())
app.use('/uploads', express.static(path.join(__dirname, 'uploads')));


app.use('/api/auth', authRouter)
app.use('/api/books', bookRouter)
app.use('/api/trades', tradeRouter)

app.get('/', (req, res) => {
  res.send('📚 Book Swap API is running locally...');
});

// mongoose.connect(process.env.MONGODB_URL).then(() =>
//   console.log("database connected....")
// ).catch(err => console.log(err))

// const PORT = process.env.PORT || 5000;
// app.listen(PORT, () => {
//   console.log("server is listening...")
// })
const PORT = process.env.PORT || 5000;

mongoose.connect(process.env.MONGO_URI || 'mongodb://localhost:27017/bookswap', {
  useNewUrlParser: true,
  useUnifiedTopology: true
}).then(() => {
  console.log('✅ MongoDB connected');
  app.listen(PORT, () => {
    console.log('🚀 Server is listening...');
  });
}).catch((err) => {
  console.error('❌ MongoDB connection failed:', err.message);
});