import java.time.LocalDateTime;

public class Task{
    private int id;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Task(int id, String description, String status, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.id = id;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getID(){ return this.id; }

    public String getDescription(){ return this.description; }

    public String getStatus(){ return this.status; }

    public LocalDateTime getCreateTime(){ return this.createdAt; }

    public LocalDateTime getUpdateTime(){ return this.updatedAt; }

    public void setDescription(String newDescription){ 
        this.description = newDescription; 
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString(){
        return "ID: "+this.id+"\nDescription: "+this.description+"\nStatus: "+this.status+"CreatedAt: "+this.createdAt+"\nUpdatedAt"+this.updatedAt;
    }
}