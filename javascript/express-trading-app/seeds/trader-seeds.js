const { Trader } = require('../models')

const traderData = [
  {
    "id": 1,
    "firstName": "Mike",
    "lastName": "Spencer",
    "dob": "1990-01-01",
    "country": "Canada",
    "email": "mike@test.com",
  },
  {
    "id": 2,
    "firstName": "Hellen",
    "lastName": "Miller",
    "dob": "1990-01-01",
    "country": "Austria",
    "email": "hellen@test.com",
  },
  {
    "id": 3,
    "firstName": "Jack",
    "lastName": "Reed",
    "dob": "1990-01-01",
    "country": "United Kingdom",
    "email": "jack@test.com",
  },
  {
    "id": 4,
    "firstName": "Robert",
    "lastName": "Howard",
    "dob": "1990-01-01",
    "country": "Switzerland",
    "email": "robert@test.com",
  },
  {
    "id": 5,
    "firstName": "Dustin",
    "lastName": "Wise",
    "dob": "1990-01-01",
    "country": "Russia",
    "email": "dustin@test.com",
  },
  {
    "id": 6,
    "firstName": "Sergio",
    "lastName": "Chung",
    "dob": "1990-01-01",
    "country": "China",
    "email": "sergio@test.com",
  },
  {
    "id": 7,
    "firstName": "Magnolia",
    "lastName": "Cortez",
    "dob": "1990-01-01",
    "country": "Malaysia",
    "email": "magnolia@test.com",
  },
  {
    "id": 8,
    "firstName": "Jeremy",
    "lastName": "Alvarez",
    "dob": "1990-01-01",
    "country": "Mexico",
    "email": "jeremy@test.com",
  },
  {
    "id": 9,
    "firstName": "Valerie",
    "lastName": "Lee",
    "dob": "1990-01-01",
    "country": "Turkey",
    "email": "valerie@test.com",
  },
  {
    "id": 10,
    "firstName": "Lydia",
    "lastName": "Zeena",
    "dob": "1990-01-01",
    "country": "Morocco",
    "email": "hellen@test.com",
  }
]

const seedTraders = () => Trader.bulkCreate(traderData)

module.exports = seedTraders