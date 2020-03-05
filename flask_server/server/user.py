import mysql.connector
from flask import (
    Blueprint, flash, g, redirect, jsonify, request, session, url_for
)
from werkzeug.security import check_password_hash, generate_password_hash

bp = Blueprint('user', __name__, url_prefix='/user')

db1 = mysql.connector.connect(user='root', password='tiancheng',
                        host='127.0.0.1',
                        database='mydb')

@bp.route('/get_user_infor')
def get_user_infor():
    if g.user:
        dict = {"UserID": g.user[0],
                "UserName": g.user[1],
                "CreateTime":g.user[3],
                "Gender": g.user[4],
                "Birthday": g.user[5]}

        return jsonify(dict)

    else:
        return "error"

@bp.route('/set_user_infor',methods=("GET","POST"))
def set_user_infor():
    if request.method == 'POST':
        username = g.user[0]
        password = request.form["password"]
        gender = request.form["gender"]
        birthday = request.form['birthday']
        db = db1.cursor(dictionary=True)
        db.execute("UPDATE user SET password=%s,Gendar=%s,Birthday=%s Where UserID = %s",(generate_password_hash(password),gender,birthday,username,))
        db1.commit()
        return "change successful"
    
    return "error"

def get_user_fav():
    fav_list = {}
    db = db1.cursor()

    return jsonify(fav_list)

def update_user_fav():

    return 'error'
