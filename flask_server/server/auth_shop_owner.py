import mysql.connector
from flask import (
    Blueprint, flash, g, redirect, render_template, request, session, url_for
)
from werkzeug.security import check_password_hash, generate_password_hash


bp = Blueprint('auth_shop_owner', __name__,url_prefix='/auth_shop_owner')

db1 = mysql.connector.connect(user='root', password='asdf1234',
                        host='127.0.0.1',
                        database='mydb')

@bp.route('/')
def hello():
    return 'Hello, owner world!'

@bp.route("/register", methods=("GET", "POST"))
def register():
    """Register a new user.
    Validates that the username is not already taken. Hashes the
    password for security.
    """
    if request.method == "POST":
        username = request.form["username"]
        password = request.form["password"]
        gender = request.form["gender"]
        birthday = request.form['birthday']
        db = db1.cursor()
        db.execute("SELECT Username FROM storeowner WHERE Username = %s", (username,))
        res = db.fetchone()
        if (res is not None ):
            return "User {0} is already registered.".format(username)

        else:
            db.execute(
                "INSERT INTO storeowner (username, password,Gender, Birthday) VALUES (%s, %s,%s,%s)",
                (username, generate_password_hash(password),gender,birthday),
            )
            db1.commit()

            return "successful store owner register"
    else:
        return "register no Get"

@bp.route("/login", methods=("GET", "POST"))
def login():
    if request.method == 'POST':
        username = request.form['username']
        password = request.form['password']
        db = db1.cursor()

        error = None
        db.execute('SELECT * FROM storeowner WHERE Username = %s', (username,))
        user = db.fetchone()

        if user is None:
            error =  'Incorrect username.'
        elif not check_password_hash(user[2], password):
            error = 'Incorrect password'

        if error is None:
            session.clear()
            session['user_id'] = user[0]     
        
            return "login successful"
        else:
            return error
    else:
        return 'error'

@bp.before_app_request
def load_logged_in_user():
    user_id = session.get('user_id')
    db = db1.cursor()
    if user_id is None:
        g.user = None
    else:
        db.execute(
            'SELECT * FROM storeowner WHERE StoreOwnerId = %s', (user_id,)
        )
        g.user = db.fetchone()

@bp.route("/logout",methods=("GET", "POST"))
def logout():
    """Clear the current session, including the stored user id."""
    session.clear()
    return "log out"