import mysql.connector


def connection():
    db1 = mysql.connector.connect(user='root', password='tiancheng',
                        host='127.0.0.1',
                        database='mydb')

    return db1


