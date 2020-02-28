from flask import (
    Blueprint, flash, g, redirect, render_template, request, url_for
)
from werkzeug.exceptions import abort
import mysql.connector

bp = Blueprint('business', __name__)

db1 = mysql.connector.connect(user='root', password='tiancheng',
                        host='127.0.0.1',
                        database='mydb')

