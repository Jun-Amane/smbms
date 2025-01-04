import requests
import json

# Base configuration
BASE_URL = "http://localhost:8080"
AUTH_URL = f"{BASE_URL}/auth/login"
USER_URL = f"{BASE_URL}/api/user"

def run_tests(user_id):
    # Step 1: Login and get token
    login_data = {
        "username": "admin",
        "password": "1234567"
    }
    
    try:
        # Login request
        login_response = requests.post(AUTH_URL, json=login_data)
        login_response.raise_for_status()
        token = login_response.json().get('token')
        
        # Set up headers with token
        headers = {
            'Authorization': f'Bearer {token}',
            'Content-Type': 'application/json'
        }
        
        # Test 1: Check if user code exists
        print("\nTest: Check if user code exists")
        response = requests.get(f"{USER_URL}/codeexists", params={"code": "admin"}, headers=headers)
        print(response.text)
        
        # Test 2: Get user list
        print("\nTest: Get user list")
        params = {
            "queryName": "test",
            "queryRole": "1",
            "pageSize": "10",
            "pageIndex": "1"
        }
        response = requests.get(USER_URL, params=params, headers=headers)
        print(response.text)
        
        # Test 3: Get user details
        print("\nTest: Get user details by ID")
        response = requests.get(f"{USER_URL}/{user_id}", headers=headers)
        print(response.text)
        
        # Test 4: Update user details
        print("\nTest: Update user details")
        update_data = {
            "name": "Updated Name",
            "code": "updatedCode",
            "password": "admin",
            "gender": 2,
            "birthday": "2000-01-01",
            "phone": "1234567890",
            "address": "Updated Address",
            "roleId": 2
        }
        response = requests.put(f"{USER_URL}/{user_id}", json=update_data, headers=headers)
        print(response.text)
        
        # Test 5: Update password
        print("\nTest: Update user's password")
        password_data = {"newPassword": "newtestpwd"}
        response = requests.patch(f"{USER_URL}/{user_id}/password", json=password_data, headers=headers)
        print(response.text)
        
        # Test 6: Delete user
        print("\nTest: Delete user by ID")
        response = requests.delete(f"{USER_URL}/{user_id}", headers=headers)
        print(response.text)
        
        # Test 7: Add new user
        print("\nTest: Add a new user")
        new_user_data = {
            "name": "New User",
            "code": "newUserCode",
            "password": "newPass123",
            "gender": 1,
            "birthday": "1995-05-05",
            "phone": "0987654321",
            "address": "New Address",
            "roleId": 1
        }
        response = requests.post(USER_URL, json=new_user_data, headers=headers)
        print(response.text)
        
        # Test 8: Get role list
        print("\nTest: Get role list")
        response = requests.get(f"{USER_URL}/rolelist", headers=headers)
        print(response.text)

    except requests.exceptions.RequestException as e:
        print(f"Error occurred: {e}")
        if hasattr(e.response, 'text'):
            print(f"Response: {e.response.text}")

if __name__ == "__main__":
    import sys
    if len(sys.argv) != 2:
        print("Usage: python script.py USER_ID")
        sys.exit(1)
        
    user_id = sys.argv[1]
    run_tests(user_id)
