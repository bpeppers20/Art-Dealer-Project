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
import jdk.nashorn.internal.runtime.arrays.ContinuousArrayData;

public class Main extends Application {
    // Needed public/ global variables
    public static String selectPattern = ""; // Pattern that the cpu will select
    public static int selectPatternIndex;   // Holds index of pattern to determine single or multi card matching
    public static PatternMatch[] currentPatternMatches; // Holds single card pattern matches
    public static PatternMatch[][] currentMultiCardPatternMatches;  // Holds multi card pattern matches
    public static String playerGuess = ""; // Player Guess

    private Stage mainStage;
    private Card[] selectedCards = new Card[4];
    private static int guessCounter;
    private static int difficulty;
    private int numOfCardsBought = 0;
    private static boolean isTwoPlayer = false;

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

        String difficultyDisplay = "";

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

        // Variables for Choices
        // All Guessing Options
        String [] choices1 = {"All Red", "All Black", "All Kings", "All Jacks", "All Queens", "All Aces",
                "All Even", "All Odd", "All Face", "Black Kings", "Black Queens", "Black Aces", "Black Jacks",
                "Red Kings", "Red Queens","Red Aces","Red Jacks", "Two of a Kind", "Three of a Kind"}; // Will Add more after testing
        // < 6 = k-2; < 17 = 3-5; entire list = 6-8

        // Stores all the possible cards that match a pattern
        // i.e. cards to buy when a specific pattern is chosen
        Map<String, PatternMatch[]> patternMatches = new HashMap<String, PatternMatch[]>();
        Map<String, PatternMatch[][]> multiCardPatternMatches = new HashMap<String, PatternMatch[][]>();

        // Build Map to store cards that match each pattern
        setupPatternMatches(patternMatches, multiCardPatternMatches, cards, choices1);

        // Drop down menu for guesses
        ChoiceBox guesses = new ChoiceBox();
        for (int i= 0; i < choices1.length; i++)
        {
            guesses.getItems().add(choices1[i]);
        }
        gridPane.add(guesses, 12, 6);
      
        // Start Game button: stores randomly selected pattern for this game 
        Button startGame = new Button("Start Game");
        startGame.setOnAction(event -> storePattern(choices1, patternMatches, multiCardPatternMatches));
        gridPane.add(startGame, 13, 9);
        // -- Make a Guess --
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

        // Make Answer to be guessed

        // Test output
        System.out.println(playerGuess);
        Button guessBtn = new Button("Make a Guess");

        guessBtn.setOnAction(e -> guessEvent(selectPattern, playerGuess, guesses, guessVbox, guessBtn));

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

                    // If Two Players, setup and show card selection box to buy cards
                    if (getIsTwoPlayer()) {
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
                            if (toggle1.isSelected()) {
                                dialogHbox.getChildren().add(new ImageView(card1.getImageUrl()));
                                numOfCardsBought++;
                            }
                            if (toggle2.isSelected()) {
                                dialogHbox.getChildren().add(new ImageView(card2.getImageUrl()));
                                numOfCardsBought++;
                            }
                            if (toggle3.isSelected()) {
                                dialogHbox.getChildren().add(new ImageView(card3.getImageUrl()));
                                numOfCardsBought++;
                            }
                            if (toggle4.isSelected()) {
                                dialogHbox.getChildren().add(new ImageView(card4.getImageUrl()));
                                numOfCardsBought++;
                            }

                            // Remove toggle buttons
                            dialogHbox.getChildren().removeAll(toggle1, toggle2, toggle3, toggle4);
                            // Remove confirmBuyBtn
                            dialogHbox.getChildren().remove(confirmBuyBtn);
                            // Close stage after x amount of time
                            PauseTransition delay = new PauseTransition(Duration.seconds(5));
                            delay.setOnFinished(event -> dialog.close());
                            delay.play();
                            // Reset cards
                            resetEvent(hbox, gridPane, randomDealBtn);
                        });
                        // Add confirmBuyBtn to dialogHbox
                        dialogHbox.getChildren().add(confirmBuyBtn);

                        Scene dialogScene = new Scene(dialogHbox, 800, 200);
                        dialog.setScene(dialogScene);
                        dialog.show();
                    // If One Player, run computer logic
                    } else {
                        /* * * * * * * * * * * * * * * * * * */
                        // Code for computer logic to buy cards
                        /* * * * * * * * * * * * * * * * * * */

                        // Setup new window to show bought cards by computer
                        final Stage dialog = new Stage();
                        dialog.initModality(Modality.APPLICATION_MODAL);
                        dialog.initOwner(primaryStage);

                        HBox dialogHbox = new HBox(20);

                        // Add text
                        Text message = new Text("These were the cards purchased!!");
                        message.setStyle("-fx-font-size: 150%; -fx-font-weight: bolder;");
                        dialogHbox.getChildren().add(message);

                        /* Logic to choose cards and place them in the dialogHbox */
                        // Grab currently selected/dealt cards
                        Card card1 = selectedCards[0];
                        Card card2 = selectedCards[1];
                        Card card3 = selectedCards[2];
                        Card card4 = selectedCards[3];

                        // Single card matching
                        if (selectPatternIndex < 17) {
                            // Loops through all possible pattern matches and if any of the
                            // four selected cards match it will be added to the dialogHbox
                            for (int i = 0; i < currentPatternMatches.length; i++) {
                                if(currentPatternMatches[i] != null) {
                                    if (currentPatternMatches[i].getValue() == card1.getValue()) {
                                        if (currentPatternMatches[i].getSuit() == card1.getSuit()) {
                                            if (currentPatternMatches[i].getColor() == card1.getColor())
                                                dialogHbox.getChildren().add(new ImageView(card1.getImageUrl()));
                                        }
                                    }
                                    if (currentPatternMatches[i].getValue() == card2.getValue()) {
                                        if (currentPatternMatches[i].getSuit() == card2.getSuit()) {
                                            if (currentPatternMatches[i].getColor() == card2.getColor())
                                                dialogHbox.getChildren().add(new ImageView(card2.getImageUrl()));
                                        }
                                    }
                                    if (currentPatternMatches[i].getValue() == card3.getValue()) {
                                        if (currentPatternMatches[i].getSuit() == card3.getSuit()) {
                                            if (currentPatternMatches[i].getColor() == card3.getColor())
                                                dialogHbox.getChildren().add(new ImageView(card3.getImageUrl()));
                                        }
                                    }
                                    if (currentPatternMatches[i].getValue() == card4.getValue()) {
                                        if (currentPatternMatches[i].getSuit() == card4.getSuit()) {
                                            if (currentPatternMatches[i].getColor() == card4.getColor())
                                                dialogHbox.getChildren().add(new ImageView(card4.getImageUrl()));
                                        }
                                    }
                                }
                            }
                        } else {
                            // Multi card matching
                            switch (selectPattern) {
                                case "Two of a Kind":
                                    // Finds two cards with same value i.e. two of a kind/pair
                                    // Setup in else if so only one pair is selected from the bunch
                                    if (card1.getValue() == card2.getValue()) {
                                        dialogHbox.getChildren().add(new ImageView(card1.getImageUrl()));
                                        dialogHbox.getChildren().add(new ImageView(card2.getImageUrl()));
                                    } else if (card1.getValue() == card3.getValue()) {
                                        dialogHbox.getChildren().add(new ImageView(card1.getImageUrl()));
                                        dialogHbox.getChildren().add(new ImageView(card3.getImageUrl()));
                                    } else if (card1.getValue() == card4.getValue()) {
                                        dialogHbox.getChildren().add(new ImageView(card1.getImageUrl()));
                                        dialogHbox.getChildren().add(new ImageView(card4.getImageUrl()));
                                    } else if (card2.getValue() == card3.getValue()) {
                                        dialogHbox.getChildren().add(new ImageView(card2.getImageUrl()));
                                        dialogHbox.getChildren().add(new ImageView(card3.getImageUrl()));
                                    } else if (card2.getValue() == card4.getValue()) {
                                        dialogHbox.getChildren().add(new ImageView(card2.getImageUrl()));
                                        dialogHbox.getChildren().add(new ImageView(card4.getImageUrl()));
                                    } else if (card3.getValue() == card4.getValue()) {
                                        dialogHbox.getChildren().add(new ImageView(card3.getImageUrl()));
                                        dialogHbox.getChildren().add(new ImageView(card4.getImageUrl()));
                                    }
                                    break;
                                case "Three of a Kind":
                                    // Finds three cards with same value i.e. three of a kind
                                    // Setup in else if so only one trio is selected from the bunch
                                    if (card1.getValue() == card2.getValue() && card1.getValue() == card3.getValue()) {
                                        dialogHbox.getChildren().add(new ImageView(card1.getImageUrl()));
                                        dialogHbox.getChildren().add(new ImageView(card2.getImageUrl()));
                                        dialogHbox.getChildren().add(new ImageView(card3.getImageUrl()));
                                    } else if (card1.getValue() == card2.getValue() && card1.getValue() == card4.getValue()) {
                                        dialogHbox.getChildren().add(new ImageView(card1.getImageUrl()));
                                        dialogHbox.getChildren().add(new ImageView(card2.getImageUrl()));
                                        dialogHbox.getChildren().add(new ImageView(card4.getImageUrl()));
                                    } else if (card1.getValue() == card3.getValue() && card1.getValue() == card4.getValue()) {
                                        dialogHbox.getChildren().add(new ImageView(card1.getImageUrl()));
                                        dialogHbox.getChildren().add(new ImageView(card3.getImageUrl()));
                                        dialogHbox.getChildren().add(new ImageView(card4.getImageUrl()));
                                    } else if (card2.getValue() == card3.getValue() && card2.getValue() == card4.getValue()) {
                                        dialogHbox.getChildren().add(new ImageView(card2.getImageUrl()));
                                        dialogHbox.getChildren().add(new ImageView(card3.getImageUrl()));
                                        dialogHbox.getChildren().add(new ImageView(card4.getImageUrl()));
                                    }
                            }
                        }
                        Scene dialogScene = new Scene(dialogHbox, 800, 200);
                        dialog.setScene(dialogScene);
                        dialog.show();

                        // Close stage after x amount of time
                        PauseTransition delay = new PauseTransition(Duration.seconds(5));
                        delay.setOnFinished(event -> dialog.close());
                        delay.play();

                        // Reset cards
                        resetEvent(hbox, gridPane, randomDealBtn);
                    }
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
    }

    // Maps keys and images together for all 52 cards
    public void setupCards(Map<String, Card> cards, HBox hbox) {

        int i = 0;  // Column counter and value(add 1 for constructor)
        int j = 0;  // Row counter and suit
        // Creating Map of Card Objects
        for (int k = 0; k < 52; k++) {
            /*  If/else constructs Card objects Black or Red relative to the order the images are stored
                Black Cards are 0-12 & 39-51  ---- Red Cards are 13-38 */
            // Black Cards
            if (k < 13 || k > 38 ) {
                cards.put("card" + k, new Card(i + 1, j, Card.BLACK, i, j, new ImageView(new Image("https://liveexample.pearsoncmg.com/book/image/card/"
                        + (k + 1) + ".png")), "https://liveexample.pearsoncmg.com/book/image/card/"
                        + (k + 1) + ".png"));
            // Red Cards
            } else {
                cards.put("card" + k, new Card(i + 1, j, Card.RED, i, j, new ImageView(new Image("https://liveexample.pearsoncmg.com/book/image/card/"
                        + (k + 1) + ".png")), "https://liveexample.pearsoncmg.com/book/image/card/"
                        + (k + 1) + ".png"));
            }

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


    // Was sending in global variables, just need to access them no need to use as arguments
    private void storePattern(String[] choices1, Map<String, PatternMatch[]> patternMatches, Map<String, PatternMatch[][]> multiCardPatternMatches) // Cpu selects pattern at random
    {
        int upperBound = -1; // Limit Question based on difficulty
        if (difficulty == 0)
            upperBound = 6;
        if (difficulty == 1)
            upperBound = 19;
        if (difficulty == 2)
            upperBound = choices1.length;
        // Randomly selecting pattern from choices with random index
        Random rand = new Random();
        selectPatternIndex = rand.nextInt(upperBound);
        selectPattern = choices1[selectPatternIndex];



        System.out.println("Current Pattern: " + selectPattern);

        // 17 is last index of EASY and MEDIUM, all of which only match single cards
        if (selectPatternIndex < 17) {
            currentPatternMatches = new PatternMatch[patternMatches.get(selectPattern).length];
            currentPatternMatches = patternMatches.get(selectPattern).clone();
            // If above 17 it's a multi card matching pattern
        } else {
            currentMultiCardPatternMatches = new PatternMatch[multiCardPatternMatches.get(selectPattern).length][];
            currentMultiCardPatternMatches = multiCardPatternMatches.get(selectPattern).clone();
        }
    }

    public void guessEvent(String s1, String playerGuess, ChoiceBox<String> guesses, VBox guessVbox, Button guessBtn)
    {
        selectPattern = s1;
        playerGuess = guesses.getValue();
        System.out.println("Guess was clicked!");
      
        // Alert if the player has used all guesses, only shows when 0 guesses
        Alert a = new Alert(AlertType.WARNING);
        a.setContentText("0 Guesses remaining! Game Lost!");
      
        if (playerGuess.equals(selectPattern))
        {
            System.out.println("Your Guess was Correct!");
        }
        else{
            System.out.println("Your Guess was Wrong! Try Again!");
          
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
        }
        //System.out.println(playerGuess);
        //System.out.println("pattern = " + selectPattern);
    }

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
        alert.setContentText("Choose your option. AFTER you've selected your difficulty" +
                " press the 'Start Game' button in the bottom right corner of the main" +
                " screen. This will select the pattern for you to figure out!");

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
            // Give user option of One or Two Players for Hard
            alert.setTitle("Number of Players");
            alert.setHeaderText("Choose how many Players will participate");
            alert.setContentText("Choose your option: One or Two Players. Remember to press" +
                    "'Start Game' after selecting!");
            // Buttons to select One or Two Players
            ButtonType buttonOnePlayer = new ButtonType("One Player");
            ButtonType buttonTwoPlayer = new ButtonType("Two Players");

            // Remove difficulty choice buttons
            alert.getButtonTypes().removeAll(buttonEasy, buttonMedium, buttonHard);
            // Add Number of Players choice buttons
            alert.getButtonTypes().setAll(buttonOnePlayer, buttonTwoPlayer);
            // Get result of button user pressed
            Optional<ButtonType> playerResult = alert.showAndWait();
            // Two Players if chosen
            if (playerResult.get() == buttonTwoPlayer) {
                setIsTwoPlayer(true);
            // One Player if chosen or box exited
            } else {
                setIsTwoPlayer(false);
            }
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

    public static void setupPatternMatches(Map<String, PatternMatch[]> pmMap, Map<String, PatternMatch[][]> multipmMap, Map<String, Card> cards, String[] patterns) {
        // Loops through all patterns and sets the matching patterns in appropriate Map structure
        for (int i = 0; i < patterns.length; i++) {
            // Store the pattern to
            String pattern = patterns[i];
            matchSingleCardsToPattern(pattern, pmMap, cards);
            matchMultipleCardsToPattern(pattern, multipmMap, cards);
        }

    }

    // Iterates through cards array to check which cards match the given pattern and store them in pmMap
    public static void matchSingleCardsToPattern(String pattern, Map<String, PatternMatch[]> pmMap, Map<String, Card> cards) {
        PatternMatch[] colorMatches = new PatternMatch[26]; // for All Red and All Black
        PatternMatch[] valueMatches = new PatternMatch[4]; // for Same Card value cases
        PatternMatch[] colorValueMatches = new PatternMatch[2]; // for Same Card and Color cases
        PatternMatch[] faceMatches = new PatternMatch[12]; // for Jack, Queen, Kings - face card cases
        PatternMatch[] evenOddMatches = new PatternMatch[20]; // for Even or Odd cases

        int arrayCounter = 0;

        switch(pattern) {
            case "All Red":
                for (int i = 0; i < cards.size(); i++) {
                    Card curCard = cards.get("card" + i);
                    if (curCard.getSuit() == Card.RED) {
                        colorMatches[arrayCounter] = new PatternMatch(curCard.getValue(), curCard.getSuit(), curCard.getColor());
                        arrayCounter++;
                    }
                }
                pmMap.put(pattern, colorMatches);       // Place pattern and matching array in Map object
                break;
            case "All Black":
                for (int i = 0; i < cards.size(); i++) {
                    Card curCard = cards.get("card" + i);
                    if (curCard.getSuit() == Card.BLACK) {
                        colorMatches[arrayCounter] = new PatternMatch(curCard.getValue(), curCard.getSuit(), curCard.getColor());
                        arrayCounter++;
                    }
                }
                pmMap.put(pattern, colorMatches);       // Place pattern and matching array in Map object
                break;
            case "All Kings":
                for (int i = 0; i < cards.size(); i++) {
                    Card curCard = cards.get("card" + i);
                    if (curCard.getValue() == Card.KING) {
                        valueMatches[arrayCounter] = new PatternMatch(curCard.getValue(), curCard.getSuit(), curCard.getColor());
                        arrayCounter++;
                    }
                }
                pmMap.put(pattern, valueMatches);       // Place pattern and matching array in Map object
                break;
            case "All Jacks":
                for (int i = 0; i < cards.size(); i++) {
                    Card curCard = cards.get("card" + i);
                    if (curCard.getValue() == Card.JACK) {
                        valueMatches[arrayCounter] = new PatternMatch(curCard.getValue(), curCard.getSuit(), curCard.getColor());
                        arrayCounter++;
                    }
                }
                pmMap.put(pattern, valueMatches);       // Place pattern and matching array in Map object
                break;
            case "All Queens":
                for (int i = 0; i < cards.size(); i++) {
                    Card curCard = cards.get("card" + i);
                    if (curCard.getValue() == Card.QUEEN) {
                        valueMatches[arrayCounter] = new PatternMatch(curCard.getValue(), curCard.getSuit(), curCard.getColor());
                        arrayCounter++;
                    }
                }
                pmMap.put(pattern, valueMatches);       // Place pattern and matching array in Map object
                break;
            case "All Aces":
                for (int i = 0; i < cards.size(); i++) {
                    Card curCard = cards.get("card" + i);
                    if (curCard.getValue() == Card.ACE) {
                        valueMatches[arrayCounter] = new PatternMatch(curCard.getValue(), curCard.getSuit(), curCard.getColor());
                        arrayCounter++;
                    }
                }
                pmMap.put(pattern, valueMatches);       // Place pattern and matching array in Map object
                break;
            case "All Even":
                for (int i = 0; i < cards.size(); i++) {
                    Card curCard = cards.get("card" + i);
                    if (curCard.getValue() >= 2 && curCard.getValue() <= 10 && curCard.getValue() % 2 == 0) {
                        evenOddMatches[arrayCounter] = new PatternMatch(curCard.getValue(), curCard.getSuit(), curCard.getColor());
                        arrayCounter++;
                    }
                }
                pmMap.put(pattern, evenOddMatches);     // Place pattern and matching array in Map object
                break;
            case "All Odd":
                for (int i = 0; i < cards.size(); i++) {
                    Card curCard = cards.get("card" + i);
                    if (curCard.getValue() > 2 && curCard.getValue() < 10 && curCard.getValue() % 2 != 0) {
                        evenOddMatches[arrayCounter] = new PatternMatch(curCard.getValue(), curCard.getSuit(), curCard.getColor());
                        arrayCounter++;
                    }
                }
                pmMap.put(pattern, evenOddMatches);     // Place pattern and matching array in Map object
                break;
            case "All Face":
                for (int i = 0; i < cards.size(); i++) {
                    Card curCard = cards.get("card" + i);
                    if (curCard.getValue() >= 11) {
                        faceMatches[arrayCounter] = new PatternMatch(curCard.getValue(), curCard.getSuit(), curCard.getColor());
                        arrayCounter++;
                    }
                }
                pmMap.put(pattern, faceMatches);     // Place pattern and matching array in Map object
                break;
            case "Black Kings":
                for (int i = 0; i < cards.size(); i++) {
                    Card curCard = cards.get("card" + i);
                    if (curCard.getValue() == Card.KING && curCard.getColor() == Card.BLACK) {
                        colorValueMatches[arrayCounter] = new PatternMatch(curCard.getValue(), curCard.getSuit(), curCard.getColor());
                        arrayCounter++;
                    }
                }
                pmMap.put(pattern, colorValueMatches);      // Place pattern and matching array in Map object
                break;
            case "Black Queens":
                for (int i = 0; i < cards.size(); i++) {
                    Card curCard = cards.get("card" + i);
                    if (curCard.getValue() == Card.QUEEN && curCard.getColor() == Card.BLACK) {
                        colorValueMatches[arrayCounter] = new PatternMatch(curCard.getValue(), curCard.getSuit(), curCard.getColor());
                        arrayCounter++;
                    }
                }
                pmMap.put(pattern, colorValueMatches);      // Place pattern and matching array in Map object
                break;
            case "Black Aces":
                for (int i = 0; i < cards.size(); i++) {
                    Card curCard = cards.get("card" + i);
                    if (curCard.getValue() == Card.ACE && curCard.getColor() == Card.BLACK) {
                        colorValueMatches[arrayCounter] = new PatternMatch(curCard.getValue(), curCard.getSuit(), curCard.getColor());
                        arrayCounter++;
                    }
                }
                pmMap.put(pattern, colorValueMatches);      // Place pattern and matching array in Map object
                break;
            case "Black Jacks":
                for (int i = 0; i < cards.size(); i++) {
                    Card curCard = cards.get("card" + i);
                    if (curCard.getValue() == Card.JACK && curCard.getColor() == Card.BLACK) {
                        colorValueMatches[arrayCounter] = new PatternMatch(curCard.getValue(), curCard.getSuit(), curCard.getColor());
                        arrayCounter++;
                    }
                }
                pmMap.put(pattern, colorValueMatches);      // Place pattern and matching array in Map object
                break;
            case "Red Kings":for (int i = 0; i < cards.size(); i++) {
                Card curCard = cards.get("card" + i);
                if (curCard.getValue() == Card.KING && curCard.getColor() == Card.RED) {
                    colorValueMatches[arrayCounter] = new PatternMatch(curCard.getValue(), curCard.getSuit(), curCard.getColor());
                    arrayCounter++;
                }
            }
                pmMap.put(pattern, colorValueMatches);      // Place pattern and matching array in Map object
                break;
            case "Red Queens":
                for (int i = 0; i < cards.size(); i++) {
                    Card curCard = cards.get("card" + i);
                    if (curCard.getValue() == Card.QUEEN && curCard.getColor() == Card.RED) {
                        colorValueMatches[arrayCounter] = new PatternMatch(curCard.getValue(), curCard.getSuit(), curCard.getColor());
                        arrayCounter++;
                    }
                }
                pmMap.put(pattern, colorValueMatches);      // Place pattern and matching array in Map object
                break;
            case "Red Aces":
                for (int i = 0; i < cards.size(); i++) {
                    Card curCard = cards.get("card" + i);
                    if (curCard.getValue() == Card.ACE && curCard.getColor() == Card.RED) {
                        colorValueMatches[arrayCounter] = new PatternMatch(curCard.getValue(), curCard.getSuit(), curCard.getColor());
                        arrayCounter++;
                    }
                }
                pmMap.put(pattern, colorValueMatches);      // Place pattern and matching array in Map object
                break;
            case "Red Jacks":
                for (int i = 0; i < cards.size(); i++) {
                    Card curCard = cards.get("card" + i);
                    if (curCard.getValue() == Card.JACK && curCard.getColor() == Card.RED) {
                        colorValueMatches[arrayCounter] = new PatternMatch(curCard.getValue(), curCard.getSuit(), curCard.getColor());
                        arrayCounter++;
                    }
                }
                pmMap.put(pattern, colorValueMatches);      // Place pattern and matching array in Map object
                break;
        }
    }

    public static void matchMultipleCardsToPattern(String pattern, Map<String, PatternMatch[][]> pmMap, Map<String, Card> cards) {
        PatternMatch[][] twoOfAKindMatches = new PatternMatch[156][]; // for Two of a Kind cases
        PatternMatch[][] threeOfAKindMatches = new PatternMatch[312][]; // for Three of a Kind cases

        int arrayCounter = 0;

        switch (pattern) {
            case "Two of a Kind":
                for (int i = 0; i < cards.size(); i++) {
                    Card curCard = cards.get("card" + i);
                    for (int j = 0; j < cards.size(); j++) {
                        Card compareCard = cards.get("card" + j);
                        // If they aren't the same card but have the same value
                        if (i != j && curCard.getValue() == compareCard.getValue()) {
                            PatternMatch[] pair = new PatternMatch[2];
                            pair[0] = new PatternMatch(curCard.getValue(), curCard.getSuit(), curCard.getColor());
                            pair[1] = new PatternMatch(compareCard.getValue(), compareCard.getSuit(), compareCard.getColor());
                            twoOfAKindMatches[arrayCounter] = pair;
                            arrayCounter++;
                        }
                    }
                }
                pmMap.put(pattern, twoOfAKindMatches);      // Place pattern and matching array in Map object
                break;
            case "Three of a Kind":
                for (int i = 0; i < cards.size(); i++) {
                    Card curCard = cards.get("card" + i);
                    // Loop to find a matching card
                    for (int j = 0; j < cards.size(); j++) {
                        Card compareCard1 = cards.get("card" + j);
                        // If they aren't the same card but have the same value
                        if (i != j && curCard.getValue() == compareCard1.getValue()) {
                            // Now loop once more to find third matching card
                            for (int k = 0; k < cards.size(); k++) {
                                Card compareCard2 = cards.get("card" + k);
                                if ( i != k && j != k && compareCard1.getValue() == compareCard2.getValue()) {
                                    PatternMatch[] trio = new PatternMatch[3];
                                    trio[0] = new PatternMatch(curCard.getValue(), curCard.getSuit(), curCard.getColor());
                                    trio[1] = new PatternMatch(compareCard1.getValue(), compareCard1.getSuit(), compareCard1.getColor());
                                    trio[2] = new PatternMatch(compareCard2.getValue(), compareCard2.getSuit(), compareCard2.getColor());
                                    threeOfAKindMatches[arrayCounter] = trio;
                                    arrayCounter++;
                                }
                            }
                        }
                    }
                }
                pmMap.put(pattern, threeOfAKindMatches);      // Place pattern and matching array in Map object
                break;
        }

    }

    public static int getGuessCounter() { return guessCounter; }
    public static void setGuessCounter(int guesses) { guessCounter = guesses; }

    public static int getDifficulty() { return difficulty; }
    public static void setDifficulty(int diff) { difficulty = diff; }

    public static boolean getIsTwoPlayer() { return isTwoPlayer; }
    public static void setIsTwoPlayer(boolean twoPlayer) { isTwoPlayer = twoPlayer; }

    public static void main(String[] args) {
        launch(args);
    }
}