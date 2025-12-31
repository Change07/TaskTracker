package main;

import java.util.ArrayList;
import java.nio.file.StandardOpenOption;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import utils.Task;
import utils.Utilities;

public class TaskTracker{
    public static ArrayList<Task> TO_DO = new ArrayList<>();
    public static ArrayList<Task> IN_PROGRESS = new ArrayList<>();
    public static ArrayList<Task> DONE = new ArrayList<>();
    public static ArrayList<Integer> EXISTING_IDs = new ArrayList<>();

    public static Path TASK_FILE_PATH = Path.of("tasks.json");

    public static void loadFile(){
        try(BufferedReader​ reader = Files.newBufferedReader(TASK_FILE_PATH)){
            String line; String taskCategory; String[] token; String[] tasksToLoad;
            
            reader.skip(1);
            while( (line = reader.readLine()) != null ){
                
                token = line.split("\\[");
                if (token.length > 1){
                    taskCategory = token[0].split(":")[0].trim().replaceAll("\"", "");
                    tasksToLoad = token[1].replaceAll("\\]", "").trim().split("\\},\\s*\\{");

                    for (int i=0; i<tasksToLoad.length;i++){
                        tasksToLoad[i] = tasksToLoad[i].replaceAll("[{}]", "");
                    }
                    
                    switch (taskCategory){
                        case "todo" -> { loadFileInTODO(tasksToLoad);}
                        case "in-progress" -> { loadFileToINPROGRESS(tasksToLoad); }
                        case "done" -> { loadFileTODONE(tasksToLoad); }
                    }
                }
            }

            Files.writeString(
                TASK_FILE_PATH, "", StandardOpenOption.WRITE, StandardOpenOption.DELETE_ON_CLOSE);

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private static void loadFileInTODO(String[] tasks){
        Task myTask = null;

        for (String task : tasks){
            myTask = new Task();
            setValue(task, myTask);
            TO_DO.add(myTask);
        }

    }

    private static void loadFileToINPROGRESS(String[] tasks){
        Task myTask = null;

        for (String task : tasks){
            myTask = new Task();
            setValue(task, myTask);
            IN_PROGRESS.add(myTask);
        }
    }

    private static void loadFileTODONE(String[] tasks){
        Task myTask = null;

        for (String task : tasks){
            myTask = new Task();
            setValue(task, myTask);
            DONE.add(myTask);
        }
    }

    private static void setValue(String task, Task myTask){
        String[] taskAttributes; String[] taskTokens;
        
        taskAttributes = task.split(",");
        for (String t : taskAttributes){
            taskTokens = t.replaceAll("\"", "").split(":");
            if (taskTokens[0].trim().equals("id")){
                EXISTING_IDs.add(Integer.valueOf(taskTokens[1].trim()));
                myTask.setID(Integer.parseInt(taskTokens[1].trim()));
            }
            else if(taskTokens[0].trim().equals("description")){
                myTask.setDescription(taskTokens[1].trim());
            }
            else if (taskTokens[0].trim().equals("createdAt")){
                myTask.setCreateTime((taskTokens[1].trim()+":"+taskTokens[2]+":"+taskTokens[3]));
            }
            else if (taskTokens[0].trim().equals("updatedAt")){
                myTask.setUpdateTime((taskTokens[1].trim()+":"+taskTokens[2]+":"+taskTokens[3]));
            }
            else if (taskTokens[0].trim().equals("status")){
                myTask.setStatus(taskTokens[1].trim());
            }
        }
    }

    public static void main(String[] args){
        // Creates the file if it doesnt exits
        try{
            Files.writeString(TASK_FILE_PATH, "", StandardOpenOption.CREATE);

            TaskTracker.loadFile();

        }catch(IOException e){
            System.out.println("Problem loading file... "+e.getMessage());
        }


        // handling user input
        String action = args[0];
        if (action != null){
            switch (action){
                case "add" -> {Utilities.addTask(args[1].replaceAll("\"", ""), TO_DO, EXISTING_IDs);}
                case "update" -> {Utilities.updateTask(Integer.parseInt(args[1]), args[2].replaceAll("\"", ""), TO_DO, IN_PROGRESS, DONE);}
                case "delete" -> {Utilities.deleteTask(Integer.parseInt(args[1]), TO_DO, IN_PROGRESS, DONE);}
                case "mark-in-progress" -> {Utilities.markInProgress(Integer.parseInt(args[1]), TO_DO, IN_PROGRESS, DONE);}
                case "mark-done" -> {Utilities.markAsDone(Integer.parseInt(args[1]), TO_DO, IN_PROGRESS, DONE);}
                case "list" -> {
                    if (args.length < 2)
                        Utilities.listAll(TO_DO, IN_PROGRESS, DONE);
                    else{
                        switch(args[1]){
                            case "todo" -> {Utilities.listTodo(TO_DO);}
                            case "in-progress" -> {Utilities.listInProgress(IN_PROGRESS);}
                            case "done" -> {Utilities.listDone(DONE);}
                        }
                    }
                }
            }
        }

        // Save task to json file
        TaskTracker.saveToFile();
    }

    public static void saveToFile(){
        try{
            Files.writeString(TASK_FILE_PATH, "{\n", StandardOpenOption.WRITE, StandardOpenOption.CREATE);

            // save task with todo status
            if(!TO_DO.isEmpty()) {
                saveTodoTasks();
                Files.writeString(TASK_FILE_PATH, ",\n", StandardOpenOption.APPEND);
            }

            // save tasks with in-progress status
            if(!IN_PROGRESS.isEmpty()){
                saveInProgressTasks();
                Files.writeString(TASK_FILE_PATH, ",\n", StandardOpenOption.APPEND);
            }

            // save tasks with done status
            if(!DONE.isEmpty()){
                saveDoneTasks();
            }

            Files.writeString(TASK_FILE_PATH, "\n}", StandardOpenOption.APPEND);
        }catch(IOException e){
            System.out.println("Issue writing to file... "+e.getMessage());
        }
        
    }

    private static void saveTodoTasks(){
        try{
            Files.writeString(
                TASK_FILE_PATH,
                "\"todo\" : [",
                StandardOpenOption.APPEND
            );
        }catch(IOException e){
            System.out.println("Issue writing to file... "+e.getMessage());
        }

        try(BufferedWriter writer = Files.newBufferedWriter(TASK_FILE_PATH, StandardOpenOption.APPEND)){
            for (int i=0; i < TO_DO.size(); i++){
                if (i==TO_DO.size()-1) {writer.write(TO_DO.get(i).toString()+"]");}
                else{
                    writer.write(TO_DO.get(i).toString()+", ");
                }
            }
        }catch(IOException e){
            System.out.println("Issue writing to file... "+e.getMessage());
        }
    }

    private static void saveDoneTasks(){
        try{
            Files.writeString(
                TASK_FILE_PATH,
                "\"done\" : [",
                StandardOpenOption.APPEND
            );
        }catch(IOException e){
            System.out.println("Issue writing to file... "+e.getMessage());
        }

        try(BufferedWriter writer = Files.newBufferedWriter(TASK_FILE_PATH, StandardOpenOption.APPEND)){
            for (int i=0; i < DONE.size(); i++){
                if (i==DONE.size()-1) {writer.write(DONE.get(i).toString()+"]");}
                else{
                    writer.write(DONE.get(i).toString()+", ");
                }
            }
        }catch(IOException e){
            System.out.println("Issue writing to file... "+e.getMessage());
        }
    }

    private static void saveInProgressTasks(){
        try{
            Files.writeString(
                TASK_FILE_PATH,
                "\"in-progress\" : [",
                StandardOpenOption.APPEND
            );
        }catch(IOException e){
            System.out.println("Issue writing to file... "+e.getMessage());
        }

        try(BufferedWriter writer = Files.newBufferedWriter(TASK_FILE_PATH, StandardOpenOption.APPEND)){
            for (int i=0; i < IN_PROGRESS.size(); i++){
                if (i==IN_PROGRESS.size()-1) {writer.write(IN_PROGRESS.get(i).toString()+"]");}
                else{
                    writer.write(IN_PROGRESS.get(i).toString()+", ");
                }
            }
        }catch(IOException e){
            System.out.println("Issue writing to file... "+e.getMessage());
        }
    }    
}