package cd.project.client.ui.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class SessionExpireLabel {
    public SessionExpireLabel(Label label) {
        label.setText("Session has expired. Please login again.");
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFont(Font.font(label.getFont().getName(), FontWeight.BOLD, 15));
        label.setTextFill(Color.GRAY);
        label.setPadding(new Insets(30, 0, 0, 0));
    }
}
