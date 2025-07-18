// Student Grade Calculator
import java.util.Scanner;

public class StudentGradeCalculator {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Get number of subjects from user
        System.out.print("Enter the number of subjects: ");
        int numSubjects = scanner.nextInt();
        
        // Create array to store marks
        int[] marks = new int[numSubjects];
        int totalMarks = 0;
        
        // Get marks for each subject
        System.out.println("\nEnter marks for each subject (out of 100):");
        for (int i = 0; i < numSubjects; i++) {
            System.out.print("Subject " + (i + 1) + ": ");
            marks[i] = scanner.nextInt();
            
            // Add to total
            totalMarks += marks[i];
        }
        
        // Calculate average percentage
        double averagePercentage = (double) totalMarks / numSubjects;
        
        // Determine grade based on average
        String grade = calculateGrade(averagePercentage);
        
        // Display results
        System.out.println("\n=== STUDENT GRADE REPORT ===");
        System.out.println("Total Marks: " + totalMarks + " out of " + (numSubjects * 100));
        System.out.println("Average Percentage: " + String.format("%.2f", averagePercentage) + "%");
        System.out.println("Grade: " + grade);
        
        scanner.close();
    }
    
    // Method to calculate grade based on percentage
    public static String calculateGrade(double percentage) {
        if (percentage >= 90) {
            return "A+";
        } else if (percentage >= 80) {
            return "A";
        } else if (percentage >= 70) {
            return "B";
        } else if (percentage >= 60) {
            return "C";
        } else if (percentage >= 50) {
            return "D";
        } else {
            return "F";
        }
    }
}

