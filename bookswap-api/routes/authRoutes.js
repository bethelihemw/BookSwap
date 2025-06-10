const express = require('express');
const router = express.Router();
const authController = require('../controllers/authController');
const authMiddleware = require('../middleware/authMiddleware');
const checkRole = require('../middleware/checkRoleMiddleware');
const upload = require('../middleware/fileUploadMiddleware');
const apiLimiter = require('../middleware/rateLimit');

router.use(apiLimiter);

router.post('/auth/register', upload.single('profilePic'), authController.register);
router.post('/auth/login', authController.login);
router.get('/auth/users', authMiddleware, checkRole('admin'), authController.getUsers);
router.get('/auth/users/:id', authMiddleware, checkRole('admin'), authController.getSingleUser);
router.put('/auth/me', authMiddleware, authController.updateProfile);
router.post('/auth/change-password', authMiddleware, authController.changePassword);
router.put('/auth/update-profile-pic', authMiddleware, upload.single('profilePic'), authController.updateProfilePic);
router.post('/auth/logout', authMiddleware, authController.logout);
router.delete('/auth/account', authMiddleware, authController.deleteAccount);

module.exports = router;