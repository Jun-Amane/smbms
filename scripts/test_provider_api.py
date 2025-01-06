import requests
import json

# Base configuration
BASE_URL = "http://localhost:8080"
PROVIDER_URL = f"{BASE_URL}/api/provider"
LOGIN_URL = f"{BASE_URL}/auth/login"


def run_provider_tests():
    # Step 1: Login and get token
    login_data = {
        "username": "admin",
        "password": "1234567"
    }

    try:
        # Login request
        login_response = requests.post(LOGIN_URL, json=login_data)
        login_response.raise_for_status()
        token = login_response.json().get('token')

        # Set up headers with token
        headers = {
            'Authorization': f'Bearer {token}',
            'Content-Type': 'application/json'
        }

        # Test 1: Query providers
        print("\nTest: Query providers")
        params = {
            "queryName": "Provider Test",
            "queryCode": "PT001",
            "pageIndex": 1,
            "pageSize": 10
        }
        response = requests.get(PROVIDER_URL, params=params, headers=headers)
        print(response.text)

        # Test 2: Add a new provider
        print("\nTest: Add a new provider")
        new_provider_data = {
            "code": "NEW001",
            "name": "New Provider",
            "description": "A new test provider",
            "contact": "John Doe",
            "phone": "1234567890",
            "address": "123 Test Street",
            "fax": "123-456-7890",
            "createdBy": 1,
            "creationDate": "2025-01-01T12:00:00",
            "modifiedBy": 1,
            "modificationDate": "2025-01-01T12:00:00"
        }
        response = requests.post(PROVIDER_URL, json=new_provider_data, headers=headers)
        print(response.text)

        # Test 3: Get provider by ID
        provider_id = 1  # Replace with a valid provider ID
        print("\nTest: Get provider by ID")
        response = requests.get(f"{PROVIDER_URL}/{provider_id}", headers=headers)
        print(response.text)

        # Test 4: Modify provider
        print("\nTest: Modify provider")
        modified_provider_data = {
            "code": "BJ_GYS001",
            "name": "Modified Provider",
            "description": "An updated test provider",
            "contact": "Jane Smith",
            "phone": "0987654321",
            "address": "456 Update Lane",
            "fax": "098-765-4321",
            "createdBy": 1,
            "creationDate": "2025-01-01T12:00:00",
            "modifiedBy": 1,
            "modificationDate": "2025-01-05T12:00:00"
        }
        response = requests.put(f"{PROVIDER_URL}/{provider_id}", json=modified_provider_data, headers=headers)
        print(response.text)

        # Test 5: Delete provider by ID
        print("\nTest: Delete provider by ID")
        response = requests.delete(f"{PROVIDER_URL}/{provider_id}", headers=headers)
        print(response.text)

    except requests.exceptions.RequestException as e:
        print(f"Error occurred: {e}")
        if hasattr(e.response, 'text'):
            print(f"Response: {e.response.text}")


if __name__ == "__main__":
    run_provider_tests()
