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



def test_set_user_infor(client, auth, app):
    auth.login_shop()
    response = client.post('shop_owner/set_shop_owner_infor', 
    data={"gender": 'f', "password": 123,'birthday':'2020-03-02'})
    assert response.status_code == 200
    
    with app.app_context():
        rv = get_db().execute(
            "SELECT username,Gender,Birthday FROM storeowner where username= 'best'"
            ).fetchone()
        assert (rv[0],rv[1],rv[2])== ('best', 'f','2020-03-02')
