

import com.model.*;
import java.net.URL;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class Main extends Application {

    private final String HOME_PAGE = "resources/html/window.html";

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage stage) {
        String homePageUrl = getHomePageUrl();
        WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();
        webView.setMaxHeight(500);
        webEngine.load(homePageUrl);
        Scene scene = new Scene(webView);
        stage.setScene(scene);
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
            @Override
            public void changed(ObservableValue ov, State oldState, State newState) {
                if (newState == State.SUCCEEDED) {
                    stage.setTitle("Algoritmo de Wilson");
                    JSObject jsWindow = (JSObject) webEngine.executeScript("window");
                    jsWindow.setMember("fx", new JavaToJavaScript(stage));
                    stage.show();
                }
            }
        });
        
    }
    
    public String getHomePageUrl() {
        String pageUrl = null;
        URL url = this.getClass().getClassLoader().getResource(HOME_PAGE);
        if (url == null)
            System.out.println("Could not find " + HOME_PAGE + " in CLASSPATH.");
        else
            pageUrl = url.toExternalForm();
        return pageUrl;
    }

}
