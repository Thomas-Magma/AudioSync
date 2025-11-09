package wtf.magma.audiosync.audio;

import dsj.smtc.SmtcLoader;
import lombok.Getter;

public class SMTC {

    @Getter
    public static String info;
    @Getter
    public static String base64 = "No Media";
    @Getter
    public static String title = "No Media";
    @Getter
    public static long pos = 0;
    @Getter
    public static long dur = 0;

    public static void thread() {
        Thread smtcThread = new Thread(() -> {
            try {
                Class.forName("dsj.smtc.SmtcLoader");
                while (true) {
                    updateMediaInfo();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        smtcThread.setName("SMTC-Thread");
        smtcThread.setDaemon(true);
        smtcThread.start();
    }

    private static void updateMediaInfo() {
        try {
            info = SmtcLoader.getSmtcInfo();
            String[] parts = info.split("\\|", -1);

            if (parts.length >= 4 && !"No media".equals(parts[0])) {
                title = parts[0];
                base64 = parts[3];
                pos = Long.parseLong(parts[1]);
                dur = Long.parseLong(parts[2]);
            } else {
                title = "No Media";
                base64 = "No Media";
                pos = 0;
                dur = 0;
            }
        } catch (Exception e) {
            title = "Error";
            base64 = "Error";
            pos = 0;
            dur = 0;
        }
    }
}