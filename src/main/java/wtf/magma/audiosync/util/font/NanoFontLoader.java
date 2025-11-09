package wtf.magma.audiosync.util.font;

/**
 * @author ChengFeng
 * @since 2024/8/3
 **/
public class NanoFontLoader {
    public static NanoFontRenderer SFPRO_BOLD;
    public static NanoFontRenderer noto;
    public static NanoFontRenderer cguiicon;

    public static NanoFontRenderer Misans_Regular;
    public static NanoFontRenderer Misans_Bold;
    public static NanoFontRenderer Misans_Demibold;
    public static NanoFontRenderer Misans_ExtraLight;
    public static NanoFontRenderer Misans_Heavy;
    public static NanoFontRenderer Misans_Medium;
    public static NanoFontRenderer Misans_Normal;
    public static NanoFontRenderer Misans_Semibold;
    public static NanoFontRenderer Misans_Thin;

    public static NanoFontRenderer quicksand;
    public static void registerFonts() {
        SFPRO_BOLD = new NanoFontRenderer("SFPRO", "SF-UI-Pro");
        noto = new NanoFontRenderer("Noto", "noto");
        quicksand = new NanoFontRenderer("Quicksand", "quicksand");
        cguiicon = new NanoFontRenderer("Icon","ClickGuiIcon");
        Misans_Normal = new NanoFontRenderer("MiSans", "MiSans-Normal");
        Misans_Demibold = new NanoFontRenderer("MiSans", "MiSans-Demibold");
        Misans_Thin = new NanoFontRenderer("MiSans", "MiSans-Thin");
        Misans_Semibold = new NanoFontRenderer("MiSans", "MiSans-Semibold");
        Misans_Bold = new NanoFontRenderer("MiSans", "MiSans-Bold");
        Misans_Heavy = new NanoFontRenderer("MiSans", "MiSans-Heavy");
        Misans_Medium = new NanoFontRenderer("MiSans", "MiSans-Medium");
        Misans_ExtraLight = new NanoFontRenderer("MiSans", "MiSans-ExtraLight");
        Misans_Regular = new NanoFontRenderer("MiSans", "MiSans-Regular");
    }

}