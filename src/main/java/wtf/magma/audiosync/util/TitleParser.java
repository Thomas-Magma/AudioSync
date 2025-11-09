package wtf.magma.audiosync.util;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * 音乐标题解析工具
 */
public class TitleParser {

    private static final Pattern[] SEPARATOR_PATTERNS = {
            Pattern.compile("\\s*-\\s*"),
            Pattern.compile("\\s*–\\s*"),
            Pattern.compile("\\s*—\\s*"),
            Pattern.compile("\\s*•\\s*"),
            Pattern.compile("\\s*\\|\\s*"),
            Pattern.compile("\\s*::\\s*"),
            Pattern.compile("\\s*by\\s*", Pattern.CASE_INSENSITIVE) // " by " 格式
    };
    
    /**
     * 解析标题，提取歌曲名和艺术家
     * @param title 原始标题，格式如 "歌曲名 - 艺术家"
     * @return 包含歌曲名和艺术家的数组 [song, artist]
     */
    public static String[] parseTitle(String title) {
        if (title == null || title.isEmpty() || "No Media".equals(title) || "Error".equals(title)) {
            return new String[]{"", ""};
        }

        for (Pattern pattern : SEPARATOR_PATTERNS) {
            String[] parts = pattern.split(title, 2);
            if (parts.length == 2) {
                String song = parts[0].trim();
                String artist = parts[1].trim();
                return new String[]{song, artist};
            }
        }

        return fallbackParse(title);
    }
    
    /**
     * 备用解析方法
     */
    private static String[] fallbackParse(String title) {
        if (title.contains("(") && title.contains(")")) {
            Pattern pattern = Pattern.compile("(.+?)\\s*\\(([^)]+)\\)");
            Matcher matcher = pattern.matcher(title);
            if (matcher.matches()) {
                return new String[]{matcher.group(1).trim(), matcher.group(2).trim()};
            }
        }

        if (title.contains("\"")) {
            Pattern pattern = Pattern.compile("\"([^\"]+)\"\\s*(.+)");
            Matcher matcher = pattern.matcher(title);
            if (matcher.matches()) {
                return new String[]{matcher.group(1).trim(), matcher.group(2).trim()};
            }
        }

        return new String[]{title.trim(), ""};
    }
    
    /**
     * 验证歌曲名和艺术家是否合理
     */
    private static boolean isValidSongArtist(String song, String artist) {
        if (song.isEmpty() || artist.isEmpty()) {
            return false;
        }

        if (artist.length() > song.length() * 3) {
            return false;
        }

        if (song.contains("http://") || song.contains("https://") ||
            artist.contains("http://") || artist.contains("https://")) {
            return false;
        }
        
        return true;
    }
    
    /**
     * 只获取歌曲名
     */
    public static String getSongName(String title) {
        return parseTitle(title)[0];
    }
    
    /**
     * 只获取艺术家
     */
    public static String getArtist(String title) {
        return parseTitle(title)[1];
    }
    
    /**
     * 智能格式化显示
     */
    public static String formatDisplay(String title, boolean showArtist) {
        String[] parts = parseTitle(title);
        String song = parts[0];
        String artist = parts[1];
        
        if (artist.isEmpty() || !showArtist) {
            return song;
        } else {
            return song + " - " + artist;
        }
    }
    
    /**
     * 检查标题是否包含艺术家信息
     */
    public static boolean hasArtistInfo(String title) {
        String artist = getArtist(title);
        return artist != null && !artist.isEmpty();
    }
}