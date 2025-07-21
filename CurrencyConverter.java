import java.util.*;

public class CurrencyConverter {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("   CURRENCY CONVERTER - TASK 4");
        System.out.println("=================================");
        
        while (true) {
            try {
                // Currency Selection
                String baseCurrency = selectCurrency("base");
                String targetCurrency = selectCurrency("target");
                
                // Amount Input
                double amount = getAmountInput();
                
                // Fetch Exchange Rate and Convert
                double exchangeRate = fetchExchangeRate(baseCurrency, targetCurrency);
                double convertedAmount = amount * exchangeRate;
                
                // Display Result
                displayResult(amount, baseCurrency, convertedAmount, targetCurrency, exchangeRate);
                
                // Ask if user wants to continue
                if (!askToContinue()) {
                    break;
                }
                
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again.");
            }
        }
        
        System.out.println("Thank you for using Currency Converter!");
        scanner.close();
    }
    
    private static String selectCurrency(String type) {
        String[] currencies = {"USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "CNY", "INR", "KRW"};
        String[] currencyNames = {
            "US Dollar", "Euro", "British Pound", "Japanese Yen", "Australian Dollar",
            "Canadian Dollar", "Swiss Franc", "Chinese Yuan", "Indian Rupee", "South Korean Won"
        };
        
        System.out.println("\nSelect " + type + " currency:");
        System.out.println("─────────────────────────────");
        
        for (int i = 0; i < currencies.length; i++) {
            System.out.printf("%2d. %s - %s\n", (i + 1), currencies[i], currencyNames[i]);
        }
        
        System.out.print("Enter your choice (1-" + currencies.length + "): ");
        
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= currencies.length) {
                    String selectedCurrency = currencies[choice - 1];
                    System.out.println("Selected: " + selectedCurrency + " - " + currencyNames[choice - 1]);
                    return selectedCurrency;
                } else {
                    System.out.print("Invalid choice! Please enter a number between 1 and " + currencies.length + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! Please enter a valid number: ");
            }
        }
    }
    
    private static double getAmountInput() {
        System.out.print("\nEnter amount to convert: ");
        
        while (true) {
            try {
                double amount = Double.parseDouble(scanner.nextLine());
                if (amount > 0) {
                    return amount;
                } else {
                    System.out.print("Amount must be positive! Enter a valid amount: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! Please enter a valid number: ");
            }
        }
    }
    
    private static double fetchExchangeRate(String baseCurrency, String targetCurrency) throws Exception {
        // Using mock exchange rates for this demonstration
        // This makes the code work without external dependencies
        
        Map<String, Double> mockRates = createMockRates(baseCurrency);
        
        if (mockRates.containsKey(targetCurrency)) {
            System.out.println("Fetching exchange rates...");
            Thread.sleep(800); // Simulate API call delay
            return mockRates.get(targetCurrency);
        }
        
        throw new Exception("Currency not supported");
    }
    
    private static Map<String, Double> createMockRates(String baseCurrency) {
        Map<String, Double> rates = new HashMap<>();
        
        // Realistic mock exchange rates for demonstration
        switch (baseCurrency) {
            case "USD":
                rates.put("EUR", 0.85); rates.put("GBP", 0.73); rates.put("JPY", 110.0);
                rates.put("AUD", 1.35); rates.put("CAD", 1.25); rates.put("CHF", 0.92);
                rates.put("CNY", 6.45); rates.put("INR", 83.2); rates.put("KRW", 1330.0);
                rates.put("USD", 1.0);
                break;
            case "EUR":
                rates.put("USD", 1.18); rates.put("GBP", 0.86); rates.put("JPY", 129.5);
                rates.put("AUD", 1.59); rates.put("CAD", 1.47); rates.put("CHF", 1.08);
                rates.put("CNY", 7.60); rates.put("INR", 98.2); rates.put("KRW", 1569.0);
                rates.put("EUR", 1.0);
                break;
            case "GBP":
                rates.put("USD", 1.37); rates.put("EUR", 1.16); rates.put("JPY", 150.7);
                rates.put("AUD", 1.85); rates.put("CAD", 1.71); rates.put("CHF", 1.26);
                rates.put("CNY", 8.84); rates.put("INR", 114.0); rates.put("KRW", 1822.0);
                rates.put("GBP", 1.0);
                break;
            case "INR":
                rates.put("USD", 0.012); rates.put("EUR", 0.0102); rates.put("GBP", 0.00877);
                rates.put("JPY", 1.32); rates.put("AUD", 0.0162); rates.put("CAD", 0.015);
                rates.put("CHF", 0.011); rates.put("CNY", 0.0775); rates.put("KRW", 15.98);
                rates.put("INR", 1.0);
                break;
            case "JPY":
                rates.put("USD", 0.0091); rates.put("EUR", 0.0077); rates.put("GBP", 0.00664);
                rates.put("AUD", 0.0123); rates.put("CAD", 0.0114); rates.put("CHF", 0.00837);
                rates.put("CNY", 0.0586); rates.put("INR", 0.757); rates.put("KRW", 12.09);
                rates.put("JPY", 1.0);
                break;
            default:
                // Default conversion through USD
                rates.put("USD", 1.0); rates.put("EUR", 0.85); rates.put("GBP", 0.73);
                rates.put("JPY", 110.0); rates.put("AUD", 1.35); rates.put("CAD", 1.25);
                rates.put("CHF", 0.92); rates.put("CNY", 6.45); rates.put("INR", 83.2);
                rates.put("KRW", 1330.0);
        }
        
        return rates;
    }
    
    private static void displayResult(double amount, String baseCurrency, double convertedAmount, 
                                    String targetCurrency, double exchangeRate) {
        System.out.println("\n=================================");
        System.out.println("        CONVERSION RESULT");
        System.out.println("=================================");
        System.out.printf("Amount: %.2f %s\n", amount, baseCurrency);
        System.out.printf("Exchange Rate: 1 %s = %.4f %s\n", baseCurrency, exchangeRate, targetCurrency);
        System.out.printf("Converted Amount: %.2f %s\n", convertedAmount, getCurrencySymbol(targetCurrency));
        System.out.println("=================================");
    }
    
    private static String getCurrencySymbol(String currency) {
        switch (currency) {
            case "USD": return currency + " ($)";
            case "EUR": return currency + " (€)";
            case "GBP": return currency + " (£)";
            case "JPY": return currency + " (¥)";
            case "INR": return currency + " (₹)";
            case "CNY": return currency + " (¥)";
            case "KRW": return currency + " (₩)";
            default: return currency;
        }
    }
    
    private static boolean askToContinue() {
        System.out.print("\nDo you want to convert another currency? (y/n): ");
        String choice = scanner.nextLine().toLowerCase();
        return choice.equals("y") || choice.equals("yes");
    }
}