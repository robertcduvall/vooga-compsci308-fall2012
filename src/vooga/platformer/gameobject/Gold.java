package vooga.platformer.gameobject;

import java.io.File;
import java.io.IOException;


public class Gold extends StaticObject {
    public Gold (double inX, double inY, double inWidth, double inHeight,
            int inId, File defaultImageFile) throws IOException {
        super(inX, inY, inWidth, inHeight, inId, defaultImageFile);
    }

    public Gold getInstance (double inX, double inY, double inWidth,
            double inHeight, int inId, File defaultImageFile) throws IOException {
        return new Gold(inX, inY, inWidth, inHeight, inId, defaultImageFile);
    }
//    @Override
//    protected String setTypeName () {
//        return "Gold";
//    }
}
