import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public final class Utils {

    public static int getBuyingPrice(int a) throws IOException {
        return getGEPrice(a, "buying");
    }

    public static int getSellingPrice(int a) throws IOException {
        return getGEPrice(a, "selling");
    }

    private static int getGEPrice(int itemId, String type) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new URL("https://api.rsbuddy.com/grandExchange?a=guidePrice&i=" + itemId).openStream()));
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.contains("{")) {
                continue;
            }

            line = line.trim();
            break;
        }

        String priceString;
        switch (type) {
            case "buying":
                int idx = line.indexOf(",") + 10;
                priceString = line.substring(idx, lastIndexOf(line, ',', 1)).trim();
                break;
            case "selling":
                priceString = line.substring(lastIndexOf(line, ',', 2) + 11, line.indexOf("sellingQuantity") - 2).trim();
                break;
            default:
                priceString = line.substring(line.indexOf(":") + 1, line.indexOf(",")).trim();
                break;
        }

        br.close();
        return Integer.parseInt(priceString);
    }

    private static int lastIndexOf(String str, char character, int count) {
        int indexOfChar = str.indexOf(character, 0);
        if (count <= 0) {
            return indexOfChar;
        }

        while (true) {
            if (--count <= 0 || indexOfChar == -1) {
                return indexOfChar;
            }

            indexOfChar = str.indexOf(character, indexOfChar + 1);
        }
    }

    public static int getOverallPrice(final int a) throws IOException {
        return getGEPrice(a, "overall");
    }

}
