import pytest
from server.db import get_db

def test_get_user_infor(client, auth, app):
    auth.login_shop()
    assert client.get('shop_owner/get_shop_owner_infor').status_code == 200
    rv = client.get('shop_owner/get_shop_owner_infor')
    json_data = rv.get_json()
    assert json_data == {
  "Birthday": " 2020-03-01}",
  "CreateTime": '',
  "Gender": "male",
  "StoreOwnerId": 2,
  "UserName": "best"
}


#def test_set_user_infor(client, auth, app):