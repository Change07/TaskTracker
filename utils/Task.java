package utils;
import java.time.LocalDateTime;

public class Task{
    private int id;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Task(){
        this.id = 0;
        this.description = null;
        this.createdAt = null;
        this.updatedAt = null;
    }

    public Task(int id, String description, String status, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getID(){ return this.id; }

    public String getDescription(){ return this.description; }

    public String getStatus(){ return this.status; }

    public LocalDateTime getCreateTime(){ return this.createdAt; }

    public LocalDateTime getUpdateTime(){ return this.updatedAt; }

    public void setID(int id){ this.id = id; }

    public void setCreateTime(String time){ this.createdAt = LocalDateTime.parse(time); }

    public void setUpdateTime(String time){ this.updatedAt = LocalDateTime.parse(time); }

    public void setDescription(String description){ this.description = description; }

    public void setStatus(String status){ this.status = status;}

    public void updateDescription(String newDescription){ 
        this.description = newDescription; 
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString(){
        return "{\"id\" : "+this.id+", \"description\" : \""+this.description+"\", \"status\" : \""+this.status+"\", \"createdAt\" : \""+this.createdAt+"\", \"updatedAt\" : \""+this.updatedAt+"\"}";
    }
}