import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DoWork doWork = new DoWork();
        String inText = doWork.getClass().getPackage() + "; \n\n";
        inText += "public class "+ doWork.getClass().getSimpleName() + " { \n";
        inText += "\tpublic void doWork(){";
        while (scanner.hasNextLine()) {
            String text = scanner.nextLine();
            if (text.equals("")) {
                break;
            }
            inText += "\n\t\t" + text;
        }
        inText += "\n\t}\n}";

        String filename = "С://DoWork.java";
        StringBuffer stringInFile = readFile(filename);
        writeFile(filename, inText);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null,null, filename);

        ClassLoader parentClassLoader = DoWork.class.getClassLoader();
        Loader loader = new Loader(parentClassLoader);
        Class kindMagicClass = null;
        try {
            kindMagicClass = loader.loadClass("DoWork");
            kindMagicClass.getMethod("doWork").invoke(kindMagicClass.newInstance(), null);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static void writeFile(String fileName, String text) {
        File file = new File(fileName);
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "utf-8"))) {
            writer.write(text);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static StringBuffer readFile(String fileName){
        int i;
        StringBuffer text = new StringBuffer();
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            while ((i = fileInputStream.read()) != -1) {
                text.append((char) i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}

