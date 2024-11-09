package portfolio.httpapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.plugin.json.JsonMapper;

import org.jetbrains.annotations.NotNull;

/**
 * Exercise 1
 * <p>
 * Application Server for the Tasks API
 */
public class TasksAppServer {
    private static final TasksDatabase database = new TasksDatabase();

    private final Javalin appServer;

    /**
     * Create the application server and configure it.
     */
    public TasksAppServer() {
        this.appServer = Javalin.create(config -> {
            config.defaultContentType = "application/json";
            config.jsonMapper(createGsonMapper());
        });

        this.appServer.get("/tasks", this::getAllTasks);
        this.appServer.get("/task/{id}", this::getOneTask);
        this.appServer.post("/task", this::addTask);

    }

    /**
     * Use GSON for serialisation instead of Jackson
     * because GSON allows for serialisation of objects without noargs constructors.
     *
     * @return A JsonMapper for Javalin
     */
    private static JsonMapper createGsonMapper() {
        Gson gson = new GsonBuilder().create();
        return new JsonMapper() {
            @NotNull
            @Override
            public String toJsonString(@NotNull Object obj) {
                return gson.toJson(obj);
            }

            @NotNull
            @Override
            public <T> T fromJsonString(@NotNull String json, @NotNull Class<T> targetClass) {
                return gson.fromJson(json, targetClass);
            }
        };
    }

    /**
     * Start the application server
     *
     * @param port the port for the app server
     */
    public void start(int port) {
        this.appServer.start(port);
    }

    /**
     * Stop the application server
     */
    public void stop() {
        this.appServer.stop();
    }

    /**
     * Get all tasks
     *
     * @param context the server context
     */
    private void getAllTasks(Context context) {
        context.contentType("application/json");
        context.json(database.all());
    }


    private void getOneTask(Context context){
        int id = Integer.parseInt(context.pathParam("id"));
        context.contentType("application/json");
        Task id_data = database.get(id);

        if(id_data != null){
            context.json(id_data);
        }else{
            context.status(404).result("Not found");
        }
    }


    private void addTask(Context context){
        int taskID = context.bodyAsClass(Task.class).getId();
        String taskDescription = context.bodyAsClass(Task.class).getDescription();
        System.out.println(taskID);
        System.out.println(taskDescription);
    
        Task add = new Task(taskID, taskDescription);
        // TasksDatabase created = new TasksDatabase();
    
        if(database.add(add)){
            context.res.setStatus(201);
            context.res.setHeader("Location", "/task/" + add.getId());
        }else{
            context.status(400);
        }
    }
    
}


