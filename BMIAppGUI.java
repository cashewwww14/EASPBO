import javax.swing.*;
import java.awt.*;

public class BMIAppGUI {
    private UserManager userManager;
    private JFrame frame;

    public BMIAppGUI() {
        userManager = new UserManager();
        frame = new JFrame("BMI Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        showLoginScreen();
    }

    private void showLoginScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(loginButton);
        panel.add(registerButton);

        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            RegularUser user = userManager.loginUser(username);
            if (user != null) {
                showUserDashboard(user);
            } else {
                JOptionPane.showMessageDialog(frame, "User not found. Please register.");
            }
        });

        registerButton.addActionListener(e -> showRegisterScreen());
    }

    private void showRegisterScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();
        JLabel heightLabel = new JLabel("Height (cm):");
        JTextField heightField = new JTextField();
        JLabel weightLabel = new JLabel("Weight (kg):");
        JTextField weightField = new JTextField();
        JLabel genderLabel = new JLabel("Gender:");
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Laki-laki", "Perempuan"});
        JButton registerButton = new JButton("Register");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(ageLabel);
        panel.add(ageField);
        panel.add(heightLabel);
        panel.add(heightField);
        panel.add(weightLabel);
        panel.add(weightField);
        panel.add(genderLabel);
        panel.add(genderComboBox);
        panel.add(registerButton);

        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            int age = Integer.parseInt(ageField.getText());
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());
            String gender = (String) genderComboBox.getSelectedItem();

            if (userManager.registerUser(username, age, height, weight, gender)) {
                JOptionPane.showMessageDialog(frame, "Registration successful!");
                showLoginScreen();
            } else {
                JOptionPane.showMessageDialog(frame, "Username already exists.");
            }
        });
    }

    private void showUserDashboard(RegularUser user) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, " + user.getUsername());
        JTextArea infoArea = new JTextArea();
        infoArea.setText("BMI: " + user.calculateBMI() + "\n" +
                "Ideal Weight Range: " + user.getIdealWeightRange() + "\n" +
                "Calorie Intake/Day: " + user.calculateCalorieIntake() + " kcal\n" +
                user.getHealthAdvice() + "\n" +
                "Nutrition Plan: " + user.getRecommendedNutrition());
        infoArea.setEditable(false);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> showLoginScreen());

        panel.add(welcomeLabel, BorderLayout.NORTH);
        panel.add(new JScrollPane(infoArea), BorderLayout.CENTER);
        panel.add(logoutButton, BorderLayout.PAGE_END);

        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BMIAppGUI().frame.setVisible(true));
    }
}
