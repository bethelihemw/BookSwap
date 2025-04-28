const express = require("express");
const router = express.Router();
const authController = require("../controller/authController")
const authMiddleware = require("../middleware/authMiddleware")

router.post("/register", authController.register)

router.post("/login", authController.login)

router.get("/", authMiddleware, authController.getUsers)

router.get("/:id", authMiddleware, authController.getSingleUser)

router.put("/:id", authMiddleware, authController.update)

router.delete("/:id", authMiddleware, authController.deleteUser)


module.exports = router