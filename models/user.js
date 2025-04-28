const mongoose = require("mongoose");
const bcrypt = require("bcrypt")

const schema = new mongoose.Schema(
  {
    name: {
      type: String,
      required: true,
    },
    email: {
      type: String,
      required: true,
    },
    password: {
      type: String,
      required: true,
      unique: true
    },
    profilePic: {
      type: String
    },
    role: {
      type: String,
      default: "user",
    },
  },
  { timestamps: true }
);

schema.pre("save", async function(next){
    if(!this.isModified("password")) return next();
    try {
        const salt = await bcrypt.genSalt(10)
        this.password = await bcrypt.hash(this.password, salt)
        return next()
    } catch (error) {
        return next(error)
    }
})

const User = mongoose.model("User", schema);

module.exports = User;
