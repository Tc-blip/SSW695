from flask import (
    Blueprint, flash, g, redirect, jsonify, request, session, url_for
)
from werkzeug.security import check_password_hash, generate_password_hash

from server.db import get_db
bp = Blueprint('shop_owner', __name__, url_prefix='/shop_owner')


@bp.route('/get_shop_owner_infor')
def get_user_infor():
    if g.user:
        dict = {"StoreOwnerId": g.user[0],
                "UserName": g.user[1],
                "CreateTime":g.user[3],
                "Gender": g.user[4],
                "Birthday": g.user[5]}

        return jsonify(dict)

    else:
        return "error"

@bp.route('/set_shop_owner_infor',methods=("GET","POST"))
def set_user_infor():
    if request.method == 'POST':
        username = session.get('user_id')
        password = request.form["password"]
        gender = request.form["gender"]
        birthday = request.form['birthday']
        db = get_db()
        db.execute(
                "UPDATE storeowner SET password=?,Gender=?,Birthday=? Where UserId = ?",
                (generate_password_hash(password),gender,birthday,username,)
                )
        db.commit()
        return "change successful"
    
    return "error"

