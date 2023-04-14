package dk.letbillet.presentation.controller;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import dk.letbillet.Main;
import dk.letbillet.presentation.model.TicketModel;
import dk.letbillet.presentation.model.UserModel;
import dk.letbillet.presentation.model.VoucherModel;
import dk.letbillet.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {

    @FXML
    public AnchorPane contentPane;
    @FXML
    public Label loggedInAsLabel;
    @FXML
    public Button eventButton;
    @FXML
    public Button voucherButton;
    @FXML
    public Button userButton;
    @FXML
    public Button aboutButton;
    @FXML
    public Button exitButton;
    @FXML
    public Button minimizeButton;
    @FXML
    public HBox windowDecoration;
    @FXML
    public Button settingsButton;

    private final UserModel userModel;
    private final TicketModel ticketModel;
    private final VoucherModel voucherModel;

    public ApplicationController() {
        userModel = new UserModel();
        ticketModel = new TicketModel();
        voucherModel = new VoucherModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Window Decoration
        try {
            Text exitIcon = GlyphsDude.createIcon(FontAwesomeIcons.CLOSE, "25pt");
            exitIcon.setStyle("-fx-font-family: FontAwesome; -fx-fill: RED;");
            exitButton.setGraphic(exitIcon);
            exitButton.setText("");
            exitButton.getStyleClass().add("noDecoration");

            Text minimizeIcon = GlyphsDude.createIcon(FontAwesomeIcons.MINUS, "25pt");
            minimizeIcon.setStyle("-fx-font-family: FontAwesome; -fx-fill: GRAY;");
            minimizeButton.setGraphic(minimizeIcon);
            minimizeButton.setText("");
            minimizeButton.getStyleClass().add("noDecoration");
        } catch (Exception ignored) { }

        // Set logged in as label
        loggedInAsLabel.setText("Logged in as: " + UserService.getInstance().getLoggedInUser().getUsername());

        // Set what you are able to do depending on what role you have
        int userRole = UserService.getInstance().getLoggedInUser().getRole().getId();
        if(userRole == 1) {
          voucherButton.setDisable(true);
        } else if(userRole == 2) {
           userButton.setDisable(true);
        }

        // Icons
        try {
            Text eventIcon = GlyphsDude.createIcon(FontAwesomeIcons.CALENDAR, "20pt");
            Text voucherIcon = GlyphsDude.createIcon(FontAwesomeIcons.TICKET, "20pt");
            Text usersIcon = GlyphsDude.createIcon(FontAwesomeIcons.USERS, "20pt");
            Text settingsIcon = GlyphsDude.createIcon(FontAwesomeIcons.GEAR, "20pt");
            Text aboutIcon = GlyphsDude.createIcon(FontAwesomeIcons.INFO, "20pt");

            setButtonIcon(eventIcon, eventButton);
            setButtonIcon(voucherIcon, voucherButton);
            setButtonIcon(usersIcon, userButton);
            setButtonIcon(settingsIcon, settingsButton);
            setButtonIcon(aboutIcon, aboutButton);
        } catch (Exception ignored) { }

        handleShowEvents();
    }

    private void setButtonIcon(Text icon, Button button) {
        button.setContentDisplay(ContentDisplay.LEFT);
        button.setGraphicTextGap(10);
        button.setGraphic(icon);
    }

    public void exitApplication() {
        System.exit(0);
    }

    public void minimizeApplication() {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }

    public void handleShowEvents() {
        try {
            FXMLLoader content = new FXMLLoader(Main.class.getResource("presentation/view/Events.fxml"));
            Node node = content.load();
            EventsController controller = content.getController();
            controller.setTicketModel(ticketModel);
            AnchorPane.setRightAnchor(node, 0D);
            AnchorPane.setTopAnchor(node, 0D);
            AnchorPane.setBottomAnchor(node, 0D);
            contentPane.getChildren().setAll(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleShowVouchers() {
        try {
            FXMLLoader content = new FXMLLoader(Main.class.getResource("presentation/view/vouchers/Vouchers.fxml"));
            Node node = content.load();
            VouchersController controller = content.getController();
            controller.init(voucherModel);
            AnchorPane.setRightAnchor(node, 0D);
            AnchorPane.setTopAnchor(node, 0D);
            AnchorPane.setBottomAnchor(node, 0D);
            contentPane.getChildren().setAll(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleShowUsers() {
        try {
            FXMLLoader content = new FXMLLoader(Main.class.getResource("presentation/view/Users.fxml"));
            Node node = content.load();
            UserController controller = content.getController();
            controller.setUserModel(userModel);
            AnchorPane.setRightAnchor(node, 0D);
            AnchorPane.setTopAnchor(node, 0D);
            AnchorPane.setBottomAnchor(node, 0D);
            contentPane.getChildren().setAll(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleShowAbout() {
        try {
            FXMLLoader content = new FXMLLoader(Main.class.getResource("presentation/view/About.fxml"));
            Node node = content.load();
            AnchorPane.setRightAnchor(node, 0D);
            AnchorPane.setTopAnchor(node, 0D);
            AnchorPane.setBottomAnchor(node, 0D);
            contentPane.getChildren().setAll(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
