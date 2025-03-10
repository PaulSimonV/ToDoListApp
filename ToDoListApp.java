import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.*;

class TaskItem {
    String task;
    String priority;
    String dueDate;
    boolean completed;

    public TaskItem(String task, String priority, String dueDate) {
        this.task = task;
        this.priority = priority;
        this.dueDate = dueDate;
        this.completed = false;
    }
}

public class ToDoListApp {
    private JFrame frame;
    private JPanel taskPanel;
    private JTextField taskField;
    private JComboBox<String> priorityBox;
    private JTextField dueDateField;
    private ArrayList<TaskItem> taskList;

    public ToDoListApp() {
        frame = new JFrame("Pastel To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 650);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(230, 230, 250)); // Soft Lavender

        taskList = new ArrayList<>();
        taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
        taskPanel.setBackground(new Color(240, 248, 255)); // Alice Blue
        taskPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(taskPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBackground(new Color(240, 248, 255));
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(2, 3, 10, 10));
        panel.setBackground(new Color(224, 255, 255)); // Pastel Cyan
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        taskField = new JTextField();
        taskField.setFont(new Font("Poppins", Font.BOLD, 18));
        taskField.setBackground(new Color(250, 235, 215)); // Antique White
        taskField.setForeground(Color.DARK_GRAY);
        taskField.setCaretColor(Color.DARK_GRAY);

        priorityBox = new JComboBox<>(new String[]{"Low", "Medium", "High"});
        priorityBox.setFont(new Font("Poppins", Font.BOLD, 16));
        priorityBox.setBackground(new Color(193, 225, 193)); // Pastel Green
        priorityBox.setForeground(Color.DARK_GRAY);

        dueDateField = new JTextField();
        dueDateField.setFont(new Font("Poppins", Font.BOLD, 16));
        dueDateField.setBackground(new Color(255, 218, 185)); // Soft Peach
        dueDateField.setForeground(Color.DARK_GRAY);
        dueDateField.setCaretColor(Color.DARK_GRAY);
        dueDateField.setToolTipText("Due Date (DD-MM-YYYY HH:MM)");

        JButton addButton = createStyledButton("➕ Add Task", new Color(176, 224, 230)); // Powder Blue
        JButton removeButton = createStyledButton("❌ Remove Completed", new Color(255, 182, 193)); // Light Pink
        JButton sortButton = createStyledButton("🔽 Sort by Priority", new Color(221, 160, 221)); // Plum

        panel.add(taskField);
        panel.add(priorityBox);
        panel.add(dueDateField);
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(sortButton);

        frame.add(panel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addTask());
        removeButton.addActionListener(e -> removeCompletedTasks());
        sortButton.addActionListener(e -> sortTasksByPriority());

        frame.setVisible(true);
    }

    private void addTask() {
        String taskText = taskField.getText().trim();
        String priority = (String) priorityBox.getSelectedItem();
        String dueDate = dueDateField.getText().trim();

        if (!taskText.isEmpty() && !dueDate.isEmpty()) {
            TaskItem newTask = new TaskItem(taskText, priority, dueDate);
            taskList.add(newTask);
            updateTaskPanel();
            taskField.setText("");
            dueDateField.setText("");
        } else {
            JOptionPane.showMessageDialog(frame, "Please enter a task and due date.");
        }
    }

    private void removeCompletedTasks() {
        taskList.removeIf(task -> task.completed);
        updateTaskPanel();
    }

    private void sortTasksByPriority() {
        taskList.sort(Comparator.comparing(task -> task.priority));
        updateTaskPanel();
    }

    private void updateTaskPanel() {
        taskPanel.removeAll();
        for (TaskItem task : taskList) {
            JCheckBox taskCheckBox = new JCheckBox(" " + task.task + " (" + task.priority + ") - " + task.dueDate);
            taskCheckBox.setSelected(task.completed);
            taskCheckBox.setFont(new Font("Poppins", Font.BOLD, 16));
            taskCheckBox.setForeground(Color.DARK_GRAY);
            taskCheckBox.setBackground(new Color(255, 245, 238)); // Seashell
            taskCheckBox.addItemListener(e -> task.completed = taskCheckBox.isSelected());
            taskPanel.add(taskCheckBox);
        }
        taskPanel.revalidate();
        taskPanel.repaint();
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setForeground(Color.DARK_GRAY);
        button.setFont(new Font("Poppins", Font.BOLD, 14));
        button.setBackground(color);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ToDoListApp::new);
    }
}