package project.printseven.service;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
import project.printseven.dto.UserRes;

public class UserListCellFactory implements Callback<ListView<UserRes>, ListCell<UserRes>> {

    @Override
    public ListCell<UserRes> call(ListView<UserRes> param) {
        return new ListCell<UserRes>() {
            @Override
            protected void updateItem(UserRes userRes1, boolean empty) {
                System.out.println("!!!!!!!!!!!!!!");
                super.updateItem(userRes1, empty);
                UserRes userRes = new UserRes(1L,"wrwer",12L,123L,213L,3L,21L,112L);
                if (userRes != null) {
                    HBox hbox = new HBox();
                    Label emailLabel = new Label("Email: ");
                    Label emailValueLabel = new Label(userRes.getEmail());
                    Label successa4 = new Label("Success A4: ");
                    Label successa4ValueLabel = new Label(String.valueOf(userRes.getSuccessA4()));
                    Label two = new Label("Success A5: ");
                    Label twoValueLabel = new Label(String.valueOf(userRes.getSuccessA5()));
                    Label tree = new Label("Success A5: ");
                    Label treeValueLabel = new Label(String.valueOf(userRes.getSuccessA5()));

                    HBox.setHgrow(emailValueLabel, Priority.ALWAYS);
                    HBox.setHgrow(successa4ValueLabel, Priority.ALWAYS);
                    HBox.setHgrow(twoValueLabel, Priority.ALWAYS);
                    HBox.setHgrow(treeValueLabel, Priority.ALWAYS);

                    hbox.getChildren().addAll(emailLabel, emailValueLabel, successa4, successa4ValueLabel,two,twoValueLabel,tree,treeValueLabel);
                    hbox.setSpacing(10);
                    hbox.setPadding(new Insets(5));

                    setGraphic(hbox);
                } else {
                    setGraphic(null);
                }
            }
        };
    }
}
