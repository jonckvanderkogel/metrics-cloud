# metrics-cloud
This project creates a Word Cloud of the most commonly occurring headers in error messages.

The point of this is that it's nice for OPS teams to visualize which categories are causing problems. This allows them to quickly identify where a problem lies and ensure the correct team is contacted.

Also this project is an exercise in Reactive Streams so that's the technique used to generate the error messages.

# Test websocket
To test if the websocket is actually receiving data:
In the root of the project start a webserver which will serve the index.html file:
python -m SimpleHTTPServer 8000

Using a browser, navigate to localhost:8000