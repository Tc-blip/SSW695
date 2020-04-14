import pytest
from server.db import get_db


def test_create_shop(client, auth, app):
    auth.login_shop()
    response = client.post('shop/create_shop', 
    data={"storename": 'gucci', "latitute": 456,'longitude':678,'description':"right"})
    assert response.status_code == 200
    
    with app.app_context():
        rv = get_db().execute(
            'SELECT StoreId, StoreName, StoreLatitude, StoreLongitude, StoreDescription from store \
                where StoreId = (SELECT max(StoreId) FROM store)'
            ).fetchone()

        rv2 = get_db().execute(
            'SELECT StoreId,StoreOwnerId from store_has_storeowner \
                where id = (SELECT max(id) FROM store_has_storeowner)'
            ).fetchone()
       
        assert (rv[1],rv[2],rv[3],rv[4]) == ('gucci', 456,678,"right")
        assert (rv2[0],rv2[1]) == (rv[0],2)
        
def test_get_shop_infor(client, auth, app):
    auth.login_shop()
    assert client.get('shop/get_shop_infor').status_code == 200
    rv = client.get('shop/get_shop_infor')
    json_data = rv.get_json()

def test_set_shop_infor(client, auth, app):
    auth.login_shop()
    response = client.post('shop/set_shop_infor', 
    data={"shop_id": 17,"storename":'gucci2', "latitute": 999,'longitude':666,'description':"suc"})
    assert response.status_code == 200

    with app.app_context():
        rv = get_db().execute(
            "SELECT * FROM store where StoreId = 17"
            ).fetchone()
        assert (rv[1],rv[2],rv[3],rv[4]) == ('gucci2', 999,666,"suc")
