package sample;

import java.util.*;
import java.awt.event.InputEvent;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    private Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.mainStage = primaryStage;
        primaryStage.setOnCloseRequest(confirmCloseEventHandler);
        //Parent rootP = FXMLLoader.load(getClass().getResource("sample.fxml"));

        // Fill list with string values used to fetch images
        // in setupCardImageViews()
        ArrayList<String> cards = new ArrayList<>();
        for (int i = 0; i < 52; i++) {
            cards.add(String.valueOf(i + 1));
        }

        /* * * GRID PANE SETUP * * */
        //Creating a Grid Pane
        GridPane gridPane = new GridPane();

        //Setting size for the pane
        gridPane.setMinSize(400, 200);

        //Setting the padding
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        //Setting the vertical and horizontal gaps between the columns
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        //Setting the Grid alignment
        gridPane.setAlignment(Pos.CENTER);

        gridPane.setStyle("-fx-background-color: GREEN;");
        /* * * * * * * * * * * * * */

        // Current Card section
        HBox hbox = new HBox();
        hbox.setMinHeight(150);
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");
        // Add hbox to gridPane
        gridPane.add(hbox, 0, 7, 8,2);

        // Data structure to hold images of all cards
        Map<String, ImageView> imageViews = new HashMap<String, ImageView>();
        // Fill data structure
        setupCardImageViews(cards, imageViews, hbox);

        // Add cards to GridPane
        addCards(gridPane, imageViews);


        /* ToDo: Need to consider how to implement difficulty selection
             - have different display at start of game?
             - allow changing in middle of game?*/
        //Label for difficulty
        Text difficultyLabel = new Text("Difficulty");
        //Choice box for difficulty
        ChoiceBox difficultychoiceBox = new ChoiceBox();
        difficultychoiceBox.getItems().addAll
                ("Easy (K-2)", "Medium (3-5)", "Hard (6-8)");

        // Add to difficulty choiceBox gridPane
        gridPane.add(difficultyLabel, 8, 6);
        gridPane.add(difficultychoiceBox, 9, 6);


        /* * * Buttons * * */

        // -- Close Application --
        Button closeButton = new Button("Close Application");
        closeButton.setOnAction(event ->
                primaryStage.fireEvent(new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST))
        );

        // -- Make a Guess --
        Button guessBtn = new Button("Make a Guess");
        guessBtn.setOnAction(e -> {
            // ToDo: Need to add real action
                // 1. If playing against computer
                    // a. Show input box for guess
                    // b.
                // 2. If playing against Student
                    // a. Show input box for guess
                    // b. Once entered, show 'Correct' and 'Wrong' buttons
                    // c. 2nd player will click one of the buttons
                        // i. Correct -> Game is over and player won
                        // ii. Wrong  -> Decrement guess counter, start next turn

            // Test output
            System.out.println("Guess was clicked!");
        });

        // -- Random Deal --
        Button randomDealBtn = new Button("Random Deal");
        randomDealBtn.setOnAction(e -> {
            // Implement the Card Class once that is complete


            // ArrayList holding 0-51 to access imageViews indices
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int k = 0; k < 52; k++) {
                list.add(new Integer(k));
            }
            Collections.shuffle(list);  // Shuffle list of 0-51 to 'randomize' it

            // Now grab 4 'random numbers' from the shuffled list to grab cards
            ImageView card1 = imageViews.get("view" + list.get(0));
            ImageView card2 = imageViews.get("view" + list.get(1));
            ImageView card3 = imageViews.get("view" + list.get(2));
            ImageView card4 = imageViews.get("view" + list.get(3));

            // Add 4 random cards to selected cards box
            hbox.getChildren().addAll(card1, card2, card3, card4);

            // Disable Random Deal button
            randomDealBtn.setDisable(true);

            // ToDo: Still need to re-enable the button once the next turn starts

        });

        // Add buttons to GridPane
        gridPane.add(randomDealBtn, 9, 0);
        gridPane.add(guessBtn, 9, 1);
        gridPane.add(closeButton, 9, 2);

        Scene scene = new Scene(gridPane);

        primaryStage.setTitle("Art Dealer Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Maps keys and images together for all 52 cards
    public void setupCardImageViews(ArrayList<String> cards, Map<String, ImageView> imageViews, HBox hbox) {
        for (int i = 0; i < 52; i++) {
            imageViews.put("view" + i, new ImageView( new Image("https://liveexample.pearsoncmg.com/book/image/card/"
                    + cards.get(i) + ".png")));
            int finalI = i;
            imageViews.get("view" + i).addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                hbox.getChildren().add(imageViews.get("view" + finalI));
                e.consume();
            });
        }
    }

    public void addCards(GridPane gridPane, Map<String, ImageView> imageViews) {
        // Add all cards in gridPane
        int j = 0;
        int i = 0;
        for(int x = 0; x < 52; x++) {
            String key = "view" + x;
            gridPane.add(imageViews.get(key), i, j);
            // Adding cards to grid with 9 in each Row
            if((i % 8) == 0 && i != 0) {
                j++;
                i = 0;
            } else {
                i++;
            }
        }
    }

    private EventHandler<WindowEvent> confirmCloseEventHandler = event -> {
        Alert closeConfirmation = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure you want to exit?"
        );
        Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(
                ButtonType.OK
        );
        exitButton.setText("Exit");
        closeConfirmation.setHeaderText("Confirm Exit");
        closeConfirmation.initModality(Modality.APPLICATION_MODAL);
        closeConfirmation.initOwner(mainStage);

        Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
        if (!ButtonType.OK.equals(closeResponse.get())) {
            event.consume();
        }
    };

    public static void main(String[] args) {
        launch(args);
    }
}