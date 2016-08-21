package atlas.view;

import java.io.File;

public class FileWalker { 
    // http://stackoverflow.com/questions/2938942/file-explorer-java
    public void walk( String path ) { 

        File root = new File( path );
        if(!root.exists()) {
            throw new IllegalAccessError();
        }
        File[] list = root.listFiles(); 

        System.out.println("Searching file from " + root);
        System.out.println("list = " + list);
        for ( File f : list ) { 
            if ( f.isDirectory() ) { 
                System.out.println( "Dir:" + f.getAbsoluteFile() );
                for(File fs : f.listFiles()) {
                    if(fs.isFile()) {
                        System.out.println( "File:" + fs.getAbsoluteFile() );
                    }
                }
            }
        } 
    } 

    public static void main(String[] args) { 
        FileWalker fw = new FileWalker(); 
        fw.walk(System.getProperty("user.dir")+"/bodies");
//        System.out.println(System.getProperty("user.dir")+"/res");
    } 
} 