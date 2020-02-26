from flask import Flask, escape, request
import mysql.connector
from flask.helpers import flash
from flask import session
from werkzeug.security import check_password_hash
from werkzeug.security import generate_password_hash

app = Flask(__name__)

db1 = mysql.connector.connect(user='root', password='tiancheng',
                        host='127.0.0.1',
                        database='mydb')

@app.route('/')
def hello():
    name = request.args.get("name", "World")
    return f'Hello, {escape(name)}!'

@app.route("/login", methods=("GET", "POST"))
def login():
    if request.method == 'POST':
        username = request.form['username']
        password = request.form['password']
        db = db1.cursor()

        error = None
        db.execute('SELECT * FROM user WHERE Username = %s', (username,))
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


@app.route("/register", methods=("GET", "POST"))
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
        
        db.execute("SELECT Username FROM user WHERE Username = %s", (username,))
        res = db.fetchone()
        if (res is not None ):
            return "User {0} is already registered.".format(username)

        else:
            db.execute(
                "INSERT INTO user (username, password,Gendar, Birthday) VALUES (%s, %s,%s,%s)",
                (username, generate_password_hash(password),gender,birthday),
            )
            db1.commit()

            return "successful"
            

@app.route("/logout",methods=("GET", "POST"))
def logout():
    """Clear the current session, including the stored user id."""
    session.clear()
    return "log out"



if __name__ == '__main__':
    app.secret_key = 'my secret key'
    app.debug = True
    app.run()