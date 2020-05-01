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
import javafx.scene.control.Alert.AlertType;

public class Main extends Application {

    private Stage mainStage;
    private Card[] selectedCards = new Card[4];

    public void start(Stage primaryStage) throws Exception{
        this.mainStage = primaryStage;
        primaryStage.setOnCloseRequest(confirmCloseEventHandler);
        //Parent rootP = FXMLLoader.load(getClass().getResource("sample.fxml"))

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
        // Add hbox to gridPane at the bottom
        gridPane.add(hbox, 0, 7, 8,2);

        // Data structure to hold images of all cards
        Map<String, Card> cards = new HashMap<String, Card>();
        // Fill data structure
        setupCards(cards, hbox);

        // Add cards to GridPane
        addCards(gridPane, cards);


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
        gridPane.add(difficultyLabel, 13, 3);
        gridPane.add(difficultychoiceBox, 13, 4);


        /* * * * Buttons * * * */

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
            // Current number of selected cards
            int numSelected = hbox.getChildren().size();

            // ArrayList holding 0-51 to access imageViews indices
            // Using list to ensure that each number selected is
            // unique when being chosen
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int k = 0; k < 52; k++) {
                list.add(new Integer(k));
            }
             Collections.shuffle(list);  // Shuffle list of 0-51 to 'randomize' it


            // Switch cascades to add the appropriate number of
            // randomly selected cards depending on how many are
            // already selected
            switch (numSelected) {
                case 0:
                    selectedCards[0] = cards.get("card" + list.get(0));
                    ImageView cardImage1 = selectedCards[0].getImage();
                    hbox.getChildren().add(cardImage1);
                case 1:
                    selectedCards[1] = cards.get("card" + list.get(1));
                    ImageView cardImage2 = selectedCards[1].getImage();
                    hbox.getChildren().add(cardImage2);
                case 2:
                    selectedCards[2] = cards.get("card" + list.get(2));
                    ImageView cardImage3 = selectedCards[2].getImage();
                    hbox.getChildren().add(cardImage3);
                case 3:
                    selectedCards[3] = cards.get("card" + list.get(3));
                    ImageView cardImage4 = selectedCards[3].getImage();
                    hbox.getChildren().add(cardImage4);
                    break;
                default:
                    System.out.println("Already 4 Cards selected");
            }

            // Disable Random Deal button
            randomDealBtn.setDisable(true);

            // ToDo: Still need to re-enable the button once the next turn starts

        });

        // -- Confirm Deal --
        // create an alert
        Alert a = new Alert(Alert.AlertType.NONE);

        Button confirmDealBtn = new Button("Confirm Selection");
        confirmDealBtn.setOnAction(e -> {
            a.setAlertType(Alert.AlertType.CONFIRMATION);
            a.setContentText("Are you sure you want to sell these cards?");
            a.show();
        });

        // -- Reset Deal --
        Button resetDealBtn = new Button("Reset Cards");
        resetDealBtn.setOnAction(e -> {
            int numSelected = hbox.getChildren().size();
            Card curCard;

            for(int i = 0; i < numSelected; i++) {
                curCard = selectedCards[i];
                gridPane.add(curCard.getImage(), curCard.getXPos(), curCard.getYPos());
            }

            // Clear Selected Cards array
            Arrays.fill(selectedCards, null);

            // Reset random deal button since all cards deselected
            randomDealBtn.setDisable(false);
        });

        // Add buttons to GridPane
        gridPane.add(randomDealBtn, 13, 0);
        gridPane.add(guessBtn, 13, 1);
        gridPane.add(closeButton, 13, 2);
        gridPane.add(confirmDealBtn, 13, 7);
        gridPane.add(resetDealBtn, 13, 8);

        Scene scene = new Scene(gridPane);

        primaryStage.setTitle("Art Dealer Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Maps keys and images together for all 52 cards
    public void setupCards(Map<String, Card> cards, HBox hbox) {

        int i = 0;  // Column counter and value(add 1 for constructor)
        int j = 0;  // Row counter and suit
        // Creating Map of Card Objects
        for (int k = 0; k < 52; k++) {
            cards.put("card" + k, new Card(i + 1, j, i, j, new ImageView( new Image("https://liveexample.pearsoncmg.com/book/image/card/"
                    + (k + 1) + ".png"))));
            //
            if((i % 12) == 0 && i != 0) {
                j++;
                i = 0;
            } else {
                i++;
            }

            int finalK = k;
            // Adds click event handler to place card in hbox
            cards.get("card" + k).getImage().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                int numSelected = hbox.getChildren().size();
                Card curCard = cards.get("card" + finalK);
                if(numSelected < 4) {
                    hbox.getChildren().add(curCard.getImage());
                    selectedCards[numSelected] = curCard;
                    System.out.println("x: " + selectedCards[numSelected].getXPos() + " y: " + curCard.getYPos());
                }

                e.consume();
            });

        }

    }

    // Add all cards in gridPane
    public void addCards(GridPane gridPane, Map<String, Card> cards) {
        int j = 0;  // Row counter
        int i = 0;  // Column counter
        for(int x = 0; x < 52; x++) {   // x is used to access all the card views
            String key = "card" + x;
            gridPane.add(cards.get(key).getImage(), i, j);
            // Adding cards to grid with 13 in each Row
            if((i % 12) == 0 && i != 0) {
                j++;
                i = 0;
            } else {
                i++;
            }
        }
    }

    // Display alert to confirm user wants to close app
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