package sample;

import java.util.*;
import java.awt.event.InputEvent;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;

public class Main extends Application {
    private Stage mainStage;
    private Card[] selectedCards = new Card[4];
    private static int guessCounter;
    private static int difficulty;
    private int numOfCardsBought = 0;

    // Difficulty level codes
    public final static int EASY = 0, MEDIUM = 1, HARD = 2;

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

        // GridPane styling
        gridPane.setStyle("-fx-background-color: GREEN; -fx-font-family: 'Cambria Math';");
        /* * * * * * * * * * * * * */

        // Set Difficulty
        /* ToDo: Need to implement a selection of difficulty at
                 at start of the game */
        // Alert to have user choose Difficulty
        Alert difficultyAlert = new Alert(AlertType.INFORMATION);
        //difficultyAlert.set;

        //difficulty = EASY; // Set arbitrarily for now
        //String difficultyDisplay = setDifficulty();
        String difficultyDisplay = "";

        /* ToDo: Need to consider how to implement difficulty selection
         - have different display at start of game?
         - allow changing in middle of game? */

//        //Label for difficulty
//        Text difficultyLabel = new Text("Difficulty: " + difficultyDisplay);
//        // Add difficulty label to gridPane
//        gridPane.add(difficultyLabel, 13, 3);

        // Current Card section
        HBox hbox = new HBox();
        hbox.setMinHeight(150);
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");
        // Add hbox to gridPane at the bottom
        gridPane.add(hbox, 0, 7, 8,2);

        // Guess Counter box
        VBox guessVbox = new VBox();
        Text guessLabel = new Text("Guesses Remaining:");
        guessVbox.setStyle("-fx-font-size: 125%; -fx-alignment: center; -fx-border-color: black; -fx-border-width: 2px;" +
                "-fx-border-radius: 95%;");
        // We add vbox and the number of guesses remaining after difficulty chosen

        // Add guessHbox to gridPane
        gridPane.add(guessVbox, 9,7,2,1);

        // Data structure to hold images of all cards
        Map<String, Card> cards = new HashMap<String, Card>();
        // Fill data structure
        setupCards(cards, hbox);

        // Add cards to GridPane
        addCards(gridPane, cards);


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

            Alert a = new Alert(AlertType.WARNING);
            a.setContentText("0 Guesses remaining! Game Lost!");

            if (guessCounter > 0) {
                // Decrement global guess counter
                guessCounter--;
                // Remove old label
                guessVbox.getChildren().remove(1);
                // Create new label
                Text guessCounterLabel = new Text(String.valueOf(guessCounter));
                // Add label to guessVbox
                guessVbox.getChildren().add(1, guessCounterLabel);
            }
            if (guessCounter == 0) {
                // Show game lost alert if guesses are 0
                a.show();
                guessBtn.setDisable(true);
            }
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

        // -- Reset Deal --
//        public void resetEventHandler() {
//            @Override
//            public void handle(MouseEvent event) {
//                int numSelected = hbox.getChildren().size();
//                Card curCard;
//
//                for(int i = 0; i < numSelected; i++) {
//                    curCard = selectedCards[i];
//                    gridPane.add(curCard.getImage(), curCard.getXPos(), curCard.getYPos());
//                }
//
//                // Clear Selected Cards array
//                Arrays.fill(selectedCards, null);
//
//                // Reset random deal button since all cards deselected
//                randomDealBtn.setDisable(false);
//            }
//        };

        Button resetDealBtn = new Button("Reset Cards");
        resetDealBtn.setOnAction(e -> {
            resetEvent(hbox, gridPane, randomDealBtn);
        });

        // -- Confirm Deal --
        // create an alert
        Alert a = new Alert(Alert.AlertType.NONE);

        Button confirmDealBtn = new Button("Confirm Selection");
        confirmDealBtn.setOnAction(e -> {
            int numSelected = hbox.getChildren().size();

            // If there are 4 cards selected to deal
            if (numSelected == 4) {
                a.setAlertType(Alert.AlertType.CONFIRMATION);
                a.setContentText("Are you sure you want to sell these cards?");
                Optional<ButtonType> result = a.showAndWait();

                if(!result.isPresent()) {
                    // Alert is exited, no button has been pressed.
                    System.out.println("Confirm exited");
                }
                // Deal Confirmed
                else if(result.get() == ButtonType.OK) {
                    // OK button is pressed
                    System.out.println("Confirmed!!!");

                    Card boughtCards[] = new Card[4];

                    // Logic for the computer to take turn
                    // -- Maybe have a global boolean that says it's seller's turn and flip it here

                    Alert selectCards = new Alert(AlertType.CONFIRMATION);
                    selectCards.setTitle("Cards that are Dealt");
                    selectCards.setHeaderText("Choose the 'paintings' to buy.");
                    selectCards.setContentText("Choose your 'paintings'.");
                    // Toggle buttons to choose which cards to buy
                    ToggleButton toggle1 = new ToggleButton();
                    ToggleButton toggle2 = new ToggleButton();
                    ToggleButton toggle3 = new ToggleButton();
                    ToggleButton toggle4 = new ToggleButton();
                    // Grab currently selected/dealt cards
                    Card card1 = selectedCards[0];
                    Card card2 = selectedCards[1];
                    Card card3 = selectedCards[2];
                    Card card4 = selectedCards[3];
                    // Add image of cards selected to toggle buttons
                    toggle1.setStyle("-fx-graphic: url('" + card1.getImageUrl() + "')");
                    toggle2.setStyle("-fx-graphic: url('" + card2.getImageUrl() + "')");
                    toggle3.setStyle("-fx-graphic: url('" + card3.getImageUrl() + "')");
                    toggle4.setStyle("-fx-graphic: url('" + card4.getImageUrl() + "')");
                    // Set size of each toggle button
                    toggle1.setMinSize(125, 150);
                    toggle1.setMaxSize(125, 150);
                    toggle2.setMinSize(125, 150);
                    toggle2.setMaxSize(125, 150);
                    toggle3.setMinSize(125, 150);
                    toggle3.setMaxSize(125, 150);
                    toggle4.setMinSize(125, 150);
                    toggle4.setMaxSize(125, 150);

                    // Setup new window to choose cards to buy / show bought cards
                    final Stage dialog = new Stage();
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.initOwner(primaryStage);

                    HBox dialogHbox = new HBox(20);
                    dialogHbox.getChildren().addAll(toggle1, toggle2, toggle3, toggle4);

                    // Confirm Buy Button
                    Button confirmBuyBtn = new Button("Purchase Selected 'Paintings'");
                    confirmBuyBtn.setOnAction(e2 -> {
                        numOfCardsBought = 0;
                        // Add text
                        Text message = new Text("These were the cards purchased!!");
                        message.setStyle("-fx-font-size: 150%; -fx-font-weight: bolder;");
                        dialogHbox.getChildren().add(message);
                        // Place bought card images in dialogHbox
                        if(toggle1.isSelected()) {
                            dialogHbox.getChildren().add(new ImageView(card1.getImageUrl()));
                            numOfCardsBought++;
                        }
                        if(toggle2.isSelected()) {
                            dialogHbox.getChildren().add(new ImageView(card2.getImageUrl()));
                            numOfCardsBought++;
                        }
                        if(toggle3.isSelected()) {
                            dialogHbox.getChildren().add(new ImageView(card3.getImageUrl()));
                            numOfCardsBought++;
                        }
                        if(toggle4.isSelected()) {
                            dialogHbox.getChildren().add(new ImageView(card4.getImageUrl()));
                            numOfCardsBought++;
                        }

                        // Remove toggle buttons
                        dialogHbox.getChildren().removeAll(toggle1, toggle2, toggle3, toggle4);
                        // Remove confirmBuyBtn
                        dialogHbox.getChildren().remove(confirmBuyBtn);
                        // Close stage after x amount of time
                        PauseTransition delay = new PauseTransition(Duration.seconds(5));
                        delay.setOnFinished( event -> dialog.close() );
                        delay.play();
                        // Reset cards
                        resetEvent(hbox, gridPane, randomDealBtn);
                    });
                    // Add confirmBuyBtn to dialogHbox
                    dialogHbox.getChildren().add(confirmBuyBtn);

                    Scene dialogScene = new Scene(dialogHbox, 800, 200);
                    dialog.setScene(dialogScene);
                    dialog.show();

                }
                // Deal cancelled
                else if(result.get() == ButtonType.CANCEL) {
                    // Cancel button is pressed
                    System.out.println("Confirm cancelled");
                }
            // If there aren't 4 cards selected to deal
            } else {
                a.setAlertType(AlertType.INFORMATION);
                a.setContentText("MUST be 4 cards chosen to deal!");
                a.show();
            }


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

        /* * * After UI loads and displays... * * */

        // Have user choose difficulty and set ui and counters appropriately
        difficultyDisplay = setupDifficulty();

        //Label for difficulty
        Text difficultyLabel = new Text("Difficulty: " + difficultyDisplay);
        // Add difficulty label to gridPane
        gridPane.add(difficultyLabel, 13, 3);

        // Set guesses remaining text and add to ui
        Text guessesRemainingLabel = new Text(String.valueOf(getGuessCounter()));
        guessVbox.getChildren().addAll(guessLabel, guessesRemainingLabel);

//        game(primaryStage);
    }

    // Maps keys and images together for all 52 cards
    public void setupCards(Map<String, Card> cards, HBox hbox) {

        int i = 0;  // Column counter and value(add 1 for constructor)
        int j = 0;  // Row counter and suit
        // Creating Map of Card Objects
        for (int k = 0; k < 52; k++) {
            cards.put("card" + k, new Card(i + 1, j, i, j, new ImageView( new Image("https://liveexample.pearsoncmg.com/book/image/card/"
                    + (k + 1) + ".png")), "https://liveexample.pearsoncmg.com/book/image/card/"
                    + (k + 1) + ".png"));
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

    public void resetEvent(HBox hbox, GridPane gridPane, Button randomDealBtn) {
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
    };

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

    public static void resetGame() {
        // Put guesses back to appropriate level


        // Randomly select new pattern

        //
    }

    public static String setupDifficulty() {
        String diffDisplay = "";

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Difficulty Level");
        alert.setHeaderText("Choose the level of Difficulty you'd like to play");
        alert.setContentText("Choose your option.");

        ButtonType buttonEasy = new ButtonType("Easy");
        ButtonType buttonMedium = new ButtonType("Medium");
        ButtonType buttonHard = new ButtonType("Hard");

        alert.getButtonTypes().setAll(buttonEasy, buttonMedium, buttonHard);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonEasy){
            // ... user chose "Easy"
            setDifficulty(EASY);
        } else if (result.get() == buttonMedium) {
            // ... user chose "Medium"
            setDifficulty(MEDIUM);
        } else if (result.get() == buttonHard) {
            // ... user chose "Hard"
            setDifficulty(HARD);
        }

        // Set number of guesses allowed and display for difficulty level
        switch(getDifficulty()) {
            case EASY:
                setGuessCounter(10);
                diffDisplay = "Easy";
                break;
            case MEDIUM:
                setGuessCounter(8);
                diffDisplay = "Medium";
                break;
            case HARD:
                setGuessCounter(6);
                diffDisplay = "Hard";
                break;
        }

        return diffDisplay;
    }

    public static int getGuessCounter() { return guessCounter; }
    public static void setGuessCounter(int guesses) { guessCounter = guesses; }

    public static int getDifficulty() { return difficulty; }
    public static void setDifficulty(int diff) { difficulty = diff; }


    // Construct to switch turns
    public static void game(Stage stage, HBox hbox, VBox vbox, GridPane gridPane) {
        boolean proceed = true;

        System.out.println("Game Started!");

    }

    // The Art Seller -- Human
    public static void player1Turn() {

    }

    // The Art Dealer/Buyer -- Could be Computer or human
    public static void player2Turn() {

    }


    public static void main(String[] args) {
        launch(args);
    }
}