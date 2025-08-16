package Lab_5_Starter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Create CreateLoginForm class to create login form
// Class extends JFrame to create a window where our components are added
// Class implements ActionListener to perform an action on button click
class CreateLoginForm extends JFrame implements ActionListener {
    // Initialize button, panel, label, and text field
    JButton jButtonSubmit, jButtonExit, jButtonCreateAccount;
    JPanel newPanel;
    JLabel userLabel, passLabel, infoLabel;
    final JTextField textField1, textField2;

    // Constructor
    CreateLoginForm() {
        setTitle("Lab 5 Login Window");
        setLayout(new BorderLayout());
        Icon logo = new ImageIcon(getClass().getResource("user-login-305.png"));
        infoLabel = new JLabel(logo);
        infoLabel.setSize(new Dimension(20, 20));

        // Create label for username
        userLabel = new JLabel();
        userLabel.setText("Username"); // Set label value for textField1

        // Create text field to get username from the user
        textField1 = new JTextField(15); // Set length of the text

        // Create label for password
        passLabel = new JLabel();
        passLabel.setText("Password"); // Set label value for textField2

        // Create text field to get password from the user
        textField2 = new JPasswordField(15); // Set length for the password

        // Create submit button
        jButtonSubmit = new JButton("SUBMIT"); // Set label to button
        jButtonExit = new JButton("EXIT");
        jButtonCreateAccount = new JButton("Create an Account");

        // Create panel to put form elements
        newPanel = new JPanel(new GridLayout(4, 1));
        newPanel.add(userLabel); // Set username label to panel
        newPanel.add(textField1); // Set text field to panel
        newPanel.add(passLabel); // Set password label to panel
        newPanel.add(textField2); // Set text field to panel
        newPanel.add(jButtonSubmit);
        newPanel.add(jButtonExit);

        // Add panel to frame
        add(infoLabel, BorderLayout.NORTH);
        add(newPanel, BorderLayout.CENTER);
        add(jButtonCreateAccount, BorderLayout.SOUTH);

        // Perform action on button click
        jButtonSubmit.addActionListener(this); // Add action listener to button

        jButtonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int r = JOptionPane.showConfirmDialog(null,
                        "Are you sure?", "Confirmation window", JOptionPane.YES_NO_OPTION);
                if (r == 0)
                    System.exit(0);
            }
        });

        jButtonCreateAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateAccount createAccountForm = new CreateAccount();
                createAccountForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                createAccountForm.setSize(300, 220); // Set size of the frame
                createAccountForm.setVisible(true); // Make form visible to the user
            }
        });

    }

    // Define abstract method actionPerformed() which will be called on button click
    public void actionPerformed(ActionEvent ae) {
        String userValue = textField1.getText(); // Get user-entered username from textField1
        String passValue = textField2.getText(); // Get user-entered password from textField2
        boolean isSuccess = false;

        // Step 5: Use username and password from users.txt file
        try (Scanner fileScanner = new Scanner(new File("users.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] credentials = line.split(","); // Assuming the file format is "username,password"
                if (credentials.length == 2) {
                    String user = credentials[0].trim();
                    String pw = credentials[1].trim();

                    // Check if entered credentials match
                    if (userValue.equals(user) && passValue.equals(pw)) {
                        isSuccess = true;
                        break;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error: users.txt file not found!",
                    "File Error", JOptionPane.ERROR_MESSAGE);
        }

        // Check whether the credentials are authentic or not
        if (!isSuccess) {
            // Navigate the user to the CellPhoneInventory page
            CellPhoneInventory page = new CellPhoneInventory();
            page.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            page.setSize(350, 250); // Set frame size
            page.setVisible(true); // Display frame
            CreateLoginForm.this.dispose(); // Close the login form
        } else {
            // Show error message if authentication fails
            JOptionPane.showMessageDialog(null, "Please enter valid username and password");
            System.out.println("Invalid username or password.");
        }
    }
}

// Create the main class
class LoginFormDemo {
    // Main() method start
    public static void main(String arg[]) {
        // Create an instance of the CreateLoginForm
        // Login window for Lab 5
        CreateLoginForm form = new CreateLoginForm();
        form.setSize(320, 240); // Set size of the frame
        form.setVisible(true); // Make form visible to the user
    }
}
