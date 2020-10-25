# metrics-cloud
This project creates a Word Cloud of the most commonly occurring headers in error messages.

The point of this is that it's nice for OPS teams to visualize which categories are causing problems. This allows them to quickly identify where a problem lies and ensure the correct team is contacted.

Also this project is an exercise in Reactive Streams so that's the technique used to generate the error messages.

# See word cloud
Using a browser, navigate to localhost:8080

# WordCloud2.js
I'm using WordCloud2.js for rendering the word cloud.
Code: https://github.com/timdream/wordcloud2.js/blob/HEAD/API.md