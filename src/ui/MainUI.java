import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import java.awt.*;

public class MainUI {
    static String[] LOCATIONS = { "El Paso", "Seattle", "Austin", "San Francisco" };
    static String[] OCCUPATIONS = { "Software Engineer", "Janitor", "CEO" };
    static String[] INCOME_CLASSES = { "High income", "Medium income", "Low income" };

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        LayoutManager layout = new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS);
        frame.setLayout(layout);

        JPanel[] questions = {
                question1(),
                question2(),
                question3(),
                question4(),
                question5(),
                question6(),
                question7(),
                question8(),
                question9(),
                question10()
        };

        for (JPanel question : questions) {
            question.setAlignmentX(Component.LEFT_ALIGNMENT);
            frame.add(question);
        }

        frame.doLayout();
        frame.setMinimumSize(frame.getPreferredSize());
        frame.setSize(1000, 500);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void displayQueryResult() {
        JFrame resultFrame = new JFrame();
        resultFrame.setVisible(true);
    }

    public static JPanel question1() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.add(new JLabel("1. "));
        panel.add(new JLabel("Where does a person with occupation "));

        JComboBox<String> options = new JComboBox<String>(OCCUPATIONS);
        options.setMaximumSize(options.getPreferredSize());
        panel.add(options);

        panel.add(new JLabel(" typically live?"));
        panel.add(Box.createHorizontalGlue());

        JButton button = new JButton("Answer");
        button.addActionListener(e -> {
            displayQueryResult();
        });
        panel.add(button);
        return panel;
    }

    public static JPanel question2() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.add(new JLabel("2. "));
        panel.add(new JLabel("What are the highest paying places to move to find occupation "));

        JComboBox<String> options = new JComboBox<String>(OCCUPATIONS);
        options.setMaximumSize(options.getPreferredSize());

        panel.add(options);
        panel.add(new JLabel(" ?"));
        panel.add(Box.createHorizontalGlue());

        JButton button = new JButton("Answer");
        button.addActionListener(e -> {
            displayQueryResult();
        });
        panel.add(button);

        return panel;
    }

    public static JPanel question3() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.add(new JLabel("3. "));
        panel.add(new JLabel("What are the highest paying occupations in "));

        JComboBox<String> options = new JComboBox<String>(LOCATIONS);
        options.setMaximumSize(options.getPreferredSize());
        panel.add(options);

        panel.add(new JLabel(" ?"));
        panel.add(Box.createHorizontalGlue());

        JButton button = new JButton("Answer");
        button.addActionListener(e -> {
            displayQueryResult();
        });
        panel.add(button);

        return panel;
    }

    public static JPanel question4() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.add(new JLabel("4. "));
        panel.add(new JLabel("What is the median income of occupation "));

        JComboBox<String> options1 = new JComboBox<String>(OCCUPATIONS);
        options1.setMaximumSize(options1.getPreferredSize());
        panel.add(options1);

        panel.add(new JLabel(" in location "));
        JComboBox<String> options2 = new JComboBox<String>(LOCATIONS);
        options2.setMaximumSize(options2.getPreferredSize());
        panel.add(options2);

        panel.add(new JLabel(" ?"));
        panel.add(Box.createHorizontalGlue());

        JButton button = new JButton("Answer");
        button.addActionListener(e -> {
            displayQueryResult();
        });
        panel.add(button);

        return panel;
    }

    public static JPanel question5() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.add(new JLabel("5. "));
        panel.add(new JLabel("What occupations area available in location "));

        JComboBox<String> options = new JComboBox<String>(LOCATIONS);
        options.setMaximumSize(options.getPreferredSize());
        panel.add(options);

        panel.add(new JLabel(" ?"));
        panel.add(Box.createHorizontalGlue());

        JButton button = new JButton("Answer");
        button.addActionListener(e -> {
            displayQueryResult();
        });
        panel.add(button);

        return panel;
    }

    public static JPanel question6() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.add(new JLabel("6. "));
        panel.add(new JLabel("What is the average income in location "));

        JComboBox<String> options1 = new JComboBox<String>(LOCATIONS);
        options1.setMaximumSize(options1.getPreferredSize());
        panel.add(options1);

        panel.add(new JLabel(" for occupation "));
        JComboBox<String> options2 = new JComboBox<String>(OCCUPATIONS);
        options2.setMaximumSize(options2.getPreferredSize());
        panel.add(options2);

        panel.add(new JLabel(" ?"));
        panel.add(Box.createHorizontalGlue());

        JButton button = new JButton("Answer");
        button.addActionListener(e -> {
            displayQueryResult();
        });
        panel.add(button);

        return panel;
    }

    public static JPanel question7() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.add(new JLabel("7. "));
        panel.add(new JLabel("How many individuals with occupation "));

        JComboBox<String> options1 = new JComboBox<String>(OCCUPATIONS);
        options1.setMaximumSize(options1.getPreferredSize());
        panel.add(options1);

        panel.add(new JLabel(" live in "));

        JComboBox<String> options2 = new JComboBox<String>(LOCATIONS);
        options2.setMaximumSize(options2.getPreferredSize());
        panel.add(options2);

        panel.add(new JLabel(" ?"));
        panel.add(Box.createHorizontalGlue());

        JButton button = new JButton("Answer");
        button.addActionListener(e -> {
            displayQueryResult();
        });
        panel.add(button);

        return panel;
    }

    public static JPanel question8() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.add(new JLabel("8. "));
        panel.add(new JLabel("What occupations are the lowest in location "));

        JComboBox<String> options = new JComboBox<String>(LOCATIONS);
        options.setMaximumSize(options.getPreferredSize());
        panel.add(options);

        panel.add(new JLabel(" ?"));
        panel.add(Box.createHorizontalGlue());

        JButton button = new JButton("Answer");
        button.addActionListener(e -> {
            displayQueryResult();
        });
        panel.add(button);

        return panel;
    }

    public static JPanel question9() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.add(new JLabel("9. "));
        panel.add(new JLabel("What occupations are "));

        JComboBox<String> options = new JComboBox<String>(INCOME_CLASSES);
        options.setMaximumSize(options.getPreferredSize());
        panel.add(options);

        panel.add(new JLabel(" ?"));
        panel.add(Box.createHorizontalGlue());

        JButton button = new JButton("Answer");
        button.addActionListener(e -> {
            displayQueryResult();
        });
        panel.add(button);

        return panel;
    }

    public static JPanel question10() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.add(new JLabel("10. "));
        panel.add(new JLabel("What is the percentage of women for occupation "));

        JComboBox<String> options = new JComboBox<>(OCCUPATIONS);
        options.setMaximumSize(options.getPreferredSize());
        panel.add(options);

        panel.add(new JLabel(" ?"));
        panel.add(Box.createHorizontalGlue());

        JButton button = new JButton("Answer");
        button.addActionListener(e -> {
            displayQueryResult();
        });

        panel.add(button);
        return panel;
    }
}
