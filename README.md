Task Retrieval Program (Java)
This Java program interacts with a task management system via HTTP endpoints to retrieve a list of tasks. It fetches tasks from an API, optionally filtering tasks by parameters such as status, due date, and priority. The program handles errors appropriately and supports API token authentication for secure access to the endpoints.

Features
Retrieve tasks using HTTP GET requests.
Filter tasks based on parameters such as status, due_date, and priority.
Authentication using Bearer token.
Handle various HTTP errors, such as timeouts, 4xx, and 5xx status codes.
Output task details, such as task name, description, status, and due date.
Requirements
Java 8 or higher
Apache HttpClient (for HTTP requests)
JSON processing library (such as org.json or Jackson)
