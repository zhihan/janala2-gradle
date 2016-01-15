from . import app
from flask import render_template

@app.route('/')
@app.route('/index')
@app.route('/stopped')
def stopped():
    running = True
    title = 'Stopped'

    return render_template('index.html',
                           title=title,
                           running=False)


@app.route('/running')
def running():
    title = 'Running a test'

    return render_template('index.html',
                           title=True,
                           running=running)
