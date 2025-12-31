package utils;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Random;

public class Utilities{

    public static void addTask(String description, ArrayList<Task> TO_DO, ArrayList<Integer> EXISTING_IDs){
        Random rand = new Random(); int id = -1; boolean exist = true;
        
        while (id < 0 || exist){
            id = rand.nextInt();
            exist = exists(Integer.valueOf(id), EXISTING_IDs);
        }
        EXISTING_IDs.add(Integer.valueOf(id));
        Task newTask = new Task(id, description, "todo", LocalDateTime.now(), LocalDateTime.now());
        TO_DO.add(newTask);
        System.out.println("Task added successfully (ID: "+newTask.getID()+")");

    }

    private static boolean exists(Integer id, ArrayList<Integer> EXISTING_IDs){
        for (Integer i : EXISTING_IDs){
            if (i.equals(id)){
                return true;
            }
        }
        return false;
    }

    public static void updateTask (int id, String description, ArrayList<Task> TO_DO, ArrayList<Task> IN_PROGRESS, ArrayList<Task> DONE){
        Task taskToUpdate = findTask(id, TO_DO, IN_PROGRESS, DONE);
        if (taskToUpdate != null){
            taskToUpdate.updateDescription(description);
            System.out.println("task updated successfully at: "+taskToUpdate.getUpdateTime());
        }
        else{
            System.out.println("Task does not exist");
        }
    }
    private static Task findTask(int id, ArrayList<Task> TO_DO, ArrayList<Task> IN_PROGRESS, ArrayList<Task> DONE){
        for (Task t : TO_DO){
            if (t.getID()==id){return t;}
        }

        for (Task t : IN_PROGRESS){
            if (t.getID()==id){return t;}
        }

        for (Task t : DONE){
            if (t.getID()==id){return t;}
        }

        return null;
    }
    public static void deleteTask(int id, ArrayList<Task> TO_DO, ArrayList<Task> IN_PROGRESS, ArrayList<Task> DONE){
        Task taskToDelete = findTask(id, TO_DO, IN_PROGRESS, DONE);
        if (taskToDelete != null){
            String status = taskToDelete.getStatus();

            switch(status){
                case "todo" -> {TO_DO.remove(taskToDelete);}
                case "in-progress" -> {IN_PROGRESS.remove(taskToDelete);}
                case "done" -> {DONE.remove(taskToDelete);}
            }
            System.out.printf("Task (ID: %d) has been deleted.\n", taskToDelete.getID());
        }
    }
    public static void markInProgress(int id, ArrayList<Task> TO_DO, ArrayList<Task> IN_PROGRESS, ArrayList<Task> DONE){
        Task taskToMark = findTask(id, TO_DO, IN_PROGRESS, DONE);
        if (taskToMark != null){
            String status = taskToMark.getStatus();

            switch(status){
                case "todo" -> {
                    taskToMark.setStatus("in-progress");
                    IN_PROGRESS.add(taskToMark);
                    TO_DO.remove(taskToMark);
                    System.out.printf("Task (ID: %d) moved to in-progress status\n", taskToMark.getID());
                }
                case "done" -> {
                    taskToMark.setStatus("in-progress");
                    IN_PROGRESS.add(taskToMark);
                    DONE.remove(taskToMark);
                    System.out.printf("Task (ID: %d) moved to in-progress status\n", taskToMark.getID());
                }
            }
        }
    }
    public static void markAsDone(int id, ArrayList<Task> TO_DO, ArrayList<Task> IN_PROGRESS, ArrayList<Task> DONE){
        Task taskToMark = findTask(id, TO_DO, IN_PROGRESS, DONE);
        if (taskToMark != null){
            String status = taskToMark.getStatus();

            switch(status){
                case "todo" -> {
                    taskToMark.setStatus("done");
                    DONE.add(taskToMark);
                    TO_DO.remove(taskToMark);
                    System.out.printf("Task (ID: %d) moved to done status\n", taskToMark.getID());
                }
                case "in-progress" -> {
                    taskToMark.setStatus("done");
                    DONE.add(taskToMark);
                    IN_PROGRESS.remove(taskToMark);
                    System.out.printf("Task (ID: %d) moved to done status\n", taskToMark.getID());
                }
            }
        }
    }

    public static void listAll(ArrayList<Task> TO_DO, ArrayList<Task> IN_PROGRESS, ArrayList<Task> DONE){
        System.out.println("<---ALL TASKS--->");
        System.out.println("ID  :   Description :   Status  :   CreatedAt   :   UpdatedAt");

        for (Task t : TO_DO){
            System.out.printf("%d   :   %s  :   %s  :   %s  :   %s\n", t.getID(), t.getDescription(), t.getStatus(), t.getCreateTime().toString(), t.getUpdateTime().toString());
        }
        for (Task t : IN_PROGRESS){
            System.out.printf("%d   :   %s  :   %s  :   %s  :   %s\n", t.getID(), t.getDescription(), t.getStatus(), t.getCreateTime().toString(), t.getUpdateTime().toString());
        }

        for (Task t : DONE){
            System.out.printf("%d   :   %s  :   %s  :   %s  :   %s\n", t.getID(), t.getDescription(), t.getStatus(), t.getCreateTime().toString(), t.getUpdateTime().toString());
        }
    }
    public static void listDone(ArrayList<Task> DONE){
        System.out.println("<---TASK COMPLETED--->");
        System.out.println("ID  :   Description :   Status  :   CreatedAt   :   UpdatedAt");

        for (Task t : DONE){
            System.out.printf("%d   :   %s  :   %s  :   %s  :   %s\n", t.getID(), t.getDescription(), t.getStatus(), t.getCreateTime().toString(), t.getUpdateTime().toString());
        }
    }
    public static void listTodo(ArrayList<Task> TO_DO){
        System.out.println("<---TASK TO DO--->");
        System.out.println("ID  :   Description :   Status  :   CreatedAt   :   UpdatedAt");

        for (Task t : TO_DO){
            System.out.printf("%d   :   %s  :   %s  :   %s  :   %s\n", t.getID(), t.getDescription(), t.getStatus(), t.getCreateTime().toString(), t.getUpdateTime().toString());
        }
    }
    public static void listInProgress(ArrayList<Task> IN_PROGRESS){
        System.out.println("<---TASK TO DO--->");
        System.out.println("ID  :   Description :   Status  :   CreatedAt   :   UpdatedAt");

        for (Task t : IN_PROGRESS){
            System.out.printf("%d   :   %s  :   %s  :   %s  :   %s\n", t.getID(), t.getDescription(), t.getStatus(), t.getCreateTime().toString(), t.getUpdateTime().toString());
        }
    }
}