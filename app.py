from db import db, User, Note
from flask import Flask, request
import json
import datetime

app = Flask(__name__)
db_filename = "notes.db"

app.config["SQLALCHEMY_DATABASE_URI"] = "sqlite:///%s" % db_filename
app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False
app.config["SQLALCHEMY_ECHO"] = False

db.init_app(app)
with app.app_context():
    db.create_all()


# generalized response formats
def success_response(data, code=200):
    return json.dumps(data), code


def failure_response(message, code=404):
    return json.dumps({"error": message}), code


# ----------- User routes -------------


@app.route("/api/users/", methods=["GET"])
def get_all_users():
    users = User.query.all()
    res = {"users": [note.serialize() for note in users]}
    return success_response(res)


@app.route("/api/users/", methods=["POST"])
def create_user():
    body = json.loads(request.data)

    username = body.get("username")
    password = body.get("password")

    if username is None or password is None:
        return failure_response("Correctly fill in 'username' and 'password' fields", 400)
    
    user = User(username=username, password=password)

    db.session.add(user)
    db.session.commit()

    res = user.serialize()

    return success_response(res, 201)


@app.route("/api/users/<int:user_id>/")
def get_specific_user(user_id):
    user = User.query.filter_by(id=user_id).first()
    if user is None:
        return failure_response("user not found, check id")
    res = user.serialize()
    return success_response(res)


@app.route("/api/users/<int:user_id>/", methods=["DELETE"])
def delete_user(user_id):
    res = delete_note_helper(user_id)
    return res



# ---------- Notes Helpers ------------

def create_note_helper(title, content, date, user_id):
    note = Note(title=title, content=content, date=date, user_id=user_id)

    db.session.add(note)
    db.session.commit()

    return note.serialize()

def update_note_helper(note_id, title, content):
    note = Note.query.filter_by(id=note_id).first()

    if note is None:
        return failure_response("note not found, check note id")
    
    note.title = title
    note.content = content

    db.session.commit()

    return note.serialize()

def delete_note_helper(note_id):
    note = Note.query.filter_by(id=note_id).first()

    if note is None:
        return failure_response("note not found, check note id")
    
    res = note.serialize()

    db.session.delete(note)
    db.session.commit()

    return success_response(res)

# ----------- Notes routes ------------

# get all notes
@app.route("/api/notes/", methods=["GET"])
def get_all_notes():
    notes = Note.query.all()

    res = {"notes": [note.serialize() for note in notes]}

    return success_response(res)


# get specific note
@app.route("/api/notes/<int:note_id>/", methods=["GET"])
def get_note(note_id):
    note = Note.query.filter_by(id=note_id).first()

    if note is None:
        return failure_response("note not found, check id")
    
    res = note.serialize()
    
    return success_response(res)


# create note, take in title, content, and user_id
# should we allow empty notes? I am right now
@app.route("/api/notes/", methods=["POST"])
def create_note():
    
    body = json.loads(request.data)

    title = body.get("title")
    content = body.get("content")
    user_id = body.get("user_id")

    if user_id is None:
        return failure_response("user_id is required")
    
    user = User.query.filter_by(id=user_id).first()

    if user is None:
        return failure_response("user not found, check user_id")

    date = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")

    
    res = create_note_helper(title, content, date, user_id)

    return success_response(res, 201)
    

# update note, take in title and content 
# (these will be updated no matter what so we need to pass in both even if only one has been changed)
# this can easily be changed if that makes it easier
@app.route("/api/update-note/<int:note_id>/", methods=["POST"])
def update_note(note_id):

    body = json.loads(request.data)

    title = body.get("title")
    content = body.get("content")
    
    note = Note.query.filter_by(id=note_id).first()

    if note is None:
        return failure_response("note not found, check note id")
    
    res = update_note_helper(note_id, title, content)

    return success_response(res)


# delete note
@app.route("/api/notes/<int:note_id>/", methods=["DELETE"])
def delete_note(note_id):
    
    res = delete_note_helper(note_id)

    return res

# get all notes for a user
@app.route("/api/users/<int:user_id>/notes/", methods=["GET"])
def get_all_notes_for_user(user_id):
    
    user = User.query.filter_by(id=user_id).first()

    if user is None:
        return failure_response("user not found, check user_id")
    
    notes = Note.query.filter_by(user_id=user_id).all()

    res = {"notes": [note.serialize() for note in notes]}

    return success_response(res)


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=8000, debug=True)
