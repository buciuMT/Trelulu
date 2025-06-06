// Creează proiect
// Creează utilizator
// Creează task
// Atribuie task unui utilizator
// Adaugă comentariu la un task
// Adaugă etichetă la un task
// Filtrează taskuri după status
// Sortează taskuri după deadline
// Listează taskuri pentru un proiect
// Trimite notificare către utilizator

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Service service = new Service();
        CLI.runCLIonService(service);
    }
}