#Server Backend Setup Instructions

##Step 1
Clone the repo and navigate to the root directory of the server which should be `../CPEN442/term_project/server/lock_server`

##Step 2
Make a virtual environment for your python package installations. This assumes you already have Python3 and Pip3 installed
```
python3 -m venv env
source env/bin/activate
```

If you're on Windows run `env\Scripts\activate`

##Step 3
Install required dependencies by running `pip install -r requirements.txt`

##Step 4
Ask Mike for a copy of the current SQLite database and place it in the current directory (I'll come up with a better solution for this step at some point in the future)

##Step 5
Run `python3 manage.py migrate`. Ensure that there are no migration errors and no migrations remaining

##Step 6
To run the server, run `python3 manage.py runserver 8000` to have it launch on port 8000 of your machine.