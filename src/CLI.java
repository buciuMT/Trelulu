// Creează proiect
// Creează utilizator
// Creează task
// Atribuie task unui utilizator
// Adaugă comentariu la un task
// Adaugă etichetă la un task
// Filtrează taskuri după status
// Sortează taskuri după deadline

import java.util.Scanner;

public class CLI {
    public static void runCLIonService(Service service) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n===== MENIU =====");
            System.out.println("1. Creează utilizator");
            System.out.println("2. Creează task");
            System.out.println("3. Atribuie task unui utilizator");
            System.out.println("4. Adaugă comentariu la un task");
            System.out.println("5. Adaugă etichetă la un task");
            System.out.println("6. Filtrează și sortează taskuri după status");
            System.out.println("0. Ieșire");
            System.out.print("Alege opțiunea: ");
            int opt = Integer.parseInt(scanner.nextLine());

            switch (opt) {
                case 1 -> {
                    System.out.print("ID utilizator: ");
                    long id = Long.parseLong(scanner.nextLine());
                    System.out.print("Rol utilizator: ");
                    String role = scanner.nextLine();
                    service.addUser(new User(id, role, new java.util.ArrayList<>()));
                    System.out.println("Utilizator adăugat.");
                }
                case 2 -> {
                    System.out.print("ID task: ");
                    long id = Long.parseLong(scanner.nextLine());
                    System.out.print("Titlu task: ");
                    String title = scanner.nextLine();
                    System.out.print("Descriere task: ");
                    String description = scanner.nextLine();
                    System.out.print("Status (TO_DO, IN_PROGRESS, DONE): ");
                    TaskStatus status = TaskStatus.valueOf(scanner.nextLine());
                    System.out.print("Deadline (YYYY-MM-DDTHH:MM): ");
                    java.time.LocalDateTime deadline = java.time.LocalDateTime.parse(scanner.nextLine());
                    Task task = new Task(id, title, description,deadline, status);
                    service.addTask(task);
                    System.out.println("Task creat.");
                }
                case 3 -> {
                    System.out.print("ID task: ");
                    long taskId = Long.parseLong(scanner.nextLine());
                    System.out.print("ID utilizator: ");
                    long userId = Long.parseLong(scanner.nextLine());
                    if (service.taskToUser(taskId, userId))
                        System.out.println("Task atribuit.");
                    else
                        System.out.println("Eroare la atribuirea taskului.");
                }
                case 4 -> {
                    System.out.print("ID task: ");
                    long taskId = Long.parseLong(scanner.nextLine());
                    System.out.print("ID utilizator: ");
                    long userId = Long.parseLong(scanner.nextLine());
                    System.out.print("Comentariu: ");
                    String comment = scanner.nextLine();
                    if (service.addComment(taskId, userId, comment))
                        System.out.println("Comentariu adăugat.");
                    else
                        System.out.println("Eroare la adăugarea comentariului.");
                }
                case 5 -> {
                    System.out.print("ID task: ");
                    long taskId = Long.parseLong(scanner.nextLine());
                    System.out.print("ID utilizator: ");
                    long userId = Long.parseLong(scanner.nextLine());
                    System.out.print("Text etichetă: ");
                    String label = scanner.nextLine();
                    if (service.addLabelToTask(taskId, userId, label))
                        System.out.println("Etichetă adăugată.");
                    else
                        System.out.println("Eroare la adăugare etichetă.");
                }
                case 6 -> {
                    System.out.print("Status pentru filtrare (TO_DO, IN_PROGRESS, DONE): ");
                    TaskStatus status = TaskStatus.valueOf(scanner.nextLine());
                    var filtered = service.filterTasksByDeadline(status).stream().sorted();
                    filtered.forEach(System.out::println);
                }
                case 0 -> {
                    System.out.println("La revedere!");
                    return;
                }
                default -> System.out.println("Opțiune invalidă!");
            }
        }
    }
}
