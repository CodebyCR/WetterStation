package environment;

import java.awt.*;

public final class CRColor {

    private CRColor() {
        throw new IllegalStateException("Utility class");
    }

    public final static Color purple = new Color(80,80,210);
    public final static Color lightPurple = new Color(150,150,255);
    public final static Color darkPurple = new Color(50,50,150);

    public final static Color lightBlue = new Color(173,216,255);

    public final static Color coldBlue = new Color(0, 0, 128);

    public final static Color hotRed = new Color(128, 0, 0);

    public final static Color blue = new Color(0, 80, 255);

    public final static Color accentBlue = new Color(0, 80, 255).brighter().brighter();

    public final static Color skyBlue = new Color(0, 200, 255);

    public final static Color cyan = new Color(0, 230, 230);


    
    public static Color setAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

}
