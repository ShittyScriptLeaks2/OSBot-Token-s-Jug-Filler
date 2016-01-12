import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public final class Constants {

    public static final Area BANK_AREA = new Area(3250, 3423, 3257, 3419);
    public static final Area FOUNTAIN_AREA = new Area(3240, 3433, 3237, 3436);
    public static final Position[] POTENTIAL_BANK_DESTINATIONS = new Position[]{
            new Position(3253, 3420, 0),
            new Position(3252, 3420, 0),
            new Position(3254, 3420, 0),
            new Position(3251, 3420, 0),
            new Position(3253, 3421, 0),
            new Position(3252, 3421, 0),
            new Position(3253, 3422, 0)
    };
    public static final Position[] POTENTIAL_FOUNTAIN_DESTINATIONS = new Position[]{
            new Position(3240, 3433, 0),
            new Position(3240, 3434, 0),
            new Position(3240, 3435, 0),
            new Position(3239, 3433, 0),
            new Position(3238, 3433, 0),
            new Position(3237, 3433, 0),
            new Position(3237, 3434, 0)
    };
    public static final Position[] POTENTIAL_DEBUG_DESTINATIONS = new Position[]{
            new Position(3245, 3430, 0),
            new Position(3243, 3436, 0),
            new Position(3239, 3430, 0),
            new Position(3236, 3433, 0)
    };

    private Constants() {
    }

    public static BufferedImage imageFromURL(String url) throws IOException {
        return ImageIO.read(new URL(url));
    }

}
