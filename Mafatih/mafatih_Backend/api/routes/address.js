const express = require('express')
const router = express.Router()

// import file
const database = require("../../config")


// Add Address
router.post("/add", (request, response) => {
    const address = request.body.address
    const city = request.body.city
    const floor = request.body.floor
    const phone = request.body.phone
    const userId = request.body.userId
    const productId = request.body.productId

    const query = "INSERT INTO Shipping(address, city ,floor,phone,user_id, product_id) VALUES(?,?,?,?,?,?)"

    const args = [address, city, floor, phone, userId, productId]

    database.query(query, args, (error, result) => {
        if (error) {
            if (error.code === 'ER_DUP_ENTRY') {
                response.status(500).send("Deplicate Entry")
            } else {
                throw error;
            }
        } else {
            response.status(200).send("Your address is added")
        }
    });
});

module.exports = router