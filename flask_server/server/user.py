import functools
from flask import Blueprint
from flask import flash
from flask import g
from flask import redirect
from flask import render_template
from flask import request
from flask import session
from flask import url_for
from flask import jsonify
from werkzeug.security import check_password_hash
from werkzeug.security import generate_password_hash
from server.db import get_db
bp = Blueprint('user', __name__, url_prefix='/user')

@bp.route('/get_user_infor')
def get_user_infor():
    db = get_db()
    user_id = session.get('user_id')

    def dict_factory(cursor, row):  
        d = {}  
        for idx, col in enumerate(cursor.description):  
            d[col[0]] = row[idx]  
        return d      
    db.row_factory = dict_factory

    infor = db.execute(
        "SELECT * FROM user WHERE UserId=?",
        (user_id,)
        ).fetchone()
    return jsonify(infor)
   

@bp.route('/set_user_infor',methods=("GET","POST"))
def set_user_infor():
    if request.method == 'POST':
        userid = g.user[0]
        password = request.form["password"]
        gender = request.form["gender"]
        birthday = request.form['birthday']
        db = get_db()
        db.execute("UPDATE user SET password=?,Gender=?,Birthday=? Where UserID = ?",
                    (generate_password_hash(password),gender,birthday,userid,))
        db.commit()
        return "change successful"
    
    return "error"

@bp.route('/get_user_love')
def get_user_love():
    user_id = session.get('user_id')
    db = get_db()
    res = db.execute('SELECT * FROM user_loves_store WHERE UserId=?',
                (user_id,)).fetchall()
    def dict_factory(cursor, row):  
        d = {}  
        for idx, col in enumerate(cursor.description):  
            d[col[0]] = row[idx]  
        return d      
    db.row_factory = dict_factory

    return jsonify(res)


@bp.route('/set_user_love',methods=("GET","POST"))
def set_user_love():
    if request.method == 'POST':
        user_id = session.get('user_id')
        store_id = request.form['storeid']
        db = get_db()
        
        res = db.execute('SELECT * FROM user_loves_store WHERE user_UserId=? and store_StoreId=?',
            (user_id,store_id,)).fetchone()
        if not res:
            db.execute(
                'INSERT INTO user_loves_store (store_StoreId, user_UserId) VALUES(?,?)',
                (store_id, user_id,)
            ) 
            db.commit()
        else:
            db.execute(
                'DELETE FROM user_loves_store WHERE ID=?',
                (res['ID'],)
            ) 
            db.commit()
        return 'success'
    return 'error'