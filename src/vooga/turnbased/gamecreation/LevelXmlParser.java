package vooga.turnbased.gamecreation;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.graphicprocessing.ImageLoop;
import util.reflection.Reflection;
import util.xml.XmlUtilities;
import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gamecore.gamemodes.GameMode;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gameobject.battleobject.BattleObject;
import vooga.turnbased.gameobject.mapobject.MapObject;
import vooga.turnbased.gameobject.mapobject.MapPlayerObject;
import vooga.turnbased.gameobject.optionobject.OptionObject;
import vooga.turnbased.gui.GameWindow;
import vooga.turnbased.sprites.Sprite;

/**
 * This class is designed to parse Xml data and create a level of our game from
 * this information, creating character sprites and other objects that will
 * either be interacted with or act as obstacles.
 * 
 * @author Mark Hoffman, David Howdyshell
 */
public class LevelXmlParser {

	private static final String GAME_SETUP = "gameSetup";
	private static final String MODE_DEFS = "modeDeclarations";
	private static final String SPRITE = "sprite";
	private static final String NAME = "name";
	private static final String MODE = "mode";
	private static final String MODES = "modes";
	private static final String CLASS = "class";
	private static final String CONDITION = "condition";
	private static final String IMAGE = "image";
	private static final String LEFT = "left";
	private static final String RIGHT = "right";
	private static final String UP = "up";
	private static final String DOWN = "down";
	private static final String X = "x";
	private static final String Y = "y";
	private static final String LOCATION = "location";
	private static final String OBJECT = "object";
	private static final String START_MODE = "startMode";
	private static final String BACKGROUND_IMAGE = "backgroundImage";
	private static final String CREATE_ON = "createOn";
	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";
	private static final String MAP_SIZE = "MapDimension";
	private static final String CAMERA_SIZE = "CameraDimension";
	private static final String CSV_REGEX = "\\s*,\\s*";
	private static final String PLAYER = "player";

	private Document myXmlDocument;
	private Element myDocumentElement;

	private int myPlayerID;
	private String myStartMode;
	private Dimension myMapSize;
	private Dimension myCameraSize;
	private String myBackgroundImage;

	/**
	 * 
	 * @param gameFilePath
	 *            XML file path for file used to create the level
	 * @param playerFilePath
	 *            XML file path for file used to construct information about
	 *            player
	 * @param gm
	 *            The game manager of the level that is being parsed
	 */
	public LevelXmlParser(String gameFilePath, GameManager gm) {
		myXmlDocument = XmlUtilities.makeDocument(new File(gameFilePath));
		myDocumentElement = myXmlDocument.getDocumentElement();
		parseSetup();
	}

	/**
	 * Parses setup portion of the game's XML file.
	 */
	private void parseSetup() {
		Element gameSetupElement = XmlUtilities.getElement(myDocumentElement,
				GAME_SETUP);
		myStartMode = XmlUtilities
				.getChildContent(gameSetupElement, START_MODE);
		myBackgroundImage = XmlUtilities.getChildContent(gameSetupElement,
				BACKGROUND_IMAGE);
		myMapSize = parseDimension(GameWindow.importString(MAP_SIZE));
		myCameraSize = parseDimension(GameWindow.importString(CAMERA_SIZE));
	}

	/**
	 * Getter for size of map measured by grid spaces
	 * 
	 * @return - size of map
	 */
	public Dimension getMapSize() {
		return myMapSize;
	}

	/**
	 * Getter for size of map that camera displays at a time
	 * 
	 * @return - size of camera view
	 */
	public Dimension getCameraSize() {
		return myCameraSize;
	}

	/**
	 * Name of the first mode for the game to load
	 * 
	 * @return - starting mode name
	 */
	public String getStartMode() {
		return myStartMode;
	}

	/**
	 * String path of the background image
	 * 
	 * @return
	 */
	public String getBackgroundImage() {
		return myBackgroundImage;
	}

	public int getPlayerID() {
		return myPlayerID;
	}

	/**
	 * @param name
	 *            - The string name of the element containing dimensions
	 * @return The Dimension of the Level
	 */
	public Dimension parseDimension(String name) {
		List<Element> dimensionList = (List<Element>) XmlUtilities.getElements(
				myDocumentElement, name);
		Element dimension = dimensionList.get(0);
		int width = XmlUtilities.getChildContentAsInt(dimension, WIDTH);
		int height = XmlUtilities.getChildContentAsInt(dimension, HEIGHT);
		return new Dimension(width, height);
	}

	/**
	 * 
	 * @return Background Image of the Level
	 */
	public Image parseBackgroundImage() {
		return XmlUtilities.getChildContentAsImage(myDocumentElement,
				BACKGROUND_IMAGE);
	}

	/**
	 * 
	 * @return List of Sprites in the Level
	 */
	public List<Sprite> parseSprites() {
		List<Sprite> toReturn = new ArrayList<Sprite>();
		toReturn.addAll(parseStaticSprites());
		toReturn.addAll(parseSprites(SPRITE));
		toReturn.add(parseSprite(PLAYER));
		return toReturn;
	}

	/**
	 * Parses the background sprites that fill up the map
	 * 
	 * @return List of background sprites that don't do anything
	 */
	private List<Sprite> parseStaticSprites() {
		List<Element> staticSprites = (List<Element>) XmlUtilities.getElements(
				myDocumentElement, "staticSprite");
		List<Sprite> spriteList = new ArrayList<Sprite>();
		for (Element staticSprite : staticSprites) {
			Sprite s = new Sprite();
			for (int i = 0; i < myMapSize.width; i++) {
				for (int j = 0; j < myMapSize.height; j++) {
					Point point = new Point(i, j);
					String className = XmlUtilities.getChildContent(
							staticSprite, CLASS);
					String condition = XmlUtilities.getChildContent(
							staticSprite, CONDITION);
					Set<String> modes = getObjectsModes(staticSprite);
					Image image = XmlUtilities.getChildContentAsImage(
							staticSprite, IMAGE);
					GameObject mapTile = (GameObject) Reflection
							.createInstance(className, modes, condition, point,
									image);
					s.addGameObject(mapTile);
				}
			}
			spriteList.add(s);
		}
		return spriteList;
	}

	/**
	 * Parses the NPC's sprites from the game's XML
	 * 
	 * @return a list of NPC sprites to populate the game
	 */
	private List<Sprite> parseSprites(String elementName) {
		List<Element> sprites = (List<Element>) XmlUtilities.getElements(
				myDocumentElement, elementName);
		List<Sprite> spriteList = new ArrayList<Sprite>();
		for (Element sprite : sprites) {
			Sprite s = parseSprite(sprite);
			spriteList.add(s);
		}
		return spriteList;
	}

	private Sprite parseSprite(String elementName) {
		return parseSprite(XmlUtilities.getElement(myDocumentElement,
				elementName));
	}

	/**
	 * Creates a sprite object from a Sprite element in the XML file
	 * 
	 * @param spriteElement
	 *            XML element defining the sprite
	 * @return a sprite
	 */
	public Sprite parseSprite(Element spriteElement) {
		Sprite s = new Sprite();
		List<Element> playerObjects = (List<Element>) XmlUtilities.getElements(
				spriteElement, OBJECT);
		for (Element playerObject : playerObjects) {
			GameObject newObject = parseGameObject(playerObject);
			if (newObject != null) {
				s.addGameObject(newObject);
			}
		}
		myPlayerID = s.getID();
		return s;
	}

	private GameObject parseGameObject(Element objectElement) {
		String className = XmlUtilities.getChildContent(objectElement, CLASS);
		String condition = XmlUtilities.getChildContent(objectElement,
				CONDITION);
		Set<String> modes = getObjectsModes(objectElement);
		Image image = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
		if (XmlUtilities.getChildContent(objectElement, IMAGE) != null) {
			image = XmlUtilities.getChildContentAsImage(objectElement,
					IMAGE);
		}
		Element paramElement = XmlUtilities.getElement(objectElement, "param");
		List<Element> parameters = (List<Element>) XmlUtilities
				.getElements(paramElement);
		List<String> stringParams = new ArrayList<String>();
		for (Element param : parameters) {
			stringParams.add(XmlUtilities.getContent(param));
		}
		if (stringParams.isEmpty()) {
			return (GameObject) Reflection.createInstance(className, modes,
					condition, image);
		} else {
			return (GameObject) Reflection.createInstance(className, modes,
					condition, image, stringParams);
		}

	}

	/**
	 * Get all modes that this object is allowed to exist in
	 * 
	 * @param objectElement
	 *            - XML object defining a GameObject
	 * @return - the set of allowable modes
	 */
	private Set<String> getObjectsModes(Element objectElement) {
		String allModesAsList = XmlUtilities.getChildContent(objectElement,
				MODES);
		List<String> modeList = Arrays.asList(allModesAsList.split(CSV_REGEX));
		return new TreeSet<String>(modeList);
	}

	/**
	 * @return The user defined modes for the game
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Class<? extends GameMode>> getUserDefinedModes() {
		Map<String, Class<? extends GameMode>> userDefinedModes = new HashMap<String, Class<? extends GameMode>>();
		Element modeDefinitionsElement = XmlUtilities.getElement(
				myDocumentElement, MODE_DEFS);
		List<Element> allModes = (List<Element>) XmlUtilities.getElements(
				modeDefinitionsElement, MODE);
		for (Element mode : allModes) {
			try {
				String name = XmlUtilities.getChildContent(mode, NAME);
				String classString = XmlUtilities.getChildContent(mode, CLASS);
				Class<? extends GameMode> modeClass = (Class<? extends GameMode>) Class
						.forName(classString);
				userDefinedModes.put(name, modeClass);
			} catch (ClassNotFoundException e) {
				System.out
						.println("An exception. Checkstyle said I just HAD to tell you about it");
			}
		}
		return userDefinedModes;
	}

	/**
	 * @return Map of events and conditions
	 */
	public Map<String, List<List<String>>> getEventConditionMapping() {
		Map<String, List<List<String>>> conditionMap = new HashMap<String, List<List<String>>>();
		Element modeDefinitionsElement = XmlUtilities.getElement(
				myDocumentElement, MODE_DEFS);
		List<Element> allModes = (List<Element>) XmlUtilities.getElements(
				modeDefinitionsElement, MODE);
		for (Element mode : allModes) {
			String name = XmlUtilities.getChildContent(mode, NAME);
			conditionMap.put(name, new ArrayList<List<String>>());
			List<Element> conditionElements = (List<Element>) XmlUtilities
					.getElements(mode, CONDITION);
			for (Element conditionElement : conditionElements) {
				String[] conditions = XmlUtilities.getContent(conditionElement)
						.split(CSV_REGEX);
				conditionMap.get(name).add(Arrays.asList(conditions));
			}
		}
		return conditionMap;
	}
}
