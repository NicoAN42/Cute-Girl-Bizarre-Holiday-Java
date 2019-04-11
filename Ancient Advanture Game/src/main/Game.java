package main;

import java.awt.Graphics2D;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import Audio.AudioPlayer;

import GameState.GameStateManager;
import Obj.PlayerSave;
import main.GamePanel;


import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.VBox;

public class Game extends Application{
	private Graphics2D g;
	private GameState.GameStateManager gsm;
	private GameM gameMenu;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
//		AudioPlayer.load("/SFX/menuoption.mp3", "menuoption");
//		AudioPlayer.load("/SFX/menuselect.mp3", "menuselect");
		gsm = new GameStateManager();
		// TODO Auto-generated method stub
		Pane root = new Pane();
		root.setPrefSize(800, 600);
		
		InputStream is = Files.newInputStream(Paths.get("res/mantap.jpg"));
		Image img = new Image(is);
		is.close();
		
		ImageView imgView = new ImageView(img);
		imgView.setFitWidth(800);
		imgView.setFitHeight(600);
		
		gameMenu = new GameM();
		
		root.getChildren().addAll(imgView, gameMenu);
		
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private class GameM extends Parent{
		public GameM() {
			VBox menu0 = new VBox(15);
			VBox menu1 = new VBox(15); //20 ini jarak tulisan atas dengan tulisan bawah
			
			menu0.setTranslateX(100);
			menu0.setTranslateY(200);
			menu1.setTranslateX(100);
			menu1.setTranslateY(200);
			
			final int offset = 400;
			
			MenuButton btnResume = new MenuButton("PLAY");
			btnResume.setOnMouseClicked(event->{
				FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
				ft.setFromValue(1);
				ft.setToValue(0);
				ft.setOnFinished(evt->this.setVisible(false));
				ft.play();
				AudioPlayer.play("menuselect");
				PlayerSave.init();
				gsm.setState(GameStateManager.STAGE1);
			});
			
			MenuButton btnOptions = new MenuButton("OPTIONS");
			btnOptions.setOnMouseClicked(event->{
				getChildren().add(menu1);
				
				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);
				tt.setToX(menu0.getTranslateX() - offset);
				
				TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.25), menu1);
				tt1.setToX(menu0.getTranslateX() - offset);
				
				tt.play();
				tt1.play();
				
				tt.setOnFinished(evt->{
					getChildren().remove(menu0);
				});
			});
			
			MenuButton btnHelp = new MenuButton("HELP");
			btnHelp.setOnMouseClicked(event->{
				getChildren().add(menu1);
				
				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);
				tt.setToX(menu0.getTranslateX() - offset);
				
				TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.25), menu0);
				tt1.setToX(menu0.getTranslateX() - offset);
				
				tt.play();
				tt1.play();
				AudioPlayer.play("menuselect");
				tt.setOnFinished(evt->{
					getChildren().remove(menu0);
				});
			});
			
			MenuButton btnExit = new MenuButton("EXIT");
			btnExit.setOnMouseClicked(event->{
				System.exit(0);
			});
			
			menu0.getChildren().addAll(btnResume, btnOptions, btnExit);
			
			Rectangle bg = new Rectangle(800, 600);
			bg.setFill(Color.GREY);
			bg.setOpacity(0.4);
			
			getChildren().addAll(bg, menu0);
			
		}
	}
	
	private static class MenuButton extends StackPane{
		private Text text;
		
		public MenuButton(String name) {
			text = new Text(name);
			text.setFont(text.getFont().font(20));
			text.setFill(Color.WHITE);
			
			Rectangle bg = new Rectangle(250, 30);
			bg.setOpacity(0.6);
			bg.setFill(Color.BLACK);
			bg.setEffect(new GaussianBlur(3.5));
			
			this.setAlignment(Pos.CENTER_LEFT);
			setRotate(-0.5);
			getChildren().addAll(bg, text);
			
			setOnMouseEntered(event->{
				bg.setTranslateX(10);
				text.setTranslateX(10);
				bg.setFill(Color.WHITE);
				text.setFill(Color.BLACK);
			});
			
			setOnMouseExited(event->{
				bg.setTranslateX(0);
				text.setTranslateX(0);
				bg.setFill(Color.BLACK);
				text.setFill(Color.WHITE);
			});
			
			DropShadow drop = new DropShadow(50, Color.WHITE);
			drop.setInput(new Glow());
			
			setOnMousePressed(event->setEffect(drop));
			setOnMouseReleased(event->setEffect(null));
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	private void draw() {
		gsm.draw(g);
	}



}