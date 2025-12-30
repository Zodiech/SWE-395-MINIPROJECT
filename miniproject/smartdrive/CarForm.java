package smartdrive;

import javax.swing.*;
import java.awt.*;

public class CarForm extends JFrame {

    private final JTextField brandField = new JTextField(20);
    private final JTextField modelField = new JTextField(20);
    private final JTextField yearField  = new JTextField(20);
    private final JTextField priceField = new JTextField(20);

    private final DBConnection db = new DBConnection();

    public CarForm() {
        setTitle("SmartDrive Rentals - Register Car");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 320);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Brand:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(brandField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Model:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(modelField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Year:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(yearField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; panel.add(priceField, gbc);

    JButton registerButton = new JButton("Register");
    gbc.gridx = 1; 
    gbc.gridy = 4;
    panel.add(registerButton, gbc);

    registerButton.addActionListener(e -> registerCar());

    JButton viewButton = new JButton("View Cars");
    gbc.gridx = 1;
    gbc.gridy = 5;
    panel.add(viewButton, gbc);

    viewButton.addActionListener(e -> {
        DBConnection db = new DBConnection();
        String cars = db.getAllCarsAsText();

        JOptionPane.showMessageDialog(
                this,
                cars,
                "Registered Cars",
                JOptionPane.INFORMATION_MESSAGE
        );
    });

add(panel);


        registerButton.addActionListener(e -> registerCar());

        add(panel);
    }

    private void registerCar() {
        String brand = brandField.getText().trim();
        String model = modelField.getText().trim();
        String yearText = yearField.getText().trim();
        String priceText = priceField.getText().trim();

        if (brand.isEmpty() || model.isEmpty() || yearText.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int year;
        double price;
        try {
            year = Integer.parseInt(yearText);
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Year must be integer, Price must be number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Car car = new Car(brand, model, year, price);

        boolean ok = db.saveCar(car);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Car registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            brandField.setText("");
            modelField.setText("");
            yearField.setText("");
            priceField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Insert failed! Check terminal output.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CarForm().setVisible(true));
    }
}
