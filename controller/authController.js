const User = require("../models/user");
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");
const fs = require('fs')
require("dotenv").config();

const register = async (req, res) => {
  try {
    console.log("Register request received:", req.body);
    
    const { name, email, password, role } = req.body;
    
    // Validate required fields
    if (!name || !email || !password) {
      console.log("Missing required fields");
      return res.status(400).json({ 
        success: false,
        message: "Please provide all required fields (name, email, password)" 
      });
    }

    // Check if user already exists
    const userExists = await User.findOne({ email });
    if (userExists) {
      console.log("User already exists:", email);
      return res.status(400).json({ 
        success: false,
        message: "User with this email already exists" 
      });
    }

    // Create new user
    const user = new User({
      name: name,
      email: email,
      password: password, // Password will be hashed by the pre-save hook in the User model
      profilePic: req.file ? req.file.path : undefined,
      role: role || "user",
    });

    // Save user to database
    await user.save();
    console.log("User registered successfully:", user.email);
    
    // Generate JWT token
    const token = jwt.sign(
      { id: user._id, email: user.email, role: user.role },
      process.env.JWT_SECRET || 'your-secret-key',
      { expiresIn: '30d' }
    );
    
    // Send success response
    res.status(201).json({ 
      success: true,
      message: "User registered successfully",
      token: token,
      user: {
        id: user._id,
        name: user.name,
        email: user.email,
        role: user.role
      }
    });
    
  } catch (error) {
    console.error("Registration error:", error);
    res.status(500).json({ 
      success: false,
      message: "Server error during registration",
      error: error.message 
    });
  }
};

const login = async (req, res) => {
  try {
    const { email, password } = req.body;
    
    // Validate input
    if (!email || !password) {
      return res.status(400).json({
        success: false,
        message: 'Please provide both email and password'
      });
    }

    // Find user by email
    const user = await User.findOne({ email }).select('+password');
    
    // Check if user exists and password is correct
    if (!user || !(await user.matchPassword(password))) {
      return res.status(401).json({
        success: false,
        message: 'Invalid email or password'
      });
    }

    // Generate JWT token
    const token = jwt.sign(
      { 
        id: user._id, 
        email: user.email, 
        role: user.role 
      },
      process.env.JWT_SECRET || 'your-secret-key',
      { expiresIn: '30d' }
    );

    // Remove password from output
    user.password = undefined;

    // Send response with token and user data
    res.status(200).json({
      success: true,
      message: 'Login successful',
      token,
      user: {
        id: user._id,
        name: user.name,
        email: user.email,
        role: user.role,
        profilePic: user.profilePic
      }
    });
    
  } catch (error) {
    console.error('Login error:', error);
    res.status(500).json({
      success: false,
      message: 'Server error during login',
      error: error.message
    });
  }
};

const getUsers = async (req, res) => {
  try {
    let { search, page, limit } = req.query;
    let filter = {}
    if (search !== undefined) filter.name = new RegExp(search, "i");
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

module.exports = { register, login, getUsers, getSingleUser, deleteUser, update, updateProfilePic };
