package de.manuelanker.app.toolbox;

import javafx.scene.paint.Color;

public class Constants {
	
	// Constants for the window
	public static final int Width  = 1280; // 80 * 16
	public static final int Height =  720; // 80 *  9
	public static final String Title = "Unknown Portfolio App";
	
	//Colors of the app theme
	public static final Color bgColor       = Color.rgb(177, 247, 235);
	public static final Color bgProjectCard = Color.DARKGRAY;

	// Constants of the ProjectCard
	public static final int BorderThickness = 14;
	public static final int fontSize  = 16;
	public static final int PANEL_Y  = 35;
	public static final int PANEL_X  = 380;
	public static final int PANEL_WIDTH  = 530;
	public static final int PANEL_HEIGHT  = 640;
	
	// FilePath to the projects.xml that includes all data of the project.
	public static final String FilePath = "/assets/data/projects.xml";
	public static final String FXMLPath = "/assets/panel/";

	public static final String CardDataDir = "yml";
	public static final String ImageDataDir = "images";
	
	// Constants of each ProjectCard
	public static final int pWidth = (Width / 2);
	public static final int pHeight = 500;
	
	// Constants of each Region in the H-Box "tickBar"
	public static final int tickRegionHeight = 2;
	public static final int tickRegionWidth = 2;
	public static final int tickRegionMaxWidth = 2;
	
	public static final int maxCount = 6;
	public static int currentCount   = 1;
	public static int index = 0;
	
}
