package util.projectsearcher;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;


/*
 * An example of the use of this class would be to populate the
 * sprite editor with classes. Suppose the user of our engine
 * decides to create a new gameObject. Presumably he or she
 * would extend gameObject. Suddenly, the sprite editor will
 * not be configured to edit all the sprites, since it will not
 * know about this newly created class. Rather that asking the
 * user to add the name of the new class to a resource file, or
 * asking the user to add the actual object to a collection of
 * objects in the sprite editor, the user would not actually
 * need to do anything, since this object would automatically
 * be able to detect that a new class had extended GameObject.
 * However, a secondary method for the sprite editor to be able
 * to sense that a new GameObject has been added should also be
 * available (e.g. asking the user to select the new class himself
 * from the file system), since this class can make no guarantees
 * as to the accessibility of the newly created class. This
 * class is only capable of finding subclasses in accessible
 * local directories.
 */

/**
 * This class searches the local directory AND its subdirectories,
 * looking for subclasses of a particular class.
 * 
 * @author Paul Dannenberg
 * 
 */
public class SubClassFinder {

    private static final String DEFAULT_SEARCH_DIRECTORY = "user.dir";
    private static final String FILE_EXTENSION_TO_MATCH = ".java";
    private static final String CANONICAL_NAME_DELIMETER = ".";
    private FileFinder myFileFinder;
    private String myRootName;

    /**
     * Constructs a new object that will look for subclasses in the
     * local project directory (and its subdirectories).
     */
    public SubClassFinder () {
        String relativePath = System.getProperty(DEFAULT_SEARCH_DIRECTORY);
        myFileFinder = new FileFinder(relativePath);
        myRootName = getRootName(relativePath, File.separator);
    }

    /**
     * Constructs a new object that will look for subclasses in the
     * specified directory (and its subdirectories).
     * 
     * @param rootDirectory The directory to begin searching. All of
     *        rootDirectory's subdirectories will also be searched.
     */
    public SubClassFinder (String rootDirectory) {
        myFileFinder = new FileFinder(rootDirectory);
        myRootName = getRootName(rootDirectory, File.separator);
    }

    /**
     * Obtains the name of the root directory from a directory name
     * that also contains its parent directories.
     * 
     * @param fullRootDirectory The full directory name, which contains
     *        the names of higher level directories from ROOT.
     * @param separator The character being used to divide different
     *        directory levels.
     * @return The name of the directory, with all reference to higher
     *         level directories, normally specified in a directory path,
     *         removed.
     */
    private String getRootName (String fullRootDirectory, String separator) {
        int lastSeparatorIndex = fullRootDirectory.lastIndexOf(separator);
        return fullRootDirectory.substring(lastSeparatorIndex + 1);
    }

    /**
     * Tests to see if a file is a java class and inherits from a superclass.
     * If it is, this file is stored.
     * 
     * @param file The directory in which to look for potential classes.
     * @param nameLength The number of parts from which the name is comprised.
     *        For example, vooga.turnbased.gameobject.BattleObject is comprised
     *        of 4 parts.
     * @param subclasses A collection in which found subclasses, each of which
     *        inherit from <code> superclass </code> will be stored.
     * @param superclass The superclass whose subclasses should be found.
     * @return The parsed name of the file that was just tested, to see if it
     *         was a subclass of <code>superclass</code>.
     */
    private String storeSubClasses (File file, int nameLength, Collection<Class<?>> subclasses,
                                    Class<?> superclass) {
        String potentialClassName = findPotentialClassName(file, nameLength);
        Class<?> possibleSubClass = generateClass(potentialClassName);
        if (possibleSubClass != null && isSubClass(possibleSubClass, superclass)) {
            subclasses.add(possibleSubClass);
        }
        return potentialClassName;
    }

    /**
     * Finds all subclasses in the directory and subdirectories specified
     * in this object's constuctor. These directories must be available
     * locally and must be traversable by this class.
     * 
     * @param superclass The class whose subclasses should be looked for.
     * @return A collection of classes each of which extends or implements
     *         <code>superclass</code>.
     */
    public Collection<Class<?>> getSubClasses (Class<?> superclass) {
        Collection<Class<?>> subclasses = new ArrayList<Class<?>>();
        Collection<File> availableFiles = myFileFinder.getFiles(FILE_EXTENSION_TO_MATCH);
        for (File file : availableFiles) {
            int nameLength = 1;
            String currentDirectoryName = "";
            while (!currentDirectoryName.contains(myRootName)) {
                currentDirectoryName = storeSubClasses(file, nameLength, subclasses, superclass);
                nameLength++;
            }
        }
        return subclasses;
    }

    /**
     * Tests to see whether one class is a subclass of another.
     * 
     * @param potentialSubClass The subclass which is thought
     *        to inherit from <code>superClass</code>.
     * @param superClass The class who is a suspected superclass
     *        or superinterface of <code>potentialSubClass</code>.
     * @return true if <code>potentialSubClass</code> is indeed
     *         a subclass of <code>superClass</code>. False otherwise.
     *         If potentialSubClass and superClass refer to the same
     *         class, this method also returns false.
     */
    private boolean isSubClass (Class<?> potentialSubClass, Class<?> superClass) {
        return superClass.isAssignableFrom(potentialSubClass) &&
               !superClass.equals(potentialSubClass);
    }

    /**
     * Attempts to create a Class from a directory name. Since many
     * directories found will not actually refer to java class
     * names, this method will generate a <code>ClassNotFoundException</code>
     * most of the time. This is expected.
     * 
     * @param potentialName The name from which to attempt
     *        to create the Class.
     * @return A Class if <code>potentialName</code> referred to an
     *         actual java class. Null, otherwise.
     */
    private Class<?> generateClass (String potentialName) {
        try {
            return Class.forName(potentialName);
        }
        catch (ClassNotFoundException e) {
            // Do Nothing.
        }
        return null;
    }

    /**
     * Given an unparsed directory name, this method parses it into the form
     * of the canonical name of a potential java class.
     * 
     * @param unParsedClassName A directory name.
     * @return A name in the format of a java class.
     */
    private String parse (String unParsedClassName) {
        int extensionIndex = unParsedClassName.lastIndexOf(FILE_EXTENSION_TO_MATCH);
        String parsedClassName = unParsedClassName.substring(1, extensionIndex);
        return parsedClassName;
    }

    /**
     * Traverses a single path up a directory, steadily attempting to generate
     * a name that could potentially be a java class.
     * 
     * @param leaf The directory at which to start.
     * @param nameLength The number of parts from which the name is comprised.
     *        For example, vooga.turnbased.gameobject.BattleObject is comprised
     *        of 4 parts.
     * @return The name of a potential java class.
     */
    private String findPotentialClassName (File leaf, int nameLength) {
        String potentialClassName = "";
        File current = leaf;
        while (nameLength > 0) {
            potentialClassName = CANONICAL_NAME_DELIMETER + current.getName() + potentialClassName;
            current = current.getParentFile();
            nameLength--;
        }
        return parse(potentialClassName);
    }
}
