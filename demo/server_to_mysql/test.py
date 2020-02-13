import mysql.connector

cnx = mysql.connector.connect(user='root', password='tiancheng',
                              host='127.0.0.1',
                              database='mydb')

#cnx = mysql.connector.connect(user='root', database='mydb')
cursor = cnx.cursor()


add_employee = ("INSERT INTO user "
               "(UserId, Username, Password, Gendar) "
               "VALUES (10001, 'yukait', 'sdf1234', 'M')")

cursor.execute(add_employee)



cnx.commit()

cursor.close()
cnx.close()
