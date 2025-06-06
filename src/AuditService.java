import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

public class AuditService {
    private static AuditService instance;
    final private String filepath="log.csv";
    BufferedWriter bw;
    AuditService() throws IOException {
        bw=new BufferedWriter(new FileWriter(filepath));
    }

    public static AuditService getInstance() throws IOException{
        if(instance==null)
            instance=new AuditService();
        return instance;
    }

    public static void log(Level l,String text) throws IOException{
        var instance=getInstance();
        instance.bw.write(l+"::::"+java.time.LocalDateTime.now()+','+'"'+text+'"'+"\n");
        instance.bw.flush();
    }
}
