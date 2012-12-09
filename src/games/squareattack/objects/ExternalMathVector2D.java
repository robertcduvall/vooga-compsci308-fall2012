package games.squareattack.objects;

import util.mathvector.MathVector;
import util.mathvector.MathVector2D;


public class ExternalMathVector2D extends MathVector2D {

    private double myDetoriationFactor;

    private double originalX1;
    private double originalX2;

    public ExternalMathVector2D (double x1, double x2, double detoriation) {
        super(x1, x2);
        myDetoriationFactor = detoriation;
        originalX1 = x1;
        originalX2 = x2;
    }

    public ExternalMathVector2D (MathVector2D vector, double detoriationFactor) {
        super(vector);
        myDetoriationFactor = detoriationFactor;
    }

    public void detoriate () {
        this.scale(myDetoriationFactor);
    }

    public MathVector2D getOriginalVector () {
        return new MathVector2D(originalX1, originalX2);
    }

}
