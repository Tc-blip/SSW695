import pytest
from flask import g, session
from server.db import get_db


def test_register(client, app):
    assert client.get('/auth/register').status_code == 200
    response = client.post(
        '/auth/register', data={'username': 'a', 'password': 'a','gender':'','birthday':''}
    )
    with app.app_context():
        assert get_db().execute(
            "select * from user where username = 'a'",
        ).fetchone() is not None


@pytest.mark.parametrize(('username', 'password', 'gender','birthday','message'), (
    ('', '','','', b'Username is required.'),
    ('a', '','','', b'Password is required.'),
    ('best', '567','','', b'already registered.'),
))
def test_register_validate_input(client, username, password, gender, birthday, message):
    response = client.post(
        '/auth/register',
        data={'username': username, 'password': password,'gender': gender, 'birthday': birthday}
    )
    assert message in response.data

