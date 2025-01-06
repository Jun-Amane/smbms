import requests
import json

# 基本配置
BASE_URL = "http://localhost:8080"  # 假设 API 运行在此地址
BILL_URL = f"{BASE_URL}/api/bill"  # 账单接口地址
AUTH_URL = f"{BASE_URL}/auth/login"
PROVIDER_URL = f"{BASE_URL}/api/bill/providerlist"  # 供应商接口地址

login_data = {
    "username": "admin",
    "password": "1234567"
}

def run_tests(bill_id):

    # 登录请求
    login_response = requests.post(AUTH_URL, json=login_data)
    login_response.raise_for_status()
    token = login_response.json().get('token')

    # 设置带有 token 的请求头
    headers = {
        'Authorization': f'Bearer {token}',
        'Content-Type': 'application/json'
    }

    # 测试 1: 获取账单列表（带过滤条件）
    print("\n测试：获取带过滤条件的账单列表")
    params = {
        "queryProductName": "米",  # 示例过滤条件
        "pageIndex": 1,
        "pageSize": 10
    }
    response = requests.get(BILL_URL, params=params, headers=headers)
    print(response.status_code, response.text)

    # 测试 2: 根据 ID 获取单个账单
    print("\n测试：根据 ID 获取账单")

    response = requests.get(f"{BILL_URL}/{bill_id}", headers=headers)
    print(response.status_code, response.text)

    # 测试 3: 更新指定 ID 的账单
    print("\n测试：更新指定 ID 的账单")
    bill_data = {
        "productName": "Updated Product",
        "productDesc": "Updated Description",
        "totalPrice": 120.50,
        "id": bill_id,  # 假设账单 ID 为 1
        "isPaid": 1
    }
    response = requests.get(f"{BILL_URL}/{bill_id}", json=bill_data, headers=headers)
    print(response.status_code, response.text)

    # 测试 4: 创建新账单
    print("\n测试：创建新账单")
    new_bill_data = {
        "billCode": "BILL2016_012",           # 账单编号
        "productName": "New Product",          # 产品名称
        "productDesc": "New Description",      # 产品描述
        "totalPrice": 150.75,                  # 总价格
        "providerId": 1,                 # 假设供应商 ID 为 bill_id（可以根据实际情况修改）
        "providerName":"ll",
        "isPaid": 1,                           # 已支付
        "productUnit": "product"               # 产品单位
    }
    response = requests.get(BILL_URL, json=new_bill_data, headers=headers)
    print(response.status_code, response.text)

    # 测试 5: 获取供应商列表
    print("\n测试：获取供应商列表")
    response = requests.get(PROVIDER_URL, headers=headers)
    print(response.status_code, response.text)


if __name__ == "__main__":
    # 假设账单 ID 为 18，实际可以从其他 API 获取或通过参数传入
    bill_id = 18
    run_tests(bill_id)
