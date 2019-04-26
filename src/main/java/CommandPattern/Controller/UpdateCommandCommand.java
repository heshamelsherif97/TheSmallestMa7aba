package CommandPattern.Controller;

import CommandPattern.Command;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import sun.misc.Cache;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.File;
import java.util.Arrays;

public class UpdateCommandCommand implements Command {

    public JSONObject execute(JSONObject json) {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String s) throws ClassNotFoundException {
                return super.loadClass(s);
            }
        };
        try{
            File file = (File) json.get("file");
//            MapHandler.removeProperty(json.getString("command_name"));
           // FileUtils.forceDelete(file);
            file.createNewFile();
            Class loadedClass = classLoader.loadClass("CommandPattern.userStories." + FilenameUtils.removeExtension(file.getName()));
            MapHandler.addProperty(json.getString("command_name"),FilenameUtils.removeExtension(file.getName()));
            PropertiesHandler.addProperty("restart", "true");

//            (new DeleteCommandCommand()).execute(json);
//            (new AddCommandCommand()).execute(json);
//            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//            StandardJavaFileManager fileManager = compiler.getStandardFileManager(
//                    null, null, null);
//
//            fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays
//                    .asList(new File("generated/")));
//            // Compile the file
//            boolean success = compiler.getTask(null, fileManager, null, null, null,
//                    fileManager.getJavaFileObjectsFromFiles(Arrays.asList(file)))
//                    .call();
//            fileManager.close();

        } catch (Exception e){
            e.printStackTrace();
            return new JSONObject("{ \"message\" : \"Error in updating class\" }");
        }

        return new JSONObject();
    }
}
