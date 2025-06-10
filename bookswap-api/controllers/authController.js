const User = require('../models/user');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const fs = require('fs').promises;
require('dotenv').config();

const register = async (req, res) => {
  try {
    const { name, email, password, role } = req.body;

    // Validate required fields
    if (!name || !email || !password) {
      return res.status(400).json({ success: false, message: 'Please provide name, email, and password' });
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      return res.status(400).json({ success: false, message: 'Invalid email format' });
    }
    if (password.length < 8) {
      return res.status(400).json({ success: false, message: 'Password must be at least 8 characters' });
    }

    // Check if user exists
    const userExists = await User.findOne({ email });
    if (userExists) {
      return res.status(400).json({ success: false, message: 'User with this email already exists' });
    }

    // Create new user
    const user = new User({
      name,
      email,
      password,
      profilePic: req.file ? req.file.path : undefined,
      role: role || 'user',
    });

    await user.save();

    // Generate JWT token
    const token = jwt.sign(
      { id: user._id, email: user.email, role: user.role },
      process.env.JWT_SECRET,
      { expiresIn: '30d' }
    );

    res.status(201).json({
      success: true,
      message: 'User registered successfully',
      token,
      data: {
        id: user._id,
        name: user.name,
        email: user.email,
        role: user.role,
      },
    });
  } catch (error) {
    console.error('Registration error:', error);
    res.status(500).json({
      success: false,
      message: 'Server error during registration',
      details: process.env.NODE_ENV === 'development' ? error.message : undefined,
    });
  }
};

const login = async (req, res) => {
  try {
    const { email, password } = req.body;

    if (!email || !password) {
      return res.status(400).json({ success: false, message: 'Please provide email and password' });
    }

    const user = await User.findOne({ email }).select('+password');
    if (!user || !(await user.matchPassword(password))) {
      return res.status(401).json({ success: false, message: 'Invalid email or password' });
    }

    const token = jwt.sign(
      { id: user._id, email: user.email, role: user.role },
      process.env.JWT_SECRET,
      { expiresIn: '30d' }
    );

    user.password = undefined;

    res.status(200).json({
      success: true,
      message: 'Login successful',
      token,
      data: {
        id: user._id,
        name: user.name,
        email: user.email,
        role: user.role,
        profilePic: user.profilePic,
      },
    });
  } catch (error) {
    console.error('Login error:', error);
    res.status(500).json({
      success: false,
      message: 'Server error during login',
      details: process.env.NODE_ENV === 'development' ? error.message : undefined,
    });
  }
};

const getUsers = async (req, res) => {
  try {
    let { search, page, limit } = req.query;
    let filter = {};
    if (search) filter.name = new RegExp(search, 'i');
    page = parseInt(page) || 1;
    limit = parseInt(limit) || 5;
    const skip = (page - 1) * limit;

    const users = await User.find(filter).skip(skip).limit(limit);
    res.status(200).json({ success: true, data: users });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Failed to fetch users',
      details: process.env.NODE_ENV === 'development' ? error.message : undefined,
    });
  }
};

const getSingleUser = async (req, res) => {
  try {
    const user = await User.findById(req.params.id);
    if (!user) {
      return res.status(404).json({ success: false, message: 'User not found' });
    }
    res.status(200).json({ success: true, data: user });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Failed to fetch user',
      details: process.env.NODE_ENV === 'development' ? error.message : undefined,
    });
  }
};

const updateProfile = async (req, res) => {
  try {
    const user = await User.findById(req.user.id);
    if (!user) {
      return res.status(404).json({ success: false, message: 'User not found' });
    }

    const { name, email } = req.body;
    if (name) user.name = name;
    if (email) user.email = email;

    await user.save();
    res.status(200).json({
      success: true,
      message: 'Profile updated successfully',
      data: { id: user._id, name: user.name, email: user.email, role: user.role },
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Failed to update profile',
      details: process.env.NODE_ENV === 'development' ? error.message : undefined,
    });
  }
};

const changePassword = async (req, res) => {
  try {
    const user = await User.findById(req.user.id).select('+password');
    if (!user) {
      return res.status(404).json({ success: false, message: 'User not found' });
    }

    const { currentPassword, newPassword } = req.body;
    if (!currentPassword || !newPassword) {
      return res.status(400).json({ success: false, message: 'Please provide current and new password' });
    }

    if (!(await user.matchPassword(currentPassword))) {
      return res.status(401).json({ success: false, message: 'Current password is incorrect' });
    }

    if (newPassword.length < 8) {
      return res.status(400).json({ success: false, message: 'New password must be at least 8 characters' });
    }

    user.password = newPassword;
    await user.save();

    res.status(200).json({ success: true, message: 'Password changed successfully' });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Failed to change password',
      details: process.env.NODE_ENV === 'development' ? error.message : undefined,
    });
  }
};

const updateProfilePic = async (req, res) => {
  try {
    const user = await User.findById(req.user.id);
    if (!user) {
      return res.status(404).json({ success: false, message: 'User not found' });
    }

    if (user.profilePic) {
      await fs.unlink(user.profilePic).catch(err => console.error('Failed to delete old profile pic:', err.message));
    }

    user.profilePic = req.file.path;
    await user.save();

    res.status(200).json({
      success: true,
      message: 'Profile picture updated successfully',
      data: { profilePic: user.profilePic },
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Failed to update profile picture',
      details: process.env.NODE_ENV === 'development' ? error.message : undefined,
    });
  }
};

const logout = async (req, res) => {
  try {
    res.status(200).json({ success: true, message: 'Logout successful' });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Failed to logout',
      details: process.env.NODE_ENV === 'development' ? error.message : undefined,
    });
  }
};

const deleteAccount = async (req, res) => {
  try {
    await User.findByIdAndDelete(req.user.id);
    res.status(200).json({ success: true, message: 'Account deleted successfully' });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Failed to delete account',
      details: process.env.NODE_ENV === 'development' ? error.message : undefined,
    });
  }
};

module.exports = {
  register,
  login,
  getUsers,
  getSingleUser,
  updateProfile,
  changePassword,
  updateProfilePic,
  logout,
  deleteAccount,
};