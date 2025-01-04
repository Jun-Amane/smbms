#!/bin/bash

# Base URL of the API
BASE_URL="http://localhost:8080/api/user"

if [ -z "$1" ]; then
  echo "Usage: $0 USER_ID"
  exit 1
fi

USER_ID=$1

# Test 1: Check if a user code exists
echo "Test: Check if user code exists"
curl -X GET "$BASE_URL/codeexists?code=admin"

echo -e "\n"

# Test 2: Get user list with query parameters
echo "Test: Get user list"
curl -X GET "$BASE_URL/?queryName=test&queryRole=1&pageSize=10&pageIndex=1"

echo -e "\n"

# Test 3: Get a user's details by ID
echo "Test: Get user details by ID"
curl -X GET "$BASE_URL/$USER_ID"

echo -e "\n"

# Test 4: Update a user's details
echo "Test: Update user details"
curl -X PUT -H "Content-Type: application/json" -d '{
  "name": "Updated Name",
  "code": "updatedCode",
  "password": "admin",
  "gender": 2,
  "birthday": "2000-01-01",
  "phone": "1234567890",
  "address": "Updated Address",
  "roleId": 2
}' "$BASE_URL/$USER_ID"

echo -e "\n"

# Test 5: Update a user's password
echo "Test: Update user's password"
curl -X PATCH -H "Content-Type: application/json" -d '{"newPassword":"newtestpwd"}' "$BASE_URL/$USER_ID/password"

echo -e "\n"

# Test 6: Delete a user by ID
echo "Test: Delete user by ID"
curl -X DELETE "$BASE_URL/$USER_ID"

echo -e "\n"
# Test 7: Add a new user
echo "Test: Add a new user"
curl -X POST -H "Content-Type: application/json" -d '{
  "name": "New User",
  "code": "newUserCode",
  "password": "newPass123",
  "gender": 1,
  "birthday": "1995-05-05",
  "phone": "0987654321",
  "address": "New Address",
  "roleId": 1
}' "$BASE_URL/"

echo -e "\n"

# Test 8: Get role list
echo "Test: Get role list"
curl -X GET "$BASE_URL/rolelist"

echo -e "\n"
