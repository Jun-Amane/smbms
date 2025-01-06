import requests
import json

# 基本配置
BASE_URL = "http://localhost:8080"  # 假设 API 运行在此地址
BILL_URL = f"{BASE_URL}/api/bill"  # 账单接口地址
PROVIDER_URL = f"{BASE_URL}/api/bill/providerlist"  # 供应商接口地址

def run_tests(token, user_id):
    headers = {
        'Authorization': f'Bearer {token}',  # 使用 JWT token 进行授权
        'Content-Type': 'application/json'   # 设置内容类型为 JSON
    }

    # 测试 1: 获取账单列表（带过滤条件）
    print("\n测试：获取带过滤条件的账单列表")
    params = {
        "queryCode": "B123",  # 示例过滤条件
        "queryProductName": "Product1",
        "queryIsPaid": 1,  # 已支付
        "pageIndex": 1,
        "pageSize": 10
    }
    response = requests.get(BILL_URL, params=params, headers=headers)
    print(response.status_code, response.text)

    # 测试 2: 根据 ID 获取单个账单
    print("\n测试：根据 ID 获取账单")
    bill_id = 1  # 假设已存在的账单 ID
    response = requests.get(f"{BILL_URL}/{bill_id}", headers=headers)
    print(response.status_code, response.text)

    # 测试 3: 更新指定 ID 的账单
    print("\n测试：更新指定 ID 的账单")
    bill_data = {
        "productName": "Updated Product",
        "productDesc": "Updated Description",
        "totalPrice": 120.50,
        "id": 1,  # 假设账单 ID 为 1
        "isPaid": 1
    }
    response = requests.put(f"{BILL_URL}/{bill_id}", json=bill_data, headers=headers)
    print(response.status_code, response.text)

    # 测试 4: 创建新账单
    print("\n测试：创建新账单")
    new_bill_data = {
        "productName": "New Product",
        "productDesc": "New Description",
        "totalPrice": 150.75,
        "id": 1,  # 假设供应商 ID 为 1
        "isPaid": 0  # 未支付
    }
    response = requests.post(BILL_URL, json=new_bill_data, headers=headers)
    print(response.status_code, response.text)

    # 测试 5: 获取供应商列表
    print("\n测试：获取供应商列表")
    response = requests.get(PROVIDER_URL, headers=headers)
    print(response.status_code, response.text)

if __name__ == "__main__":
    import sys
    if len(sys.argv) != 3:
        print("用法: python script.py USER_ID TOKEN")
        sys.exit(1)

    user_id = sys.argv[1]  # 用户 ID
    token = sys.argv[2]    # 用户授权的 JWT token
    run_tests(token, user_id)
