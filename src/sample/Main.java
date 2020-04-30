package sample;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
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

        java.util.Collections.shuffle(cards);

        Map<String, ImageView> imageViews = new HashMap<String, ImageView>();

        setupCardImageViews(cards, imageViews);

//        ImageView view1 = new ImageView(
//                new Image("https://liveexample.pearsoncmg.com/book/image/card/" + cards.get(0) + ".png"));
//        ImageView view2 = new ImageView(
//                new Image("https://liveexample.pearsoncmg.com/book/image/card/" + cards.get(1) + ".png"));
//        ImageView view3 = new ImageView(
//                new Image("https://liveexample.pearsoncmg.com/book/image/card/" + cards.get(2) + ".png"));

        HBox root = new HBox();

        root.setSpacing(10);

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

        // Add all cards in gridPane
        int j = 0;
        int i = 0;
        for(int x = 0; x < 52; x++) {
            String key = "view" + x;

            gridPane.add(imageViews.get(key), i, j);

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
            System.out.println("Quit was clicked!");
        });
        Button guessBtn = new Button("Make a Guess");
        guessBtn.setOnAction(e -> {
            System.out.println("Guess was clicked!");
        });
        Button randomDealBtn = new Button("Random Deal");
        randomDealBtn.setOnAction(e -> {
            System.out.println("Random Deal was clicked!");
        });

        gridPane.add(randomDealBtn, 9, 0);
        gridPane.add(guessBtn, 9, 1);
        gridPane.add(quitBtn, 9, 2);

        gridPane.setStyle("-fx-background-color: GREEN;");

        //setupHBoxChildren(root, imageViews);

//        root.getChildren().add(view1);
//        root.getChildren().add(view2);
//        root.getChildren().add(view3);

        Scene scene = new Scene(gridPane);

        primaryStage.setTitle("Exercise14_03");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setupCardImageViews(ArrayList<String> cards, Map<String, ImageView> imageViews) {
        for (int i = 0; i < 52; i++) {
            imageViews.put("view" + i, new ImageView( new Image("https://liveexample.pearsoncmg.com/book/image/card/"
                    + cards.get(i) + ".png")));
        }
    }

//    public void setupHBoxChildren(HBox root, Map<String, ImageView> imageViews) {
//        for (Map.Entry<String, ImageView> entry : imageViews.entrySet()) {
//            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//            root.getChildren().add(entry.getValue());
//        }
//    }

    public static void main(String[] args) {
        launch(args);
    }
}