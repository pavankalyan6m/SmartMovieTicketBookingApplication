import spacy
from spacy.matcher import PhraseMatcher
from flask import Flask, request, jsonify
import requests
import logging
from flask_cors import CORS

app = Flask(__name__)

# Apply CORS only once with the desired configurations
CORS(app, origins=["http://localhost:3000"], resources={r"/process_input": {"origins": "*"}})

# Set up basic logging
logging.basicConfig(level=logging.INFO)
app.logger.setLevel(logging.INFO)

# Load the small English model from spaCy
try:
    nlp = spacy.load("en_core_web_sm")
except Exception as e:
    app.logger.error(f"Error loading spaCy model: {e}")
    exit("Failed to load spaCy model.")

# Define a list of possible movie genres
GENRES = [
    "action", "comedy", "drama", "horror", "sci-fi", "romance", "adventure",
    "thriller", "fantasy", "animation", "romantic comedy"
]

# Initialize the PhraseMatcher with the shared vocabulary
matcher = PhraseMatcher(nlp.vocab)

# Add the genres to the matcher
patterns = [nlp.make_doc(genre) for genre in GENRES]
matcher.add("Genres", None, *patterns)

# URL of the Java backend
JAVA_BACKEND_URL = "http://localhost:8001/movies/search-by-synopsis"  # Replace with the actual Java backend URL

# Define response for greeting and movie-related questions
USER_NAME = "Maya"
SMALL_TALK_RESPONSES = {
    "hello": f"Hello, I'm {USER_NAME}! How can I assist you today?",
    "hi": f"Hi there, I'm {USER_NAME}! Looking for something to watch?",
    "hey": f"Hey, I'm {USER_NAME}! Looking for a movie recommendation?",
    "goodbye": f"Goodbye, have a great day!",
    "suggest movies": "Sure! Please tell me your favorite movie genre, and I will suggest some movies.",
    "recommend movies": "I’d love to! Just let me know what genre you're in the mood for.",
    "show me some movies": "Of course! What kind of movie would you like to watch today?",
    "suggest a movie": "What kind of movie are you in the mood for? I'll recommend some options.",
    "yeah": f"Great! What genre are you in the mood for? Just let me know!",
    "recommend good movies": "I'd love to recommend some movies! Please tell me your favorite genre."
}

@app.route('/process_input', methods=['POST'])
def process_input():
    """Processes user input to detect small talk, genres, and communicate with the Java backend."""
    try:
        data = request.get_json()
        if not data or 'input' not in data:
            return jsonify({"error": "Invalid input data. Provide an 'input' field."}), 400
        user_input = data.get("input", "").strip().lower()

        # Log the user input for debugging
        app.logger.info(f"Received user input: '{user_input}'")
        
        if not user_input:
            return jsonify({"error": "Empty input provided."}), 400

        # Check for small talk responses (flexible matching)
        matched_response = None
        for phrase, response in SMALL_TALK_RESPONSES.items():
            app.logger.info(f"Checking if '{user_input}' matches small talk phrase '{phrase}'")
            if phrase in user_input:  # Allow for partial matches
                matched_response = response
                break

        # If a small talk phrase is matched, return the response
        if matched_response:
            app.logger.info(f"Matched small talk phrase: {user_input}")
            return jsonify({"message": matched_response})

        # If no small talk phrase matches, continue with genre detection
        doc = nlp(user_input)
        detected_genres = {doc[start:end].text.lower() for match_id, start, end in matcher(doc)}

        # Log detected genres for debugging
        app.logger.info(f"Detected genres: {detected_genres}")

        if not detected_genres:
            # If no genres detected, treat the input as small talk
            app.logger.info(f"No genres detected. Treating as small talk.")
            return jsonify({
                "message": "No genres detected. Can you please tell me your favorite genre or ask me about movies?"
            })

        # Send detected genres to the Java backend to get movie suggestions
        response = send_to_java_backend(list(detected_genres))
        
        # Log the Java backend response
        app.logger.info(f"Response from Java backend: {response.status_code} - {response.text}")

        if response.status_code == 200:
            movie_data = response.json()  # Assuming the Java backend returns a list of movies in JSON format
            if movie_data:
                return jsonify({
                    "message": "Here are some movies I found for you:",
                    "movies": movie_data  # Send the movie data in the response
                })
            else:
                return jsonify({
                    "message": "I couldn't find any movies matching your input. Try again!"
                })
        else:
            app.logger.error(f"Java backend returned error: {response.status_code} - {response.text}")
            return jsonify({
                "error": f"Java backend error: {response.status_code}",
                "details": response.text
            }), 500

    except Exception as e:
        app.logger.error(f"Error processing input: {e}")
        return jsonify({"error": f"Error processing input: {e}"}), 500

def send_to_java_backend(genres):
    """
    Sends the detected genres to the Java backend via a GET request.
    """
    keywords = ",".join(genres)  # Ensure genres are comma-separated
    params = {"keywords": keywords}  # Pass keywords as a query parameter
    headers = {"Content-Type": "application/json"}
    response = requests.get(JAVA_BACKEND_URL, params=params, headers=headers)
    return response

if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000, debug=True)
