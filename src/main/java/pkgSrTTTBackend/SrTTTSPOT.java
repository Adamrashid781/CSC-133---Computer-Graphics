package pkgSrTTTBackend;
import static java.lang.System.out;

public class SrTTTSPOT {
    public static final int NUM_ROWS = 3, NUM_COLS = 3;
    public static final char DEFAULT_CHAR = '-';
    public static final char MACHINE_CHAR = 'M';
    public static final char PLAYER_CHAR = 'P';
    public static final char INVALID_CHAR = '*';
    // integers [0, 2] reserved as valid row/col numbers
    public static final int NOT_STARTED = -1,
                            INVALID_INPUT = 0,
                            GAME_DRAW = 5,
                            GAME_PLAYER = 6,
                            GAME_MACHINE = 7,
                            GAME_INCOMPLETE = 8,
                            GAME_QUIT = 9;
} //  public class SrTTTSPOT


