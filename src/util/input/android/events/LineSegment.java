package util.input.android.events;



public class LineSegment extends AndroidControllerEvent {

    private int myStartX;
    private int myStartY;
    private int myEndX;
    private int myEndY;
    private int myParentWidth;
    private int myParentHeight;

    public LineSegment(int startX, int startY, int endX, int endY, int parentWidth, int parentHeight){
        myStartX = startX;
        myStartY = startY;
        myEndX = endX;
        myEndY = endY;
        myParentWidth = parentWidth;
        myParentHeight = parentHeight;
    }
     
    public double getRelativeStartX(){
        return myStartX/(double)myParentWidth;
    }
    public double getRelativeStartY(){
        return myStartY/(double)myParentHeight;
    }
    public double getRelativeEndX(){
        return myEndX/(double)myParentWidth;
    }
    public double getRelativeEndY(){
        return myEndY/(double)myParentHeight;
    }
    
    public int getStartX(){
        return myStartX;
    }
    public int getStartY(){
        return myStartY;
    }
    public int getEndX(){
        return myEndX;
    }
    public int getEndY(){
        return myEndY;
    }

}
