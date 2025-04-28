const express = require("express")
const mongoose = require("mongoose")
const authRouter = require("./routes/authRoutes")
const bookRouter = require("./routes/bookRoutes")
const tradeRouter = require("./routes/tradeRoutes")
require("dotenv").config()
const app = express()

app.use(express.json())
app.use('/api/auth', authRouter)
app.use('/api/books', bookRouter)
app.use('/api/trades', tradeRouter)


mongoose.connect(process.env.MONGODB_URL).then( ()=>
    console.log("database connected....")
).catch(err => console.log(err))

const PORT = process.env.PORT || 5000;
app.listen(PORT, ()=>{
   console.log( "server is listening...")
 })