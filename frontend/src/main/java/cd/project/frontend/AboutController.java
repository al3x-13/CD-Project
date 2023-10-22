package cd.project.frontend;

import javafx.fxml.FXML;

public class AboutController {
    @FXML
    public void initialize() {
        // TODO
    }

    @FXML
    private void handleMenuAbout() {
        Router router = new Router();
        router.navigateToAbout();
    }
}
