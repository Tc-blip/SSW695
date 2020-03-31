import mysql.connector
from flask import (
    Blueprint, flash, g, redirect, jsonify, request, session, url_for
)
from werkzeug.security import check_password_hash, generate_password_hash

bp = Blueprint('user', __name__, url_prefix='/user')

from . import db
db1 = db.connection()

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
        userid = g.user[0]
        password = request.form["password"]
        gender = request.form["gender"]
        birthday = request.form['birthday']
        db = db1.cursor(dictionary=True)
        db.execute("UPDATE user SET password=%s,Gendar=%s,Birthday=%s Where UserID = %s",
                    (generate_password_hash(password),gender,birthday,userid,))
        db1.commit()
        return "change successful"
    
    return "error"

@bp.route('/get_user_love')
def get_user_love():
    user_id = session.get('user_id')
    db = db1.cursor(dictionary=True)
    db.execute('SELECT * FROM user_loves_store WHERE user_UserId=%s',
                (user_id,))
    res = db.fetchall()
    return jsonify(res)


@bp.route('/set_user_love',methods=("GET","POST"))
def set_user_love():
    if request.method == 'POST':
        user_id = session.get('user_id')
        store_id = request.form['storeid']
        db = db1.cursor(dictionary=True)
        db.execute('SELECT * FROM user_loves_store WHERE user_UserId=%s and store_StoreId=%s',
        (user_id,store_id,))
        res = db.fetchone()
        print(res)
        if not res:
            db.execute(
                'INSERT INTO user_loves_store (store_StoreId, user_UserId) VALUES(%s,%s)',
                (store_id, user_id,)
            ) 
            db1.commit()
        else:
            db.execute(
                'DELETE FROM user_loves_store WHERE ID=%s',
                (res['ID'],)
            ) 
            db1.commit()
        return 'success'
    return 'error'
            
