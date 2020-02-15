import mysql.connector

cnx = mysql.connector.connect(user='root', password='tiancheng',
                              host='127.0.0.1',
                              database='mydb')

#cnx = mysql.connector.connect(user='root', database='mydb')

db = cnx.cursor()

username = 'yukaittt'
password = 'chengle'
db.execute("SELECT Username FROM user WHERE Username = %s", (username,))
res = db.fetchone()
if (res is not None ):
   print("User {0} is already registered.".format(username))

else:
    db.execute(
        "INSERT INTO user (username, password) VALUES (%s, %s)",
        (username, password),
    )
    cnx.commit()

    print( "successful")


'''
add_employee = ("INSERT INTO user "
               "(UserId, Username, Password, Gendar) "
               "VALUES (10002, 'tc', 'sdf1234', 'M')")



search_employee = ("SELECT * from user where Username = %s ")

cursor.execute(search_employee,('yukait',))

myresult = cursor.fetchall()

print(myresult)

cnx.commit()

cursor.close()
cnx.close()
'''
