import os
import urllib.request
from flask import (
    Blueprint, flash, g, redirect, jsonify, request, session, url_for,current_app,make_response
)
import time
from werkzeug.utils import secure_filename
from server.db import get_db

bp = Blueprint('upload', __name__, url_prefix='/upload')
ALLOWED_EXTENSIONS = set(['txt', 'pdf', 'png', 'jpg', 'jpeg', 'gif'])


def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

@bp.route('/file-upload', methods=("GET", "POST"))
def upload_file():
    # check if the post request has the file part
    if 'file' not in request.files:
        resp = jsonify({'message' : 'No file part in the request'})
        resp.status_code = 400
        return resp
    file = request.files['file']
    if file.filename == '':                 #if no file be selected
        resp = jsonify({'message' : 'No file selected for uploading'})
        resp.status_code = 400
        return resp
    if file and allowed_file(file.filename):        #if right file, then go to save 
        filename = secure_filename(file.filename)                          
        ext = filename.rsplit('.', 1)[1]                            #get file name
        unix_time = int(time.time())                                
        new_filename = str(unix_time) + '.' + ext                   #rename file name for Unique file name                
        file.save(os.path.join(current_app.config['UPLOAD_FOLDER'], new_filename))  #save to static/images
        resp = jsonify({'message' : 'File successfully uploaded'})
        resp.status_code = 201
        return resp
    else:
        resp = jsonify({'message' : 'Allowed file types are txt, pdf, png, jpg, jpeg, gif'})
        resp.status_code = 400
        return resp

@bp.route('/file_show',methods=("GET","POST"))
def show_images():
    file_name = request.form['file_name']
    ext = file_name.rsplit('.', 1)[1]
    image_data = open(os.path.join(current_app.config['UPLOAD_FOLDER'], file_name), "rb").read()
    response = make_response(image_data)
    response.headers['Content-Type'] = f'image/{ext}'
    return response