package day5;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PuzzleSolver {
    private static MessageDigest md;

    public static String task1(String doorId) throws NoSuchAlgorithmException {
        int i = 0;

        String password = "";
        while (password.length() < 8) {
            byte[] digest = getDigest(doorId, i);
            if (digest[0] == 0 && digest[1] == 0 && (digest[2] & 0xF0) == 0) {
                password += nextChar(digest[2] & 0x0F);
            }
            i++;
        }

        return password;
    }

    public static String task2(String doorId) throws NoSuchAlgorithmException {
        int i = 0;

        String[] password = new String[8];
        int set = 0;
        while (set < 8) {
            byte[] digest = getDigest(doorId, i);
            if (digest[0] == 0 && digest[1] == 0 && (digest[2] & 0xF0) == 0) {
                int position = digest[2] & 0x0F;
                if (position < 8 && password[position] == null) {
                    int number = (digest[3] & 0xF0) >> 4;
                    password[position] = nextChar(number);
                    set++;
                }
            }
            i++;
        }

        return String.join("", password);
    }

    private static String nextChar(int i) {
        if (i < 10) {
            return String.valueOf(i);
        }
        switch (i) {
            case 10:
                return "a";
            case 11:
                return "b";
            case 12:
                return "c";
            case 13:
                return "d";
            case 14:
                return "e";
            case 15:
                return "f";
            default:
                throw new RuntimeException("Shit");
        }
    }

    static byte[] getDigest(String doorId, int i) throws NoSuchAlgorithmException {
        if (md == null) {
            md = MessageDigest.getInstance("MD5");
        }
        md.reset();

        md.update(doorId.getBytes(StandardCharsets.US_ASCII));
        md.update(String.valueOf(i).getBytes(StandardCharsets.US_ASCII));
        return md.digest();
    }
}
