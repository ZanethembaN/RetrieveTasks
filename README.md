# Task Retrieval Program (Java)

## Overview
The **Task Retrieval Program** is a Java application designed to interact with a task management system via HTTP endpoints to retrieve, filter, and display task details. It fetches tasks from an API, allowing users to filter tasks by parameters such as status, due date, and priority. The program supports API token authentication for secure access to the endpoints and handles various HTTP errors gracefully.

## Features

- **Retrieve Tasks**: Fetch tasks using HTTP GET requests from an API.
- **Task Filtering**: Filter tasks based on parameters like status, due date, and priority.
- **Authentication**: Use Bearer token for secure authentication when accessing the API endpoints.
- **Error Handling**: Proper handling of HTTP errors, including timeouts, 4xx, and 5xx status codes.
- **Output**: Display task details such as name, description, status, and due date in a readable format.

## Requirements

To run this program, you will need the following:

- **Java 8** or higher
- **Apache HttpClient** library for making HTTP requests
- **JSON processing library** (such as `org.json` or `Jackson`) for parsing JSON data from the API

### Libraries

- **Apache HttpClient**: Used to send HTTP requests and handle responses.
- **JSON Processing Library**: Used to parse and process JSON data from the API (e.g., `org.json`, `Jackson`, etc.).


### Installation


**Clone the repository**:
```bash
 git clone https://github.com/ZanethembaN/RetrieveTasks.git
cd RetrieveTasks
