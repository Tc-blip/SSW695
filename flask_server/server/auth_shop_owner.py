import functools
from flask import Blueprint
from flask import flash
from flask import g
from flask import redirect
from flask import render_template
from flask import request
from flask import session
from flask import url_for
from werkzeug.security import check_password_hash
from werkzeug.security import generate_password_hash

from server.db import get_db


bp = Blueprint('auth_shop_owner', __name__, url_prefix='/auth_shop_owner')


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
        db = get_db()
        error = None
        if not username:
            error = 'Username is required.'
        elif not password:
            error = 'Password is required.'
        elif  db.execute(
            'SELECT UserId FROM storeowner WHERE username = ?', (username,)
        ).fetchone() is not None:
            error = 'User {} is already registered.'.format(username)

        if error is None:
            db.execute(
                "INSERT INTO storeowner (username, password,Gender, Birthday) VALUES (?,?,?,?)",
                (username, generate_password_hash(password),gender,birthday)
            )
            db.commit()
            return redirect(url_for("auth.login"))
        flash(error)
    else:
        return render_template("auth/register.html")

@bp.route("/login", methods=("GET", "POST"))
def login():
    if request.method == 'POST':
        username = request.form['username']
        password = request.form['password']
        db = get_db()

        error = None
        user = db.execute(
            'SELECT * FROM storeowner WHERE username = ?', (username,)
        ).fetchone()

        if user is None:
            error =  'Incorrect username'
        elif not check_password_hash(user['password'], password):
            error = 'Incorrect password'

        if error is None:
            session.clear()
            session['user_id'] = user['UserId']     
            return "success"
        else:
            return error
    
    else:
        return "cuole"

@bp.before_app_request
def load_logged_in_user():
    user_id = session.get('user_id')
    db = get_db()
    if user_id is None:
        g.user = None
    else:
        g.user = db.execute(
            'SELECT * FROM storeowner WHERE UserId = ?', (user_id,)
        ).fetchone()

@bp.route("/logout")
def logout():
    """Clear the current session, including the stored user id."""
    session.clear()
    return "log out"
    