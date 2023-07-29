/*
 * Final Project
 * Maze
 * William Zhou
 * 2023-06-19
 * ICS4UI-4
 *
 * The VisualEffect class describes a one time animation that doesn't affect gameplay
 */

package model;

public class VisualEffect extends Sprite{
    public VisualEffect(double x, double y, Animation animation) {
        super(x-animation.getImage().getWidth()/2, y-animation.getImage().getHeight()/2, animation);
        animation.play();
    }

    // Update until reaching the end of the animation, then remove itself
    @Override
    public void update(){
        getAnimation().update();
        if(getAnimation().atEnd()){
            remove();
        }
    }
}
