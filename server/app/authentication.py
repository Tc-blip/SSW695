from flask import Flask, escape, request
import mysql.connector
from db import get_db
from flask.helpers import flash


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


    return "login successful"


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
                "INSERT INTO user (username, password) VALUES (%s, %s,%s,%s)",
                (username, password,gender,birthday),
            )
            db1.commit()

            return "successful"
            

if __name__ == '__main__':
    app.debug = True
    app.run()