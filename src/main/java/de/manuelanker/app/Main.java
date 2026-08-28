package de.manuelanker.app;


/*import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.beans.Transient;
import java.lang.annotation.Target;
import java.text.AttributedCharacterIterator;
import java.util.Map;
*/

import de.manuelanker.app.toolbox.Constants;

import de.manuelanker.app.window.WindowClass;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	
	private WindowClass window;
	@SuppressWarnings("exports")
	@Override
	public void start( Stage primaryStage) throws Exception {
		window = new WindowClass();
		window.create(primaryStage, Constants.Width,Constants.Height, Constants.Title, true);
	}

	
	// TODO: Load all available Projects from the file system(yml file) into project card, with he´s own thread

	
	public static void main(String[] args) {
		launch(args);
	}

}
