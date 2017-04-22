/**
 * Created by Gaffe_D on 4/4/17.
 */


import java.io.*;
import java.net.Socket;
import java.util.*;

public class ChatClient {

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
    3. interleaving package number: 4
    4. voice chunk lenth:   20 msec
    5. left delay accept:   180- 80 - 36 = 64 msec


     */
    private String host = "localhost";// 连接到本机
    private int port = 8189;// 默认端口8189



    private int count = 1;
    public static String prefix = "/Users/Gaffe_D/Desktop/cs544_pro/audio/";



    // recieved AudioSlice and new InterleavingPackage:
    private static List<AudioSlice> set1 = new ArrayList<>();
    private static InterLeavingPckg pckg1;

    private static List<AudioSlice> set2 = new ArrayList<>();
    private static InterLeavingPckg pckg2;

    private static List<AudioSlice> set3 = new ArrayList<>();
    private static InterLeavingPckg pckg3;

    private static List<AudioSlice> set4 = new ArrayList<>();
    private static InterLeavingPckg pckg4;

    private static InterLeavingPckg pckgArray[];
    private static List< AudioSlice> set[];

//    private static List<File> recevieFile = new ArrayList<>();


    private static List<File> recivedFile = new ArrayList<>();
    private static int fileNumber = 711;

    private static double cannotAvoidDelay1 = 6  + 5 * Math.random() + 3 + 0.5   + ((10 * Math.random() + 10 * Math.random()))/10; //   set 10 msec for unknown
    private static double cannotAvoidDelay2 = 24 ;

    private int pckNumber;


    private long DelayofDjitter = -1;


    // de_jitter delay happen times and rate
    private static double DeJitter_happen_rate = 0;
    private static double Dejitter_happen_times = 0;
    private static boolean Dejitter_happen_trueORfalse = true;


    public ChatClient() {

    }

    // 连接到指定的主机和端口
    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void chat() {
        try {
            // 连接到服务器
            Socket socket = new Socket(host, port);

            try {
//                Scanner scanner = new Scanner(System.in);
//                // 读取服务器端传过来信息的DataInputStream
//                DataInputStream in = new DataInputStream(socket
//                        .getInputStream());
//                // 向服务器端发送信息的DataOutputStream
//                DataOutputStream out = new DataOutputStream(socket
//                        .getOutputStream());
//
//                 标准输入流，用于从控制台输入
//                Scanner scanner = new Scanner(System.in);
                ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());
//                        test=new ObjectInputStream(socket.getInputStream());
//                while (1>0) {
////                    ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());
//                    try {
//                        Object getFromServe1 = inFromServer.readObject();
//                        Object getFromServe2 = inFromServer.readObject();
//                        Object getFromServe3 = inFromServer.readObject();
//                        Object getFromServe4 = inFromServer.readObject();
////                    Object getFromServe5 = inFromServer.readObject();
//
//
//                        InterLeavingPckg interLeavingPckg = (InterLeavingPckg) getFromServe1;
//                        System.out.println("pck1 , slice 2, pos:" + interLeavingPckg.interleavingSet.get(1).getPostion());
//
//                        interLeavingPckg = (InterLeavingPckg) getFromServe2;
//                        System.out.println("pck2 , slice 2, pos:" + interLeavingPckg.interleavingSet.get(1).getPostion());
//
//                        interLeavingPckg = (InterLeavingPckg) getFromServe3;
//                        System.out.println("pck3 , slice 2, pos:" + interLeavingPckg.interleavingSet.get(1).getPostion());
//
//                        interLeavingPckg = (InterLeavingPckg) getFromServe4;
//                        System.out.println("pck4 , slice 2, pos:" + interLeavingPckg.interleavingSet.get(1).getPostion());
//
////                    interLeavingPckg = (InterLeavingPckg)getFromServe5;
////                    System.out.println("pck5 , slice 2, pos:"+ interLeavingPckg.interleavingSet.get(1).getPostion());
//
//                    } catch (ClassNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }

                while (true) {
                    Date date = new Date();

//                    String send = scanner.nextLine();
//                    System.out.println("客户端：" + send);
                    // 把从控制台得到的信息传送给服务器
//                    out.writeUTF("客户端：" + send);
                    // 读取来自服务器的信息
//                    String accpet = in.readUTF();
//                    System.out.println(accpet);
                    try {
                        // test 1:
//                        Object getFromsever = inFromServer.readObject();
//                        AudioSlice fileFromServe = (AudioSlice) getFromsever;
//
//                        fileFromServe.setArriveTime(date.getTime());
//                        System.out.println("file frome server, arraytime" +fileFromServe.getArriveTime()+ "\nsend time:" + fileFromServe.getSendTime()
//                                            + "\nfile slicen number:"+ fileFromServe.getSliceNumber());
                        long arriveTime = date.getTime();
                        // step1 : get the objects from the server:
                        Object getFromServe1 = inFromServer.readObject();
                        Object getFromServe2 = inFromServer.readObject();
                        Object getFromServe3 = inFromServer.readObject();
                        Object getFromServe4 = inFromServer.readObject();

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
                        InterLeavingPckg [] interLeavingPckgs = new InterLeavingPckg[4];
                        interLeavingPckgs[0]= (InterLeavingPckg)getFromServe1;
                        interLeavingPckgs[1] = ( InterLeavingPckg) getFromServe2;
                        interLeavingPckgs[2] = ( InterLeavingPckg) getFromServe3;
                        interLeavingPckgs[3] = ( InterLeavingPckg) getFromServe4;

//                        set = (List<AudioSlice>[]) new AudioSlice[4];
                        // step 2: revise the data

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

                        DeJitter_happen_rate = Dejitter_happen_times/(fileNumber / 4 );
                        System.out.println("happen rate:"+ DeJitter_happen_rate);

                        if ( DeJitter_happen_rate >= 0.1){
                            Dejitter_happen_trueORfalse = false;
                        }

                        if ( DelayofDjitter > 0 && Dejitter_happen_trueORfalse == true){
//                            for ( int i = 0; i< 4 ; i++){
//                                interLeavingPckgs[i].set_all_slice_time(interLeavingPckgs[i].interleavingSet.get(1).getSendTime() + DelayofDjitter + 36 + 100);
//
//                            }
                            if ( pckNumber >= 0 && pckNumber <=3) {
//                                interLeavingPckgs[pckNumber].set_all_slice_time(interLeavingPckgs[pckNumber].interleavingSet.get(0).getSendTime() + DelayofDjitter + 36 + 100);
                                interLeavingPckgs[pckNumber].set_all_slice_time(interLeavingPckgs[pckNumber].interleavingSet.get(0).getSendTime() + DelayofDjitter + cannotAvoidDelay1 + cannotAvoidDelay2 +100);
                                System.out.println("De jitter happen: " + DelayofDjitter);
                            }
                        }
                        else
                        {
                            for ( int i = 0; i < 4; i++){
                                interLeavingPckgs[i].set_all_slice_time(interLeavingPckgs[i].interleavingSet.get(0).getSendTime() + 36 + 100);
                            }
                        }


                        for ( int i = 0; i < 4; i ++){
                            for ( int j = 0; j< 4; j++){

                                int slice = interLeavingPckgs[i].interleavingSet.get(j).getSliceNumber();
//                                if ( slice == 1){
//                                if ( j == 0 && slice!= -1){
                                if ( j == 0 ) {
                                    count++;

                                    double delayTime = (double) interLeavingPckgs[i].interleavingSet.get(j).getDelaytime();
//                                    if ( interLeavingPckgs[i].interleavingSet.get(j).getDelaytime() > 170){
                                    if ( delayTime > 170){

                                        System.out.println("set 1 in");

                                        interLeavingPckgs[i].interleavingSet.get(j).setFlag(false);

//                                        if ( i > 0){
//                                            if ( interLeavingPckgs[i-1].interleavingSet.get(j).isFlag() == true) {
//                                                set1.add(interLeavingPckgs[i - 1].interleavingSet.get(j));
//                                                System.out.print("set 1 add i ");
//                                            }
//
//                                        }else if ( j > 0) {
//                                            if ( interLeavingPckgs[i].interleavingSet.get(j-1).isFlag() == true) {
//                                                set1.add(interLeavingPckgs[i].interleavingSet.get(j - 1));
//                                                System.out.print("set 1 add j");
//                                            }
//
//                                            // all repalce file cannot be found:
//                                        }else {
                                            File replaeFile = new File( prefix + "10");
                                            AudioSlice replace = new AudioSlice(replaeFile);
                                            set1.add(replace);


                                            // test 2:
//                                            AudioSlice replace2 = new AudioSlice(-1);
//                                            set1.add(replace2 );
//                                            set1.add(interLeavingPckgs[i].interleavingSet.get(1));
                                            System.out.print("set 1 add ");
//                                        }
                                    }


                                    else {
                                        set1.add(interLeavingPckgs[i].interleavingSet.get(j));
                                    }

                                }

//                                if ( slice == 2){
//                                if ( j == 1 && slice!= -1){
                                if ( j == 1) {
                                    count++;
                                    double delay = (double)interLeavingPckgs[i].interleavingSet.get(j).getDelaytime();

                                    System.out.println("check delay time"+ delay);


                                    // file is availbe but out of bound
//                                    if (interLeavingPckgs[i].interleavingSet.get(j).getDelaytime() > 170) {
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

//                                        interLeavingPckgs[i].interleavingSet.get(j).setArriveTime(arriveTime);
//                                    System.out.println("set 2 : file number "+ interLeavingPckgs[i].interleavingSet.get(j).getFileSlice());
                                    }
                                    else {
                                        set2.add(interLeavingPckgs[i].interleavingSet.get(j));
//                                        interLeavingPckgs[i].interleavingSet.get(j).setArriveTime(arriveTime);
//                                    System.out.println("set 2 : file number "+ interLeavingPckgs[i].interleavingSet.get(j).getFileSlice());
                                    }
                                }

//                                if ( slice == 3){
//                                if ( j == 2 && slice!= -1){
                                if ( j == 2) {
                                    count++;

                                    System.out.println("set 3 in");
//
                                    if (count == -1){
//                                    set3.add(interLeavingPckgs[i].interleavingSet.get(j));
                                        set3.add(interLeavingPckgs[i].interleavingSet.get(1));
//                                        interLeavingPckgs[i].interleavingSet.get(j).setArriveTime(arriveTime);
//                                    System.out.println("set 3 : file number "+ interLeavingPckgs[i].interleavingSet.get(j).getFileSlice());
                                    }
                                    else {
                                        set3.add(interLeavingPckgs[i].interleavingSet.get(j));
//                                        interLeavingPckgs[i].interleavingSet.get(j).setArriveTime(arriveTime);
//                                    System.out.println("set 3 : file number "+ interLeavingPckgs[i].interleavingSet.get(j).getFileSlice());

                                    }
                                }

//                                if ( slice == 4){
//                                if ( j == 3 && slice!= -1) {
                                if ( j == 3 ) {
                                    count++;


                                    System.out.println("set 4 in");

                                    if (count  == -1) {
//                                        set4.add(interLeavingPckgs[i].interleavingSet.get(j));
                                        set4.add(interLeavingPckgs[i].interleavingSet.get(2));
//                                        interLeavingPckgs[i].interleavingSet.get(j).setArriveTime(arriveTime);
//                                    System.out.println("set 4 : file number "+ interLeavingPckgs[i].interleavingSet.get(j).getFileSlice());
                                    }
                                    else {
                                        set4.add(interLeavingPckgs[i].interleavingSet.get(j));
//                                        interLeavingPckgs[i].interleavingSet.get(j).setArriveTime(arriveTime);
//                                    System.out.println("set 4 : file number "+ interLeavingPckgs[i].interleavingSet.get(j).getFileSlice());
                                    }

                                }


                            }
                        }

                        pckgArray = new InterLeavingPckg[4];
                        pckgArray[0] = new InterLeavingPckg(set1);
                        pckgArray[1] = new InterLeavingPckg(set2);
                        pckgArray[2] = new InterLeavingPckg(set3);
                        pckgArray[3] = new InterLeavingPckg(set4);

                        for ( int i = 0; i< 4 ; i++){
                            System.out.println("size of" +pckgArray[i].interleavingSet.size());

                        }


                        //step 3: load the valued file

                        for ( int i = 0; i< 4;i ++){
//                            if ( count <= fileNumber ) {
                                for (int j = 0; j < 4; j++) {
                                    if (pckgArray[i].interleavingSet.get(j).getSliceNumber() != -1) {
                                        recivedFile.add(pckgArray[i].interleavingSet.get(j).getFileSlice());
                                        System.out.println("rece file is :" + pckgArray[i].interleavingSet.get(j).getFileSlice());
                                    } else {
                                        break;
                                    }
                                }
                        }

                        for ( int i = 0 ; i < 4 ; i++){
                            pckgArray[i] = null;
                        }

                        set1.clear();
                        set3.clear();
                        set2.clear();
                        set4.clear();

                        System.out.println("count is:"+ count);
                        TransferData transferData = new TransferData();
                        File outputFile = new File(prefix + "new_6.mp3");
                        File outputFile_2 = new File( prefix + "p4_170ms_test1");
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


    public void re_construct(InterLeavingPckg[] interLeavingPckgs){
        File[] files;
        AudioSlice [] audioSlices;

        for ( int i = 0 ; i< 4; i++){
            for ( int j = 0; j< 4; j++){


            }

        }

    }

    public static void main(String[] args) {
        new ChatClient().chat();
    }
}

