package z.service.kit;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WebKit {

    public static File sessionDir() {
        String tempDir = System.getProperty("java.io.tmpdir");
        return Paths.get(tempDir, "session_dir").toFile();
    }

    public static List<String> route(String path) {
        return Stream.of(path.split("/")).filter(a -> a.length() > 0).collect(Collectors.toList());
    }

}
