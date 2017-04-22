import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javazoom.jl.player.*;

/**
 * Created by Gaffe_D on 4/18/17.
 */
public class TransferData {

    private static String soundfile = "/Users/Gaffe_D/Desktop/cs544_pro/audio/test.mp3";
    private static String prefix = "/Users/Gaffe_D/Desktop/cs544_pro/audio/";

    private static String prefix_slice3_animal = "/Users/Gaffe_D/Desktop/cs544_pro/audio_2/test2_animal/3/";
    private static String prefix_slice5_animal = "/Users/Gaffe_D/Desktop/cs544_pro/audio_2/test2_animal/5/";

    private static FileInputStream inputAudio;
    private static String TAG = "main";



    // de jitter delay double number:
    private static  double normally_happen = 50f;
    private static  double variance = 35f;


    public void GetSound(){


    }

    // play the mp3 file
    public static void playSound(){
        try
        {
            // input
//            inputAudio = new FileInputStream(soundfile);
//            inputAudio = new FileInputStream(prefix + "getFromServer.mp3");

            // test1, piece 4:
//            inputAudio = new FileInputStream(prefix + "new_5.mp3");
//            Player playMp3 = new Player(inputAudio);


            // test 2:
//            inputAudio = new FileInputStream( prefix_slice3_animal + "new_6.mp3");

            inputAudio = new FileInputStream( prefix_slice5_animal + "new_6.mp3");
            Player playMp3 = new Player(inputAudio);



            // test 3:



            playMp3.play();

        }catch (Exception ex){
            System.out.print("Error with playing sound.");
            ex.printStackTrace();
        }

        return ;
    }

    public void playFileSound(File fileName){
        try
        {

            inputAudio = new FileInputStream(prefix + fileName);
            Player playMp3 = new Player(inputAudio);
//            String tmpS = String.valueOf(fileInputStream);
//            System.out.println("tmps is :"+ tmpS);
            playMp3.play();

        }catch (Exception ex){
            System.out.print("Error with playing sound.");
            ex.printStackTrace();
        }

        return ;
    }


    // useless
    // convert the input mp3 file into string
    // this method is unright. out put string.
    public static String transferSoundToString(FileInputStream fileInputStream, String encoding) throws IOException{


        try (BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( fileInputStream, encoding))){
            StringBuilder stringBuilder = new StringBuilder();

            String line ;
            while ((line = bufferedReader.readLine())!=null){
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        }
    }

    // usless.
    public void transferSoundToBit (FileInputStream fileInputStream) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("/Users/Gaffe_D/Desktop/cs544_pro/audio/output.txt"));
        byte content[] = new byte[100];

        while ( fileInputStream.read(content) != -1){
            for ( byte b: content){
                for ( int x = 0; x< 8; x++){
                    bufferedWriter.write(b>>x & 1);
                }
            }
        }
    }


    // split file into piece
    public static void fileSplit(File file) throws IOException{

        int partCount = 1;


        // slice 1: animal_slice 4:
        // 8881.25 bytes  = 1 sec
        // 5/1000 =  5 msec .  1/200  = 45 bytes
//        int sizeOfFile = 50; // 50 bytes


        // slice 2: animal_slice 3:
        // 8881.25 bytes
        // 7/1000 = 7 msec , = 62 bytes
//        int sizeOfFile = 62;



        // slice 3: animal_slice 5:
        // 8881 bytes
        // 4/ 10000 ,  == 35.5bytes
        int sizeOfFile = 36 ;
        byte [] buffer = new byte[sizeOfFile];

        try( BufferedInputStream bufferedInputStream = new BufferedInputStream( new FileInputStream(file))){

            String nmae = file.getName();

            int tmp = 0;
            while ( ( tmp = bufferedInputStream.read(buffer))> 0){

                File newFile = new File(file.getParent(), String.format("%d", partCount++));
                try( FileOutputStream out = new FileOutputStream( newFile)){
                    out.write(buffer,0,tmp);
                }
            }
        }
    }


    public void megerFiles(List<File> files, File into) throws IOException{
        try ( BufferedOutputStream merginStreaming = new BufferedOutputStream( new FileOutputStream(into))){
            for ( File f: files){
                Files.copy(f.toPath(),merginStreaming);
            }
        }

    }

    // test re-assemable the split data.
    //testing
    public static List<File> readFile() throws FileNotFoundException {
        String string[] = new String [] {"1","2","3","4","5"};
        List<File> files = new ArrayList<>();

        for ( int i = 0; i < string.length; i++) {
            File file= new File(prefix+ string[i]);
            files.add(file);
        }
        return files;

    }


    // random set the jitter lose or delay
    public static void randomChange (){

    }


    // set De-jitter dealy for the packet.
    public long randomDeJitter_Delay(){

        Random D_jitter_delay = new Random();

//        double normally_happen = 0.01f;
//        double variance = 0.005f;

        long delayForDejitter = 0;

        delayForDejitter = (long) (normally_happen + D_jitter_delay.nextGaussian()* variance);

        return delayForDejitter;

    }


    // set the possible when delay happens
    // for network delay and de-jitter happens
    // if over 5, de-jitter may happen, and network may more conjestion
    public int setHappendRandom(){

        int happends = 0;

        Random random = new Random();
        happends = random.nextInt(10);

        return happends;
    }


    // Random lost package
    public  int getRandomPackage(){
        int packageNumber = 0;

        Random random = new Random();

        long range = 4;
        long fraction = (long) (range * random.nextDouble());
        packageNumber = (int) (fraction + 0);

        return packageNumber;
    }



    // set the limition of the delay happens ( de-jitter delay cause the package lose)  rate <= 10%
    // public void countPackageLose(){}
    // implement method  in ChatClient...






    public static void main(String arg[]) throws IOException {
        // play the mp3 in the java

        // test 1:
        FileInputStream file = new FileInputStream(prefix + "getFromServer.mp3");
        playSound();


        // test2:
        // split file 1
//        fileSplit( new File(soundfile));
//        List<File> files ;
//        files = readFile();

        // split file 2:
//        fileSplit( new File(prefix_slice3_animal + "test.mp3"));

        // split file 3:
//        fileSplit( new File(prefix_slice5_animal + "test.mp3"));
//
        // test3 : merge file
//        File newFile = new File(prefix + "output");
//
//        megerFiles(files,newFile);
//
//        inputAudio = new FileInputStream( new File(soundfile));
//        transferSoundToBit(inputAudio);
//        System.out.print("done");


        // test 4:
        // random
//        for ( int i = 0; i < 10; i ++){
//            long number = randomDeJitter_Delay();
//            int random = setHappendRandom();
//            System.out.println("random is "+ random);
//            System.out.println("delay is :" + number);
//
//            if ( random >= 5){
//                System.out.println("delay in the random limit:"+ number);
//            }
//        }

        // test 5:
//        for ( int i = 0; i< 100 ; i++) {
//            int pck = getRandomPackage();
//            System.out.println("pck is;" + pck);
//        }
    }
}
