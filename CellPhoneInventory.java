package Lab_5_Starter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;

public class CellPhoneInventory extends JFrame {
    JLabel labelModel, labelManufacturer, labelRetailPrice, labelBanner;
    JTextField jTextFieldModel, jTextFieldManufacturer, jTextFieldRetailPrice;
    JButton jButtonAdd, jButtonSave, jButtonNext, jButtonShow;
    JPanel newPanel, buttonPanel;
    ArrayList<CellPhone> phoneArrayList = new ArrayList<CellPhone>();

    CellPhoneInventory() {
        setTitle("Cellphone Inventory");
        setLayout(new BorderLayout());
        labelBanner = new JLabel("Cellphone Inventory Management");
        labelBanner.setFont(new Font("Serif", Font.BOLD, 20));
        labelBanner.setForeground(Color.BLUE);
        labelModel = new JLabel("Model");
        labelManufacturer = new JLabel("Manufacturer");
        labelRetailPrice = new JLabel("Retail Price");
        jTextFieldModel = new JTextField(15);
        jTextFieldManufacturer = new JTextField(15);
        jTextFieldRetailPrice = new JTextField(15);
        jButtonAdd = new JButton("Add");
        jButtonSave = new JButton("Save");
        jButtonNext = new JButton("Next");
        jButtonShow = new JButton("Show Inventory");
        newPanel = new JPanel(new GridLayout(3, 2));
        newPanel.add(labelModel);
        newPanel.add(jTextFieldModel);
        newPanel.add(labelManufacturer);
        newPanel.add(jTextFieldManufacturer);
        newPanel.add(labelRetailPrice);
        newPanel.add(jTextFieldRetailPrice);
        buttonPanel = new JPanel(new GridLayout(2, 2));
        buttonPanel.add(jButtonAdd);
        buttonPanel.add(jButtonNext);
        buttonPanel.add(jButtonSave);
        buttonPanel.add(jButtonShow);
        add(labelBanner, BorderLayout.NORTH);
        add(newPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        jButtonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String model = jTextFieldModel.getText();
                String manufacturer = jTextFieldManufacturer.getText();
                double price;

                try {
                    // Parse price from the input field
                    price = Double.parseDouble(jTextFieldRetailPrice.getText());

                    // Add the CellPhone to the list using try-catch blocks
                    try {
                        CellPhone newPhone = new CellPhone(model, manufacturer, price);
                        phoneArrayList.add(newPhone);

                        String inventoryDisplay = "";
                        for (CellPhone p : phoneArrayList) {
                            System.out.println(p);
                            inventoryDisplay += p.toString() + "\n";
                        }
                        JOptionPane.showMessageDialog(null, inventoryDisplay);

                    } catch (InvalidModelException ex) {
                        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(),
                                "Invalid Model", JOptionPane.ERROR_MESSAGE);
                    } catch (InvalidManufacturerException ex) {
                        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(),
                                "Invalid Manufacturer", JOptionPane.ERROR_MESSAGE);
                    } catch (InvalidRetailPrice ex) {
                        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(),
                                "Invalid Price", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (NumberFormatException ex) {
                    // Handle invalid number format for price input
                    JOptionPane.showMessageDialog(null, "Error: Please enter a valid price.",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        jButtonNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextFieldModel.setText(null);
                jTextFieldManufacturer.setText(null);
                jTextFieldRetailPrice.setText(null);
            }
        });

        jButtonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Formatter outCellPhoneList = null;
                try {
                    // File stream for output file
                    outCellPhoneList = new Formatter("cellPhones.txt");

                    for (int i = 0; i < phoneArrayList.size(); i++) {
                        outCellPhoneList.format("%s %s %.2f\n", phoneArrayList.get(i).getModel(),
                                phoneArrayList.get(i).getManufacturer(),
                                phoneArrayList.get(i).getRetailPrice());
                    }
                    System.out.println("Cellphone list stored in txt file.");
                } catch (SecurityException securityException) {
                    System.err.println("You do not have write access to this file.");
                    System.exit(1);
                } catch (FileNotFoundException fileNotFoundException) {
                    System.err.println("Error creating file.");
                    System.exit(1);
                } catch (FormatterClosedException closedException) {
                    System.err.println("Error writing to file - file has been closed.");
                    System.exit(1);
                } catch (IllegalFormatException formatException) {
                    System.err.println("Error with output.");
                    System.exit(1);
                } finally {
                    if (outCellPhoneList != null) {
                        outCellPhoneList.close();
                    }
                }
            }
        });

        jButtonShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = String.format("%-20s%-20s%10s%n", "Model", "Manufacturer",
                        "Retail Price");
                // Open cellPhoneList.txt, read its contents, and close the file
                try (Scanner input = new Scanner(Paths.get("cellPhones.txt"))) {

                    // Read record from file
                    while (input.hasNext()) { // While there is more to read
                        // Display record contents
                        msg += String.format("%-20s%-20s%10.2f%n", input.next(),
                                input.next(), input.nextDouble());
                    }
                } catch (NoSuchElementException | IllegalStateException | IOException exception) {
                    System.out.println("Error: " + exception.getMessage());
                }
                JOptionPane.showMessageDialog(null, msg);
            }
        });
    }
}
