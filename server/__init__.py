"""Base objects for the server."""
from flask import Flask

app = Flask(__name__)

from . import view
from . import endpoints
