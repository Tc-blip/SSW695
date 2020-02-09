from flask import Flask, Response, jsonify

class MyResponse(Response):
    @classmethod
    def force_type(cls, response, environ=None):
        if isinstance(response, (list, dict)):
            response = jsonify(response)
        return super(Response, cls).force_type(response, environ)

class MyFlask(Flask):
    response_class = MyResponse

app = MyFlask(__name__)

@app.route('/')
def root():
    t = {
        'a': 1,
        'b': 2,
        'c': [3, 5, 5]
    }
    return t

if __name__ == '__main__':
    app.debug = True
    app.run()