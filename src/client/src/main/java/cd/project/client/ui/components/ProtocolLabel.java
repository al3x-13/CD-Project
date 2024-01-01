package cd.project.client.ui.components;

import cd.project.client.CommunicationProtocol;
import cd.project.client.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ProtocolLabel extends VBox {
    public ProtocolLabel() {
        VBox.setVgrow(this, Priority.ALWAYS);
        this.setAlignment(Pos.BOTTOM_CENTER);
        HBox labelContainer = this.labelContainer();
        this.getChildren().add(labelContainer);
    }

    private HBox labelContainer() {
        HBox container = new HBox();
        HBox.setHgrow(container, Priority.ALWAYS);
        container.setAlignment(Pos.BOTTOM_RIGHT);
        Label proto = protocolLabel();
        container.getChildren().addAll(proto);
        return container;
    }

    private Label protocolLabel() {
        String protocolText = Main.clientProtocol == CommunicationProtocol.SOAP ?
                "Protocol: SOAP" :
                "Protocol: REST";
        Label protocolLabel = new Label(protocolText);
        protocolLabel.setPadding(new Insets(6));
        protocolLabel.setStyle("-fx-background-color: #31344C; -fx-text-fill: " + Main.TEXT_COLOR_PRIMARY + "; " +
                "-fx-font-size: 15px; -fx-background-radius: 6px 0 0 0;");
        return protocolLabel;
    }
}
