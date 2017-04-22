
/**
 * Created by Gaffe_D on 4/4/17.
 */


import java.io.*;
import java.net.Socket;
import java.util.*;

public class ChatClient3 {

    /*
    1. normal delay:
       normal delay which cannot be avoid may near 36 msecs
       which are:
                Network delay:         5 msec
                proccess delay:        6 msec
                transimission delay:   0.5 msec
                queue delay:           3 msec
                packagelizon delay:    24 msec

    2. set acceptable delay:     180 msec
    3. interleaving package number: 3
    4. voice chunk lenth:    21 msec
    5. left delay accept:   180- 63 - 36 = 101 msec


     */
    private String host = "localhost";// 连接到本机
    private int port = 8200;// 8200 , slice 3



    private int count = 1;
    public static String prefix = "/Users/Gaffe_D/Desktop/cs544_pro/audio_2/test2_animal/3/";



    // recieved AudioSlice and new InterleavingPackage:
    private static List<AudioSlice> set1 = new ArrayList<>();
    private static InterLeavingPckg pckg1;

    private static List<AudioSlice> set2 = new ArrayList<>();
    private static InterLeavingPckg pckg2;

    private static List<AudioSlice> set3 = new ArrayList<>();
    private static InterLeavingPckg pckg3;

    private static InterLeavingPckg pckgArray[];
    private static List< AudioSlice> set[];



    private static List<File> recivedFile = new ArrayList<>();

    private static int fileNumber = 573;
    private static int fileSlice = 3;

    private static double cannotAvoidDelay1 = 6  + 5 * Math.random() + 3 + 0.5   + ((10 * Math.random() + 10 * Math.random()))/10; //   set 10 msec for unknown
    private static double cannotAvoidDelay2 = 24 ;


    private int pckNumber;

    private long DelayofDjitter = -1;


    // de_jitter delay happen times and rate
    private static double DeJitter_happen_rate = 0;
    private static double Dejitter_happen_times = 0;
    private static boolean Dejitter_happen_trueORfalse = true;


    public ChatClient3() {

    }

    // connect  to the server 3
    public ChatClient3(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void chat() {
        try {
            Socket socket = new Socket(host, port);

            try {
                ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());


                while (true) {
                    Date date = new Date();

                    try {

                        long arriveTime = date.getTime();
                        // step1 : get the objects from the server:
                        Object getFromServe1 = inFromServer.readObject();
                        Object getFromServe2 = inFromServer.readObject();
                        Object getFromServe3 = inFromServer.readObject();


                        // test input
//                        InterLeavingPckg fromServe1 = (InterLeavingPckg)getFromServe1;
//                        System.out.println("from serve1 , pck number:"+fromServe1.getPckNumber());
//                        System.out.println("package deteail:"+ fromServe1.interleavingSet.get(0).getSendTime());
//
//                        System.out.println("*********server 1 ***************");
//                        for ( int i = 0 ; i < fromServe1.interleavingSet.size(); i++){
//                            int pos= fromServe1.interleavingSet.get(i).getPostion();
//                            System.out.println("the i:"+ i+ " audio slice position is:" + pos );
//
//                        }
//
//
//                        InterLeavingPckg fromServe2 = (InterLeavingPckg)getFromServe2;
//                        System.out.println("from serve2 , pck number:"+fromServe2.getPckNumber());
//
//                        System.out.println("*********server 2 ***************");
//                        for ( int i = 0 ; i < fromServe2.interleavingSet.size(); i++){
//                            int pos= fromServe2.interleavingSet.get(i).getPostion();
//                            System.out.println("the i:"+ i+ " audio slice position is:" + pos );
//
//                        }
//
//                        InterLeavingPckg fromServe3 = (InterLeavingPckg)getFromServe3;
//                        System.out.println("from serve3 , pck number:"+fromServe3.getPckNumber());
//
//                        System.out.println("*********server 3 ***************");
//                        for ( int i = 0 ; i < fromServe3.interleavingSet.size(); i++){
//                            int pos= fromServe3.interleavingSet.get(i).getPostion();
//                            System.out.println("the i:"+ i+ " audio slice position is:" + pos );
//
//                        }
//
//                        InterLeavingPckg fromServe4 = (InterLeavingPckg)getFromServe4;
//                        System.out.println("from serve4 , pck number:"+fromServe4.getPckNumber());
//
//                        System.out.println("*********server 4 ***************");
//
//                        for ( int i = 0 ; i < fromServe4.interleavingSet.size(); i++){
//                            int pos= fromServe4.interleavingSet.get(i).getPostion();
//                            System.out.println("the i:"+ i+ " audio slice position is:" + pos );
//
//                        }

                        // step 2: get the file and put into new pcks:
                        // bond:
                        // 1: delay less than 150 mssec:
                        // 2: not missing
                        // 3: if missing or delayed, how to fix?  ( extra)

                        // step 1: get the data
                        InterLeavingPckg [] interLeavingPckgs = new InterLeavingPckg[3];
                        interLeavingPckgs[0]= (InterLeavingPckg)getFromServe1;
                        interLeavingPckgs[1] = ( InterLeavingPckg) getFromServe2;
                        interLeavingPckgs[2] = ( InterLeavingPckg) getFromServe3;


                        if (count >5 && count < 60){
                            TransferData randomGetter = new TransferData();

                            int happendRate =randomGetter.setHappendRandom();

//                            long DelayofDjitter;
                            if ( happendRate >= 5){
                                DelayofDjitter = randomGetter.randomDeJitter_Delay();
                                Dejitter_happen_times ++;
                                pckNumber = randomGetter.getRandomPackage();
                            }

                        }


                        DeJitter_happen_rate = Dejitter_happen_times/(fileNumber / fileSlice );
                        System.out.println("happen rate:"+ DeJitter_happen_rate);

                        if ( DeJitter_happen_rate >= 0.1){
                            Dejitter_happen_trueORfalse = false;
                        }

                        if ( DelayofDjitter > 0 && Dejitter_happen_trueORfalse == true){
//                            for ( int i = 0; i< 4 ; i++){
//                                interLeavingPckgs[i].set_all_slice_time(interLeavingPckgs[i].interleavingSet.get(1).getSendTime() + DelayofDjitter + 36 + 100);
//
//                            }
                            if ( pckNumber >= 0 && pckNumber <=2) {
                                interLeavingPckgs[pckNumber].set_all_slice_time(interLeavingPckgs[pckNumber].interleavingSet.get(0).getSendTime() + DelayofDjitter + cannotAvoidDelay1 + cannotAvoidDelay2 +63);
                                System.out.println("De jitter happen: " + DelayofDjitter);
                            }
                        }
                        else
                        {
                            for ( int i = 0; i < 3; i++){
                                interLeavingPckgs[i].set_all_slice_time(interLeavingPckgs[i].interleavingSet.get(0).getSendTime() + 36 + 63);
                            }
                        }


                        for ( int i = 0; i < 3; i ++){
                            for ( int j = 0; j< 3; j++){
                                int slice = interLeavingPckgs[i].interleavingSet.get(j).getSliceNumber();

                                if ( j == 0 ) {
                                    count++;

                                    double delayTime = (double) interLeavingPckgs[i].interleavingSet.get(j).getDelaytime();

                                    if ( delayTime > 170){

                                        System.out.println("set 1 in");

                                        interLeavingPckgs[i].interleavingSet.get(j).setFlag(false);

                                        File replaeFile = new File( prefix + "10");
                                        AudioSlice replace = new AudioSlice(replaeFile);
                                        set1.add(replace);

                                        System.out.print("set 1 add ");
                                    }

                                    else {
                                        set1.add(interLeavingPckgs[i].interleavingSet.get(j));
                                    }
                                }

                                if ( j == 1) {
                                    count++;
                                    double delay = (double)interLeavingPckgs[i].interleavingSet.get(j).getDelaytime();

                                    System.out.println("check delay time"+ delay);

                                    // file is availbe but out of bound
                                    if (delay> 170) {
//                                        ***************set2.add(interLeavingPckgs[i].interleavingSet.get(j));

                                        // set this file's flag is false, it will not be treat as a replace file when other file occurs missing
                                        interLeavingPckgs[i].interleavingSet.get(j).setFlag(false);
                                        System.out.println("set 2 in");

                                        // add the voice piece between it.
                                        // find a replace file
//                                        if ( i > 0){
//                                            if ( interLeavingPckgs[i-1].interleavingSet.get(j).isFlag() == true) {
//                                                set2.add(interLeavingPckgs[i - 1].interleavingSet.get(j));
//                                                System.out.print("set 2 add i");
//
//                                            }
//
//                                        }else if ( j > 0) {
//                                            if ( interLeavingPckgs[i].interleavingSet.get(j-1).isFlag() == true) {
//                                                set2.add(interLeavingPckgs[i].interleavingSet.get(j - 1));
//                                                System.out.print("set 2 add j ");
//
//                                            }
//
//                                            // all repalce file cannot be found:
//                                        }else {
//                                            File replaeFile = new File( prefix + "10");
//                                            AudioSlice replace = new AudioSlice(replaeFile);
////                                            set2.add(replace);
//                                            set2.add(interLeavingPckgs[i].interleavingSet.get(1));
//                                            System.out.print("set 2 add ");
//                                        }

                                        File replaeFile = new File( prefix + "10");
                                        AudioSlice replace = new AudioSlice(replaeFile);
                                        set2.add(replace);
                                        System.out.print("set 2 add ");

                                    }
                                    else {
                                        set2.add(interLeavingPckgs[i].interleavingSet.get(j));

                                    }
                                }

                                if ( j == 0) {
                                    count++;

                                    System.out.println("set 3 in");
                                    if (count == -1){
                                        set3.add(interLeavingPckgs[i].interleavingSet.get(1));
                                    }
                                    else {
                                        set3.add(interLeavingPckgs[i].interleavingSet.get(j));
                                    }
                                }
                            }
                        }

                        pckgArray = new InterLeavingPckg[3];
                        pckgArray[0] = new InterLeavingPckg(set1);
                        pckgArray[1] = new InterLeavingPckg(set2);
                        pckgArray[2] = new InterLeavingPckg(set3);

                        for ( int i = 0; i< 3 ; i++){
                            System.out.println("size of" +pckgArray[i].interleavingSet.size());

                        }


                        //step 3: load the valued file
                        for ( int i = 0; i< 3;i ++){
                            for (int j = 0; j < 3; j++) {
                                if (pckgArray[i].interleavingSet.get(j).getSliceNumber() != -1) {
                                    recivedFile.add(pckgArray[i].interleavingSet.get(j).getFileSlice());
                                    System.out.println("rece file is :" + pckgArray[i].interleavingSet.get(j).getFileSlice());
                                } else {
                                    break;
                                }
                            }
                        }

                        for ( int i = 0 ; i < 3 ; i++){
                            pckgArray[i] = null;
                        }

                        set1.clear();
                        set3.clear();
                        set2.clear();

                        System.out.println("count is:"+ count);
                        TransferData transferData = new TransferData();
                        File outputFile = new File(prefix + "new_6.mp3");
//                        File outputFile_2 = new File( prefix + "p4_170ms_test1");
                        transferData.megerFiles(recivedFile,outputFile);
//                        while ( count <= fileNumber) {
//                            for (int i = 0; i < 4; i++) {
//                                for (int j = 0; j < 4; j++) {
//                                    if (interLeavingPckgs[i].interleavingSet.get(j) != null) {
////                                interLeavingPckgs[i].interleavingSet.get(j).setArriveTime(arriveTime);
//                                        int pos = interLeavingPckgs[i].interleavingSet.get(j).getPostion();
//                                        long delayTime = interLeavingPckgs[i].interleavingSet.get(j).getArriveTime() - interLeavingPckgs[i].interleavingSet.get(j).getSendTime();
//                                        if (pos % 4 == 1 && (delayTime <= 50)) {
//                                            set1.add(interLeavingPckgs[i].interleavingSet.get(j));
//                                            recivedFile.add(interLeavingPckgs[i].interleavingSet.get(j).getFileSlice());
//                                            System.out.println("set 1, add, file is :" + interLeavingPckgs[i].interleavingSet.get(j).getFileSlice());
//                                            count++;
//                                        }
//
//                                        if (pos % 4 == 2 && (delayTime <= 50)) {
//                                            set2.add(interLeavingPckgs[i].interleavingSet.get(j));
//                                            recivedFile.add(interLeavingPckgs[i].interleavingSet.get(j).getFileSlice());
//                                            System.out.println("set 2, add, file is :" + interLeavingPckgs[i].interleavingSet.get(j).getFileSlice());
//                                            count++;
//                                        }
//
//                                        if (pos % 4 == 3 && (delayTime <= 50)) {
//                                            set3.add(interLeavingPckgs[i].interleavingSet.get(j));
//                                            recivedFile.add(interLeavingPckgs[i].interleavingSet.get(j).getFileSlice());
//                                            System.out.println("set 3, add, file is :" + interLeavingPckgs[i].interleavingSet.get(j).getFileSlice());
//                                            count++;
//                                        }
//
//                                        if (pos % 4 == 0 && (delayTime <= 50)) {
//                                            set4.add(interLeavingPckgs[i].interleavingSet.get(j));
//                                            recivedFile.add(interLeavingPckgs[i].interleavingSet.get(j).getFileSlice());
//                                            System.out.println("set 4, add, file is :" + interLeavingPckgs[i].interleavingSet.get(j).getFileSlice());
//                                            count++;
//                                        }
//                                    }
//                                }
////                            }
//                        }


                        // sort the incoming package:



                        // get file from the package and put them into a new package



//                        System.out.println("file size  is :"+ recivedFile.size());
//
//                        TransferData transferData = new TransferData();
//                        File outPutFile = new File(prefix + "getFromServer");
//                        transferData.megerFiles(recivedFile,outPutFile);
////                        transferData.playSound(outPutFile);

                    }catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            } finally {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new ChatClient3().chat();
    }
}


