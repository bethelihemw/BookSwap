const User = require("../models/user");
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");
const fs = require('fs')
require("dotenv").config();

const register = async (req, res) => {
  try {
    const { name, email, password, role} = req.body;
    const userExists = await User.findOne({ email });
    if (userExists)
      return res.status(400).json({ message: "User already exists" });
    const user = new User({
      name: name,
      email: email,
      password: password,
      profilePic: req.file ? req.file.path : undefined,
      role: role || "user",
    });
    await user.save();
    const token = jwt.sign({id: user._id, email: user.email}, process.env.JWT_SECRET)
    res.status(200).json({ message: "user registered successfully" , token});
  } catch (error) {
    res.json({ message: error.message });
  }
};

const login = async (req, res) => {
  try {
    const { email, password } = req.body;
    const user = await User.findOne({ email });
    if (!user) return res.status(500).json({ message: "user not found!" });

    const isMatch = await bcrypt.compare(password, user.password);
    if (!isMatch)
      return res.status(404).json({ message: "invalid credentials!" });

    const token = jwt.sign(
      { id: user._id, email: user.email, role: user.role },
      process.env.JWT_SECRET
    );
    res.json({ token });
  } catch (error) {
    res.send(error.message)
  }
};

const getUsers = async (req, res) => {
  try {
    let {search, page, limit} = req.query;
    let filter = {}
    if(search !== undefined) filter.name = new RegExp(search, "i");
    page = parseInt(page) || 1;
    limit = parseInt(limit) || 5;
    const skip = (page - 1) * limit;
    const users = await User.find(filter).skip(skip).limit(limit);
    res.status(200).json({ users: users });
  } catch (error) {
    res.status(404).json({ message: error.message });
  }
};

const getSingleUser = async (req, res) => {
  try {
    const user = await User.findById(req.params.id);
    res.status(200).json({ user: user });
  } catch (error) {
    res.status(404).json({ message: error.message });
  }
};

const update = async (req, res) => {
  try {
    const user = await User.findOneAndUpdate({ _id: req.params.id }, req.body, {
      new: true,
    });
    res.status(200).json({ message: "user updated successfully", user });
  } catch (error) {
    res.status(404).json({ message: error.message });
  }
};

const deleteUser = async (req, res) => {
  try {
    const user = await User.findOneAndDelete({ _id: req.params.id });
    res.status(200).json({ message: "user deleted successfully" });
  } catch (error) {
    res.status(404).json({ message: error.message });
  }
};


const updateProfilePic = async (req, res) => {
  try {
    const { userId } = req.params;

    const user = await User.findById(userId);
    if (!user) return res.status(404).json({ message: 'User not found' });

    if (user.profilePic) {
      fs.unlink(user.profilePic, (err) => {
        if (err) console.error('Failed to delete old profile pic:', err.message);
      });
    }

    user.profilePic = req.file.path;
    await user.save();

    res.json({ message: 'Profile picture updated successfully', user });
  } catch (error) {
    console.error(error);
    res.status(500).json({ message: 'Server error', error: error.message });
  }
};

module.exports = {register, login, getUsers, getSingleUser, deleteUser, update, updateProfilePic};
