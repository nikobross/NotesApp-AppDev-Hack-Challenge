from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()

class User(db.Model):
    pass

class Note(db.Model):
    pass