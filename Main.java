package io.github.cshu.minjavawebbrowser;

import java.util.*;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.web.*;
import javafx.stage.*;

public class Main extends Application {
	private Scene scene;
	@Override
	public void start(Stage stage) throws Exception {
		stage.setMaximized(true);
		final WebView browser = new WebView();
		final WebEngine webEngine = browser.getEngine();
		List<String> up = this.getParameters().getUnnamed();
		Map<String,String> np = this.getParameters().getNamed();
		boolean jso="true".equals(np.get("js"));
		String loc;
		if(up.size()==0){
			javafx.scene.control.TextInputDialog tid = new javafx.scene.control.TextInputDialog();
			tid.getDialogPane().setPrefWidth(550D);
			tid.setTitle("File/URL is not specified");
			tid.setHeaderText("File/URL can be set in command line argument");
			Optional<String> r=tid.showAndWait();
			if(!r.isPresent()){javafx.application.Platform.exit();return;}//return is necessary, exit is not immediate exit
			loc=r.get();
			if(!np.containsKey("js"))
				jso=new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION,"Disable JavaScript? (Disabled by default, can be set in --js=false/true)").showAndWait().get()!=javafx.scene.control.ButtonType.OK;
		}else{
			loc=up.get(0);
		}
		if(!loc.startsWith("file:")&&!loc.startsWith("http:")&&!loc.startsWith("https:"))
			loc=java.nio.file.Paths.get(loc).toUri().toString();
		if(jso){
			stage.setTitle(loc);
		}else{
			webEngine.setJavaScriptEnabled(false);
			stage.setTitle(loc+" (JS is disabled)");
		}
		webEngine.load(loc);
		scene = new Scene(browser);
		stage.setScene(scene);
		stage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
