import java.io.*;
import java.util.*;

// Student class representing each student
class Student {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() { return name; }
    public int getRollNumber() { return rollNumber; }
    public String getGrade() { return grade; }

    public void setName(String name) { this.name = name; }
    public void setGrade(String grade) { this.grade = grade; }

    // Represent as CSV
    public String toCSV() {
        return name + "," + rollNumber + "," + grade;
    }

    // Create object from CSV
    public static Student fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length != 3) return null;
        return new Student(parts[0], Integer.parseInt(parts[1]), parts[2]);
    }

    public String toString() {
        return "Name: " + name + ", Roll: " + rollNumber + ", Grade: " + grade;
    }
}

// Manages student collection and file IO
class StudentManagementSystem {
    private List<Student> students = new ArrayList<>();
    private final String FILE_NAME = "students.csv";

    // Load data from file
    public void loadFromFile() {
        students.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                Student s = Student.fromCSV(line);
                if (s != null) students.add(s);
            }
        } catch (IOException e) {
            // File not found, first run — ok
        }
    }

    // Save data to file
    public void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                bw.write(s.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving to file.");
        }
    }

    public List<Student> getAllStudents() {
        return students;
    }

    public boolean addStudent(Student student) {
        // Do not allow duplicate roll numbers
        if (searchStudent(student.getRollNumber()) != null) return false;
        students.add(student);
        saveToFile();
        return true;
    }

    public boolean removeStudent(int rollNumber) {
        Iterator<Student> it = students.iterator();
        while (it.hasNext()) {
            if (it.next().getRollNumber() == rollNumber) {
                it.remove();
                saveToFile();
                return true;
            }
        }
        return false;
    }

    public Student searchStudent(int rollNumber) {
        for (Student s : students) {
            if (s.getRollNumber() == rollNumber) return s;
        }
        return null;
    }

    public boolean editStudent(int rollNumber, String newName, String newGrade) {
        Student s = searchStudent(rollNumber);
        if (s == null) return false;
        if (!newName.isEmpty()) s.setName(newName);
        if (!newGrade.isEmpty()) s.setGrade(newGrade);
        saveToFile();
        return true;
    }
}

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final StudentManagementSystem sms = new StudentManagementSystem();

    public static void main(String[] args) {
        sms.loadFromFile();
        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Edit Student");
            System.out.println("4. Search Student");
            System.out.println("5. Display All Students");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            int ch = readInt();
            switch (ch) {
                case 1: addStudentUI(); break;
                case 2: removeStudentUI(); break;
                case 3: editStudentUI(); break;
                case 4: searchStudentUI(); break;
                case 5: displayAllUI(); break;
                case 6: System.out.println("Exiting."); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    // Input validation: integer, positive
    private static int readInt() {
        while (true) {
            try {
                int n = Integer.parseInt(sc.nextLine().trim());
                if (n > 0) return n;
                System.out.print("Enter positive integer: ");
            } catch (Exception e) {
                System.out.print("Enter valid integer: ");
            }
        }
    }

    // Input validation: non-empty string
    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Input cannot be empty.");
        }
    }

    private static void addStudentUI() {
        String name = readNonEmpty("Enter name: ");
        System.out.print("Enter roll number: ");
        int roll = readInt();
        String grade = readNonEmpty("Enter grade: ");
        Student student = new Student(name, roll, grade);
        if (sms.addStudent(student)) {
            System.out.println("Student added.");
        } else {
            System.out.println("Roll number already exists.");
        }
    }

    private static void removeStudentUI() {
        System.out.print("Enter roll number to remove: ");
        int roll = readInt();
        if (sms.removeStudent(roll)) {
            System.out.println("Student removed.");
        } else {
            System.out.println("Not found.");
        }
    }

    private static void editStudentUI() {
        System.out.print("Enter roll number to edit: ");
        int roll = readInt();
        Student student = sms.searchStudent(roll);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        String newName = readInput("Enter new name (press Enter to skip): ");
        String newGrade = readInput("Enter new grade (press Enter to skip): ");
        if (sms.editStudent(roll, newName, newGrade)) {
            System.out.println("Student updated.");
        }
    }

    private static String readInput(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static void searchStudentUI() {
        System.out.print("Enter roll number to search: ");
        int roll = readInt();
        Student s = sms.searchStudent(roll);
        if (s != null) {
            System.out.println(s);
        } else {
            System.out.println("Not found.");
        }
    }

    private static void displayAllUI() {
        List<Student> all = sms.getAllStudents();
        if (all.isEmpty()) {
            System.out.println("No students.");
        } else {
            for (Student s : all) {
                System.out.println(s);
            }
        }
    }
}
