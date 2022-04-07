package z.web.kit;

/**
 * 内部专用工具
 */
public class ClazzKit {

    private ClazzKit() {
    }

    /**
     * 根据字符串加载为一个类
     *
     * @param className 类名称
     */
    public static Class<?> loadClass(String className) {
        try {
            return loadClass(null, className); //Class.forName(className);
        } catch (Throwable ex) {
            return null;
        }
    }

    /**
     * 根据字符串加载为一个类
     *
     * @param classLoader 类加载器
     * @param className   类名称
     */
    public static Class<?> loadClass(ClassLoader classLoader, String className) {
        try {
            if (classLoader == null) {
                return Class.forName(className);
            } else {
                return classLoader.loadClass(className);
            }
        } catch (Throwable ex) {
            return null;
        }
    }

    /**
     * 获取当前线程的ClassLoader
     */
    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 获取ClassLoader
     */
    public static ClassLoader getClassLoader() {
        ClassLoader classLoader = getContextClassLoader();
        if (classLoader == null) {
            classLoader = ClazzKit.class.getClassLoader();
            if (null == classLoader) {
                classLoader = ClassLoader.getSystemClassLoader();
            }
        }
        return classLoader;
    }

}
