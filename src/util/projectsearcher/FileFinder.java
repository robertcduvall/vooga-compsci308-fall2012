package util.projectsearcher;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;


/**
 * This object finds files accessible from a specified
 * local directory.
 * 
 * @author Paul Dannenberg
 * 
 */
public class FileFinder {

    private URI myRoot;
    private Collection<File> myTraversedFiles;

    /**
     * Creates a new object which will search for
     * files in the specified root directory.
     * 
     * @param rootDirectory The FileFinder will
     *        search for all files in rootDirectory and
     *        its subdirectories.
     */
    public FileFinder (String rootDirectory) {
        File file = new File(rootDirectory);
        myRoot = file.toURI();
    }

    /**
     * Creates a new object which will search for
     * files in the specified root directory.
     * 
     * @param rootDirectory The FileFinder will
     *        search for all files in rootDirectory and
     *        its subdirectories.
     */
    public FileFinder (URI rootDirectory) {
        myRoot = rootDirectory;
    }

    /**
     * Returns all files found through the search
     * in the directories and subdirectories that
     * were specified by the directory in this class's
     * constructor.
     * 
     * @return The files that were found.
     */
    public Collection<File> getFiles () {
        File rootDirectory = new File(myRoot);
        if (myTraversedFiles != null) {
            return myTraversedFiles;
        }
        else {
            Collection<File> foundFiles = new ArrayList<File>();
            return traverse(rootDirectory, foundFiles);
        }
    }

    /**
     * This method traverses a particular directory and gathers files
     * that it finds.
     * 
     * @param directory The directory in which to look for files.
     * @param foundFiles A collection in which to hold files that are
     *        found in the specified directory and also its subdirectories.
     * @return A collection of files that were found in the specified
     *         directory and its subdirectories.
     */
    private Collection<File> traverse (File directory, Collection<File> foundFiles) {
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                foundFiles.add(file);
            }
            else {
                traverse(file, foundFiles);
            }
        }
        return foundFiles;
    }

    /**
     * Finds all files whose name ends with a particular
     * sequence.
     * 
     * @param nameEndsWith The name ending that will match
     *        files in the directory and subdirectories found in
     *        the constructor.
     * @return The files found in the specified directory each
     *         of which has a name ending matching this method's parameter.
     */
    public Collection<File> getFiles (String nameEndsWith) {
        Collection<File> matchingFiles = new ArrayList<File>();
        for (File found : getFiles()) {
            if (found.getName().endsWith(nameEndsWith)) {
                matchingFiles.add(found);
            }
        }
        return matchingFiles;
    }

    /**
     * Gets all the names of the files in the directory specified in this
     * object's constructor.
     * 
     * @return The names of all the found files.
     */
    public Collection<String> getFileNames () {
        Collection<String> fileNames = new ArrayList<String>();
        for (File found : getFiles()) {
            fileNames.add(found.getName());
        }
        return fileNames;
    }

    /**
     * Gets all the names of the files in the directory specified in this
     * object's constructor, as long as their file names end with a
     * particular sequence.
     * 
     * @param The file name ending that all the returned file names will
     *        match.
     * 
     * @return The names of all the found files, each of which has a name
     *         ending with <code>nameEndsWith</code>.
     */
    public Collection<String> getFileNames (String nameEndsWith) {
        Collection<String> fileNames = new ArrayList<String>();
        for (String found : getFileNames()) {
            if (found.endsWith(nameEndsWith)) {
                fileNames.add(found);
            }
        }
        return fileNames;
    }

}
