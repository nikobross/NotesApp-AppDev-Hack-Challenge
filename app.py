from db import db
from flask import Flask, request
import json

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


@app.route("/api/users", methods=["GET"])
def get_all_users():
    pass


@app.route("/api/users/", methods=["POST"])
def create_user():
    pass


@app.route("/api/users/<int:user_id>/")
def get_specific_user(user_id):
    pass


@app.route("/api/users/<int:user_id>/", methods=["DELETE"])
def delete_user(user_id):
    pass


# ----------- Notes routes ------------

# get all notes
@app.route("/api/notes/", methods=["GET"])
def get_all_notes():
    pass

# get specific note
@app.route("/api/notes/<int:note_id>/", methods=["GET"])
def get_note(note_id):
    pass

# create note
@app.route("/api/notes/", methods=["POST"])
def create_note():
    pass

# update note
@app.route("/api/notes/<int:note_id>/", methods=["POST"])
def update_note(note_id):
    pass

# delete note
@app.route("/api/notes/<int:note_id>/", methods=["DELETE"])
def delete_note(note_id):
    pass

# get all notes for a user
@app.route("/api/user/<int:user_id>/notes/", methods=["GET"])
def get_all_notes_for_user(user_id):
    pass


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=8000, debug=True)
