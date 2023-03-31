package dk.letbillet.util;

import dk.letbillet.Main;
import dk.letbillet.entity.Event;
import dk.letbillet.presentation.controller.ViewEventController;
import dk.letbillet.presentation.model.EventModel;
import dk.letbillet.presentation.model.TicketModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class EventLoader {

    public static void openEventWindow(Event event, EventModel eventModel, TicketModel ticketModel) {
        if(event == null) return;

        try {

            Stage popupStage = new Stage();

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("presentation/view/ViewEvent.fxml"));
            Parent root = loader.load();
            ViewEventController controller = loader.getController();
            controller.setTicketModel(ticketModel);
            controller.setEventModel(eventModel);
            controller.setCurrentEvent(event);

            Scene popupScene = new Scene(root);

            popupStage.setScene(popupScene);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UNDECORATED);

            LogoLoader.addLogoToStage(popupStage);

            popupStage.showAndWait();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
