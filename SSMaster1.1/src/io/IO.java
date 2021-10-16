package io;

import parameter.Values;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class IO {

    public IO(){
        File location = new File(Values.PREFERENCE_SAVE_PATH);
        if(!location.exists()){
            location.mkdir();
            if(!location.exists()){
                Values.PREFERENCE_SAVE_PATH = Values.TITLE+File.separator;
                File newPreferencePath = new File(Values.PREFERENCE_SAVE_PATH);
                newPreferencePath.mkdir();
                if(newPreferencePath.exists()) {
                    File savePath = new File(Values.defaultLocation);
                    if(!savePath.exists()){
                        Values.defaultLocation = Values.TITLE+File.separator;
                        File newSavePath = new File(Values.defaultLocation);
                        newSavePath.mkdir();
                        if(!newSavePath.exists()){
                            System.exit(0);
                        }
                    }
                }else{
                    System.exit(0);
                }
            }
        }
    }

    public static String getSettings(){
        return "[Settings]\n"
                +"fullscreen="+Values.fullscreen+";\n"
                +"custom="+Values.customFrame[0]+","+Values.customFrame[1]+","+Values.customFrame[2]+","+Values.customFrame[3]+";\n"
                +"delay="+Values.delay+";\n"
                +"save location="+Values.defaultLocation+";\n"
                +"theme="+Values.theme+";\n"
                +"save as="+Values.format+";\n"
                +"always on top="+Values.alwaysOnTop+";\n"
                +"fps="+Values.fps+";\n"
                +"duration="+Values.duration+";";
    }

    public static void saveImage(BufferedImage ss){
        String imgFormat = Values.format == 0 ? "png" : "jpg";
        String imgName = Values.FILE_NAME;
        int i = 0;
        File file = new File(Values.defaultLocation+Values.FILE_NAME+i+"."+imgFormat);
        while(file.exists()){
            i++;
            file = new File(Values.defaultLocation+Values.FILE_NAME+i+"."+imgFormat);
        }
        try {
            ImageIO.write(ss, imgFormat, new File(Values.defaultLocation + imgName + i+"." + imgFormat));
        }catch(Exception ex){
            System.out.println(ex);
        }
    }

    public boolean read(){
        File preferenceFile = new File(Values.PREFERENCE_SAVE_PATH+Values.PREFERENCE_FILE_NAME);
        if(!preferenceFile.exists()){
            write();
        }
        Scanner reader = null;
        try{
            String content = "";
            reader = new Scanner(preferenceFile);
            while(reader.hasNextLine()) {
                content+=reader.next();
            }
            if(!content.equals("")){
                String lines[] = content.split(";");
                System.out.println(lines.length);
                if(lines.length==9){
                    Values.fullscreen = lines[0].split("=")[1].equals("true");
                    for(int i=0;i<Values.customFrame.length;i++){
                        Values.customFrame[i]=Integer.parseInt(lines[1].split("=")[1].split(",")[i]);
                    }
                    Values.delay = Integer.parseInt(lines[2].split("=")[1]);
                    Values.defaultLocation = lines[3].split("=")[1];
                    Values.theme = Integer.parseInt(lines[4].split("=")[1]);
                    Values.format = Integer.parseInt(lines[5].split("=")[1]);
                    Values.alwaysOnTop = lines[6].split("=")[1].equals("true");
                    Values.fps = Integer.parseInt(lines[7].split("=")[1]);
                    Values.duration = Integer.parseInt(lines[8].split("=")[1]);
                    return true;
                }
            }
        }catch(Exception ex){
            System.out.println(ex);
        }finally{
            try{
                reader.close();
            }catch(Exception ex){
                System.out.println(ex);
            }
        }
        return false;
    }

    public void write(){
        String content = getSettings();
        File file = new File(Values.PREFERENCE_SAVE_PATH+Values.PREFERENCE_FILE_NAME);
        FileWriter fw = null;
        try{
            fw = new FileWriter(file);
            fw.write(content);
        }catch(Exception ex){
            System.out.println(ex);
        }finally{
            try{
                fw.close();
            }catch (Exception ex){
                System.out.println(ex);
            }
        }
    }

    public static void main(String[] args) throws Exception{
        //these 2 lines must stay in the main method
        Values.customFrame[2] = 300;
        Values.customFrame[3] = 300;
        new IO().write();
    }

}
