import pytest
from flask import g, session
from server.db import get_db

def test_get_user_infor(client, auth, app):
    auth.login_user()
    assert client.get('user/get_user_infor').status_code == 200
    rv = client.get('user/get_user_infor')
    json_data = rv.get_json()
    assert json_data == {"Birthday": "", "CreateTime":'' , 
  "Gender": "s", 
  "UserId": 3, 
  "password": "pbkdf2:sha256:150000$dcBMUWiJ$5ea8ea1260a2cd290887372852fc1ed057f94c83adc8730d7feba1d103e54555", 
  "username": "tc123"
}

def test_get_user_love(client, auth, app):
    auth.login_user()
    assert client.get('user/get_user_love').status_code == 200
    rv = client.get('user/get_user_love')
    json_data = rv.get_json()
    assert json_data == []
    
   