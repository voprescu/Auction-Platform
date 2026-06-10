import com.formdev.flatlaf.FlatDarkLaf;
import ui.LoginWindow;

public class Main {
    public static void main(String[] args) {
        FlatDarkLaf.setup(); // tema moderna

        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginWindow().setVisible(true);
        });
    }
}
