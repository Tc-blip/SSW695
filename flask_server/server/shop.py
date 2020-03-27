import mysql.connector
from flask import (
    Blueprint, flash, g, redirect, jsonify, request, session, url_for
)
from werkzeug.security import check_password_hash, generate_password_hash

bp = Blueprint('shop', __name__, url_prefix='/shop')

db1 = mysql.connector.connect(user='root', password='asdf1234',
                        host='127.0.0.1',
                        database='mydb')

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
        db = db1.cursor()
        db.execute(
            "INSERT INTO store (StoreName, StoreLatitude, StoreLongitude, StoreDescription) VALUES (%s,%s,%s,%s)",
            (store_name,store_latitude,store_longitude,store_description),
        )
        db1.commit()

        db.execute('SELECT LAST_INSERT_ID()')
        store_id = db.fetchone()[0]
        user_id = session.get('user_id')
        db.execute("INSERT INTO store_has_storeowner (store_storeId,storeowner_StoreOwnerId) VALUES(%s,%s)",
                (store_id, user_id)
        )
        db1.commit()
        return "create a shop"

@bp.route('/get_shop_infor')
def get_shop_infor():
    
    db = db1.cursor(dictionary=True, buffered=True)
    user_id = session.get('user_id')
    db.execute(
        "SELECT * FROM store_has_storeowner WHERE storeowner_StoreOwnerId=%s",
        (user_id,)
        )
    return jsonify(db.fetchall())

