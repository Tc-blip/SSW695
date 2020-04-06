from flask import (
    Blueprint, flash, g, redirect, jsonify, request, session, url_for
)
from werkzeug.security import check_password_hash, generate_password_hash
from server.db import get_db
bp = Blueprint('shop', __name__, url_prefix='/shop')

@bp.route('/')
def hello():
    return 'Hello, owner world!'

@bp.route('/create_shop', methods=("GET", "POST"))
def create_shop():
    if request.method == 'POST':
        store_name = request.form["storename"]
        store_latitude = request.form["latitute"]
        store_longitude = request.form['longitude']
        store_description = request.form['description']
        print(store_name)
        db = get_db()
        db.execute(
            "INSERT INTO store (StoreName, StoreLatitude, StoreLongitude, StoreDescription) VALUES (?,?,?,?)",
            (store_name,store_latitude,store_longitude,store_description),
        )
        db.commit()

        store_id = db.execute('SELECT last_insert_rowid()').fetchone()[0]
        user_id = session.get('user_id')
        db.execute("INSERT INTO store_has_storeowner (StoreId,StoreOwnerId) VALUES(?,?)",
                (store_id, user_id)
        )
        db.commit()
        return "create a shop"

@bp.route('/get_shop_infor')
def get_shop_infor():
    db = get_db()
    user_id = session.get('user_id')
    
    def dict_factory(cursor, row):  
        d = {}  
        for idx, col in enumerate(cursor.description):  
            d[col[0]] = row[idx]  
        return d      
    db.row_factory = dict_factory

    infor = db.execute(
        "select * from store LEFT \
            JOIN store_has_storeowner on store.StoreId = store_has_storeowner.StoreId \
            where StoreOwnerId = ?",
        (user_id,)
        ).fetchall()

    return jsonify(infor)


@bp.route('/set_shop_infor',methods=("GET","POST"))
def set_shop_infor():
    if request.method == 'POST':
        shop_id = request.form['shop_id']
        store_name = request.form["storename"]
        store_latitude = request.form["latitute"]
        store_longitude = request.form['longitude']
        store_description = request.form['description']
        db = get_db()
        db.execute("UPDATE store SET StoreName=?,StoreLatitude=?,StoreLongitude=?,StoreDescription=? Where StoreId = ?",
                  (store_name,store_latitude,store_longitude,store_description,shop_id))
        db.commit()
        return "change successful"
    return "error"

