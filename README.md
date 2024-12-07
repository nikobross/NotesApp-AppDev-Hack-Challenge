# Simple Notes - AppDev Hack Challenge
#### The one-place-stop to share notes with others
Our app is an intuitive note-taking platform that allows users to create, edit, and organize their personal notes effortlessly. Specifically, the app allows you to create/delete different users. Additionally, for each user, you can create, edit, and delete notes.

<img src="https://github.com/user-attachments/assets/0343f3ef-8b65-40ea-a5b1-af843be52d6d" alt="Home Screen" width="200" />
<img src="https://github.com/user-attachments/assets/77319896-6478-4298-99d2-8fefd341ea68" alt="Users Screen" width="200" />
<img src="https://github.com/user-attachments/assets/17e80052-18c1-4bdd-b329-fc6d81ef6cc7" alt="User's Notes Screen" width="200" />
<img src="https://github.com/user-attachments/assets/fb136a3f-88bf-4e5c-84a2-e152a779b1d1" alt="Create Screen" width="200" />


## Frontend
The frontend is built using Jetpack Compose following the MVVM architecture, ensuring a clear separation of concerns with Models, ViewModels, and Repositories managing data and business logic. 
- APIService that calls flask backend APIs
- User Model and Note Model representing possible data from API calls
- Repository that have methods that act as wrappers for APIService

Furthermore, the app includes key screens such as:
- HomeScreen with animated introductions
- UsersScreen for listing and managing users
- CreateScreen for adding new notes
- UserNotesScreen for editing and deleting notes

Navigation is streamlined with a BottomNavigationBar that allows users to easily switch between Home, Users, and Create screens. Finally, networking and calling the backend APIs to read/modify users and notes data is handled efficiently using Retrofit, with StateFlow managing UI states to ensure the interface reacts seamlessly to data changes.


## Backend
The backend is built using the Flask framework, organized with Flask-RESTful and Blueprints to create a scalable and maintainable structure. It defines two primary database models, User and Note, managed via SQLAlchemy ORM, establishing a one-to-many relationship where each user can have multiple notes. The API includes comprehensive routes for user and note management, such as creating, retrieving, updating, and deleting users and notes, with endpoints like GET /users, POST /notes, and DELETE /users/<user_id>.


### Routes Implemented:
@app.route("/api/users/", methods=["GET"])
def get_all_users():

@app.route("/api/users/", methods=["POST"])
def create_user():

@app.route("/api/users/<int:user_id>/")
def get_specific_user(user_id):

@app.route("/api/users/<int:user_id>/", methods=["DELETE"])
def delete_user(user_id):

@app.route("/api/notes/", methods=["GET"])
def get_all_notes():

@app.route("/api/notes/<int:note_id>/", methods=["GET"])
def get_note(note_id):

@app.route("/api/notes/", methods=["POST"])
def create_note():

@app.route("/api/notes/update/<int:note_id>/", methods=["POST"])
def update_note(note_id):

@app.route("/api/notes/<int:note_id>/", methods=["DELETE"])
def delete_note(note_id):

@app.route("/api/users/<int:user_id>/notes/", methods=["GET"])
def get_all_notes_for_user(user_id):
