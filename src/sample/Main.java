package sample;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.awt.event.InputEvent;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent rootP = FXMLLoader.load(getClass().getResource("sample.fxml"));

        ArrayList<String> cards = new ArrayList<>();

        for (int i = 0; i < 52; i++) {
            cards.add(String.valueOf(i + 1));
        }

        // Shuffle cards; Not sure if we want or need this
        //java.util.Collections.shuffle(cards);



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

        // Current Card section
        HBox hbox = new HBox();
        hbox.setMinHeight(150);
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");

        gridPane.add(hbox, 0, 7, 8,2);

        // Data structure to hold images of all cards
        Map<String, ImageView> imageViews = new HashMap<String, ImageView>();
        // Fill data structure
        setupCardImageViews(cards, imageViews, hbox);

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

        //Label for difficulty
        Text difficultyLabel = new Text("Difficulty");

        //Choice box for difficulty
        ChoiceBox difficultychoiceBox = new ChoiceBox();
        difficultychoiceBox.getItems().addAll
                ("Easy (K-2)", "Medium (3-5)", "Hard (6-8)");

        // Add to gridPane
        gridPane.add(difficultyLabel, 8, 6);
        gridPane.add(difficultychoiceBox, 9, 6);

        // Buttons
        Button quitBtn = new Button("Quit Game");
        quitBtn.setOnAction(e -> {
            // ToDo: Need to add real action
                // 1. Display alert to ensure they meant to quit
                    // a. Yes -> end game
                    // b. No -> remove alert and proceed as normal

            // Test output
            System.out.println("Quit was clicked!");
        });

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

        Button randomDealBtn = new Button("Random Deal");
        randomDealBtn.setOnAction(e -> {
            // ToDo: Need to add real action
                // 1. Select 4 cards randomly
                // 2. Show 4 cards in 'deal spot'

            // Test output
            System.out.println("Random Deal was clicked!");
        });

        gridPane.add(randomDealBtn, 9, 0);
        gridPane.add(guessBtn, 9, 1);
        gridPane.add(quitBtn, 9, 2);

        gridPane.setStyle("-fx-background-color: GREEN;");

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

    public static void main(String[] args) {
        launch(args);
    }
}