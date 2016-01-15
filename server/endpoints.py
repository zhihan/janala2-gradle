from . import app
from flask import jsonify

@app.route('/result')
def result():
    return jsonify(test_id=1,
                   result='pass')
    
