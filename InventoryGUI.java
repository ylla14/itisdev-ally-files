import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryGUI {

    private static DefaultTableModel tableModel;

    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Inventory Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Create the header label
        JLabel headerLabel = new JLabel("Inventory", SwingConstants.CENTER);
        headerLabel.setPreferredSize(new Dimension(frame.getWidth(), 40));
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(headerLabel, BorderLayout.NORTH);

        // Create the table model and table
        String[] columns = {"Items", "Qty/units", "SKU", "Cost"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);

        // Add sample data (optional)
        Object[][] data = {
            {"Coffee Beans", "1000 g", "2", "2200.0"},
            {"Chocolate Syrup", "750 ml", "2", "550.0"},
            {"Caramel Syrup", "750 ml", "3", "550.0"},
        };
        for (Object[] row : data) {
            tableModel.addRow(row);
        }

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Create the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Create the buttons
        JButton updateButton = new JButton("Update Inventory");
        JButton exitButton = new JButton("Exit Inventory");

        // Add buttons to the button panel
        buttonPanel.add(updateButton);
        buttonPanel.add(exitButton);

        // Add the button panel to the frame
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners to the buttons
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUpdateInventoryDialog(frame);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Exit the application
                frame.dispose();
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }

    private static void showUpdateInventoryDialog(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Update Inventory", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout());

        // Create the header label
        JLabel headerLabel = new JLabel("Update Inventory", SwingConstants.CENTER);
        headerLabel.setPreferredSize(new Dimension(dialog.getWidth(), 40));
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        dialog.add(headerLabel, BorderLayout.NORTH);

        // Create the panel for input fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create the input fields and labels
        JLabel skuLabel = new JLabel("Select a SKU:");
        JTextField skuField = new JTextField();
        JLabel qtyLabel = new JLabel("How many units/QTY to add:");
        JTextField qtyField = new JTextField();
        JLabel costLabel = new JLabel("Enter the cost of the added quantity:");
        JTextField costField = new JTextField();

        // Add fields and labels to the panel
        inputPanel.add(skuLabel);
        inputPanel.add(skuField);
        inputPanel.add(qtyLabel);
        inputPanel.add(qtyField);
        inputPanel.add(costLabel);
        inputPanel.add(costField);

        dialog.add(inputPanel, BorderLayout.CENTER);

        // Create the button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton updateButton = new JButton("Update");
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners to the buttons
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sku = skuField.getText();
                String qtyStr = qtyField.getText();
                String costStr = costField.getText();

                if (!sku.isEmpty() && !qtyStr.isEmpty() && !costStr.isEmpty()) {
                    try {
                        int qty = Integer.parseInt(qtyStr);
                        double cost = Double.parseDouble(costStr);
                        updateInventory(sku, qty, cost);
                        dialog.dispose();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(dialog, "Please enter valid numbers for quantity and cost.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(dialog, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    private static void updateInventory(String sku, int qty, double cost) {
        // Update inventory logic here
        // For demonstration, just print the values
        System.out.println("Updating inventory for SKU: " + sku + ", Quantity: " + qty + ", Cost: " + cost);

        // Here you would normally find the item by SKU and update its quantity and cost
        // For now, let's just update the table model directly for demonstration purposes
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 2).equals(sku)) {
                int currentQty = Integer.parseInt(tableModel.getValueAt(i, 1).toString());
                double currentCost = Double.parseDouble(tableModel.getValueAt(i, 3).toString());

                int newQty = currentQty + qty;
                double newCost = currentCost  + cost;

                tableModel.setValueAt(newQty, i, 1);
                tableModel.setValueAt(newCost, i, 3);

                JOptionPane.showMessageDialog(null, "Inventory updated!");
                return;
            }
        }

        JOptionPane.showMessageDialog(null, "SKU not found in inventory.");
    }
}
