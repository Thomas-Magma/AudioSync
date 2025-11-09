package wtf.magma.audiosync.util.font;

public class NanoFontLoader {
    public static NanoFontRenderer Misans_Regular;
    public static void registerFonts() {
        Misans_Regular = new NanoFontRenderer("MiSans", "MiSans-Regular");
    }

}