package model;

public class VisualEffect extends Sprite{
    public VisualEffect(double x, double y, Animation animation) {
        super(x-animation.getImage().getWidth()/2, y-animation.getImage().getHeight()/2);
        setAnimation(animation);
        animation.play();
    }

    public void update(){
        getAnimation().update();
    }

    public boolean finished(){
        return getAnimation().atEnd();
    }
}
