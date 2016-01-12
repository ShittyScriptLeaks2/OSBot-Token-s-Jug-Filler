import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

// I'm.. not going to refactor this..
public final class Utils {

    public static int getBuyingPrice(final int a) throws IOException {
        return getGEPrice(a, "buying");
    }

    public static int getSellingPrice(final int a) throws IOException {
        return getGEPrice(a, "selling");
    }

    private static int getGEPrice(final int itemId, final String type) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(new StringBuilder().insert(0, "https://api.rsbuddy.com/grandExchange?a=guidePrice&i=").append(itemId).toString()).openStream()));
        String trim = null;
        Label_0050:
        while (true) {
            BufferedReader bufferedReader2 = bufferedReader;
            String line;
            while ((line = bufferedReader2.readLine()) != null) {
                if (!line.contains("{")) {
                    continue Label_0050;
                }
                trim = line.trim();
                bufferedReader2 = bufferedReader;
            }
            break;
        }

        String s;
        BufferedReader bufferedReader3;
        if (type.equals("buying")) {
            final int n = trim.indexOf(",") + 10;
            final String a2 = trim;
            s = a2.substring(n, m(a2, ',', 1)).trim();
            bufferedReader3 = bufferedReader;
        } else {
            final boolean equals = type.equals("selling");
            final String s2 = trim;
            if (equals) {
                s = s2.substring(m(trim, ',', 2) + 11, trim.indexOf("sellingQuantity") - 2).trim();
                bufferedReader3 = bufferedReader;
            } else {
                s = s2.substring(trim.indexOf(":") + 1, trim.indexOf(",")).trim();
                bufferedReader3 = bufferedReader;
            }
        }
        bufferedReader3.close();
        return Integer.parseInt(s);
    }

    private static int m(String a, char a1, int a2) {
        int var3 = a.indexOf(a1, 0);
        int var10000 = a2;

        while (true) {
            --a2;
            if (var10000 <= 0 || var3 == -1) {
                return var3;
            }

            var3 = a.indexOf(a1, var3 + 1);
            var10000 = a2;
        }
    }


    public static int getOverallPrice(final int a) throws IOException {
        return getGEPrice(a, "overall");
    }
}
