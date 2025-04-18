public class FileAttachment extends Id_er {
    private String filename;
    private String filepath;
    private Task task;

    public FileAttachment(long id,String filename, String filepath, Task task) {
        super(id);
        this.filename = filename;
        this.filepath = filepath;
        this.task = task;
    }
}
