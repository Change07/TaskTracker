import java.util.ArrayList;
import java.nio.file.StandardOpenOption;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class TaskTracker{
    public static ArrayList<Task> TO_DO = new ArrayList<>();
    public static ArrayList<Task> IN_PROGRESS = new ArrayList<>();
    public static ArrayList<Task> DONE = new ArrayList<>();
    public static Path TASK_FILE_PATH = Path.of("tasks.json");

    public static void loadFile(){
        try(BufferedReader​ reader = Files.newBufferedReader(TASK_FILE_PATH)){
            String line; String taskCategory; String[] token; String[] tasksToLoad;
            
            reader.skip(1);
            while( (line = reader.readLine()) != null ){
                
                token = line.split("\\[");
                if (token.length > 1){
                    taskCategory = token[0].split(":")[0].trim().replaceAll("\"", "");

                    //System.out.println(token[1]);
                    tasksToLoad = token[1].replaceAll("\\]", "").trim().split("\\},\\s*\\{");

                    for (int i=0; i<tasksToLoad.length;i++){
                        tasksToLoad[i] = tasksToLoad[i].replaceAll("[{}]", "");
                        //System.out.println("---> "+tasksToLoad[i]);
                    }
                    
                    switch (taskCategory){
                        case "todo" -> { loadFileInTODO(tasksToLoad);}
                        case "in-progress" -> { loadFileToINPROGRESS(tasksToLoad); }
                        case "done" -> { loadFileTODONE(tasksToLoad); }
                    }
                }
            }

            Files.writeString(
                TASK_FILE_PATH,
                "",
                StandardOpenOption.WRITE,
                StandardOpenOption.DELETE_ON_CLOSE
            );

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private static void loadFileInTODO(String[] tasks){
        System.out.println("---About to load Tasks---");
        Task myTask = null;

        for (String task : tasks){
            myTask = new Task();
            setValue(task, myTask);
            TO_DO.add(myTask);
            // System.out.println(TO_DO.get(0)+"\n");
        }

    }

    private static void loadFileToINPROGRESS(String[] tasks){
        System.out.println("---About to load Tasks---");
        Task myTask = null;

        for (String task : tasks){
            myTask = new Task();
            setValue(task, myTask);
            IN_PROGRESS.add(myTask);
        }
    }

    private static void loadFileTODONE(String[] tasks){
        System.out.println("---About to load Tasks---");
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
            
            //System.out.printf("1. %s\n2. %s\n3. %s\n", TaskTracker.TO_DO.get(0).toString(),TaskTracker.DONE.get(0).toString(), TaskTracker.IN_PROGRESS.get(0).toString());
        }catch(IOException e){
            System.out.println("Problem loading file... "+e.getMessage());
        }

        TaskTracker.saveToFile();
    }

    public static void saveToFile(){
        try{
            Files.writeString(TASK_FILE_PATH, "{\n", StandardOpenOption.WRITE, StandardOpenOption.CREATE);

            saveTodoTasks();
            Files.writeString(TASK_FILE_PATH, ",\n", StandardOpenOption.APPEND);
            saveInProgressTasks();
            Files.writeString(TASK_FILE_PATH, ",\n", StandardOpenOption.APPEND);
            saveDoneTasks();

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


// read json file and store task in three arrayLists
    // check if the file exist first
        // create a method to read from the json and store task obj to arraylist
    // create a new json file if no json file exits

// read user commands and perform action and perform action