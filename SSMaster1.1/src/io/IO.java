package io;

import parameter.Values;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class IO {

    public static int serial = 0;

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
                            JOptionPane.showMessageDialog(null,"Something went wrong!");
                            System.exit(0);
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Something went wrong!");
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
                +"duration="+Values.duration+";\n"
                +"continuous capture="+Values.continuous+";\n"
                +"open after capture="+Values.openAfterCapture +";";
    }

    public static void saveImage(BufferedImage ss){
        String imgFormat = Values.format == 0 ? "png" : "jpg";
        int i = Values.continuous ? serial:0;
        File file;
        while((file = new File(Values.defaultLocation+Values.FILE_NAME+i+"."+imgFormat)).exists()&&!Values.continuous){
            i++;
        }
        try {
            ImageIO.write(ss, imgFormat, file);
            if(Values.openAfterCapture && !Values.continuous){
                if(Values.previewWindow.isVisible()){
                    Values.previewWindow.dispose();
                }
                Values.previewWindow.show(file.getAbsolutePath());
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"The directory specified does not exist! Change the save directory in the preference menu.");
        }

    }

    public boolean read(){
        File preferenceFile = new File(Values.PREFERENCE_SAVE_PATH+Values.PREFERENCE_FILE_NAME);
        if(!preferenceFile.exists()){
            write();
        }
        try{
            String content = readFileContent(preferenceFile);
            if(!content.equals("")){
                String lines[] = content.split(";");
                if(lines.length==11){
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
                    Values.continuous = lines[9].split("=")[1].equals("true");
                    Values.openAfterCapture =lines[10].split("=")[1].equals("true");
                    return true;
                }
            }
        }catch(Exception ex){
            System.out.println(ex);
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

    public static String readFileContent(File file) throws Exception{
        FileInputStream fileInputStream;
        String content;
        fileInputStream = new FileInputStream(file);
        byte[] value = new byte[(int) file.length()];
        fileInputStream.read(value);
        fileInputStream.close();
        content = new String(value, StandardCharsets.UTF_8);
        return content;
    }

    public static void checkSerial(){
        String imgFormat = Values.format == 0?".png":".jpg";
        serial = 0;
        while(new File(Values.defaultLocation+Values.FILE_NAME+serial+imgFormat).exists()){
            serial++;
        }
    }


    public static void print_delay(){
        System.out.println(Values.delay*1000);
    }

}
