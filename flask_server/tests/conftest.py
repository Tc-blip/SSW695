import os
import tempfile

import pytest
from server import create_app
from server.db import get_db, init_db

with open(os.path.join(os.path.dirname(__file__), 'data.sql'), 'rb') as f:
    _data_sql = f.read().decode('utf8')


@pytest.fixture
def app():
    db_fd, db_path = tempfile.mkstemp()   # tempfile.mkstemp() creates and opens a temporary file, 
                                          # returning the file object and the path to it. 
    app = create_app({
        'TESTING': True,
        'DATABASE': db_path,
    })

    with app.app_context():
        init_db()
        get_db().executescript(_data_sql)

    yield app

    os.close(db_fd)
    os.unlink(db_path)


@pytest.fixture
def client(app):
    return app.test_client()


@pytest.fixture
def runner(app):
    return app.test_cli_runner()

class AuthActions:
    def __init__(self, client):
        self._client = client

    def login_user(self, username="tc123", password="567"):
        return self._client.post(
            "/auth/login", data={"username": username, "password": password}
        )
    def logout_user(self):
        return self._client.get("/auth/logout")
    
    def login_shop(self, username="best", password="123"):
        return self._client.post(
            "/auth_shop_owner/login", data={"username": username, "password": password}
        )
    def logout_shop(self):
        return self._client.get("/auth_shop_owner/logout")


@pytest.fixture
def auth(client):
    return AuthActions(client)