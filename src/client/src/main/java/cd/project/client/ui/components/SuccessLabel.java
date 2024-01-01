package cd.project.client.ui.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class SuccessLabel {
    public SuccessLabel(Label label, String message, boolean success) {
        label.setText(message);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFont(Font.font(label.getFont().getName(), 16));
        label.setTextFill(success ? Color.GREEN : Color.RED);
        label.setPadding(new Insets(30, 0, 0, 0));
    }
}
