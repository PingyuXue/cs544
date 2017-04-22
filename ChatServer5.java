
/**
 * Created by Gaffe_D on 4/4/17.
 */

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ChatServer5 {
    private static String prefix = "/Users/Gaffe_D/Desktop/cs544_pro/audio_2/test2_animal/5/";
    private static int count = 1;

    private static int fileNumber = 987;

    private  int port = 8205;
    private  boolean flag = true;


    private static List<AudioSlice> set1 = new ArrayList<>();
    private static InterLeavingPckg pckg1;

    private static List<AudioSlice> set2 = new ArrayList<>();
    private static InterLeavingPckg pckg2;

    private static List<AudioSlice> set3 = new ArrayList<>();
    private static InterLeavingPckg pckg3;

    private static List<AudioSlice> set4 = new ArrayList<>();
    private static InterLeavingPckg pckg4;

    private static List<AudioSlice> set5 = new ArrayList<>();
    private static InterLeavingPckg pckg5;


    private static AudioSlice out_of_bond_audio_slice= new AudioSlice(-1);

    public ChatServer5() {
    }

    // 创建指定端口的服务器
    public ChatServer5(int port) {
        this.port = port;
    }


    public void service() {
        try {// 建立服务器连接
            ServerSocket server = new ServerSocket(port);
            Socket socket = server.accept();

            try {
                ObjectOutputStream outToClient = new ObjectOutputStream(socket.getOutputStream());

                while (true) {
                    // send data:
                    Date date = new Date();

                    while ( count <= fileNumber && flag == true){

                        // edge case: when the last number not 16
                        for (int i = 1; i <= 25; i++) {

                            if (i % 5 == 1) {

                                if ( count <= fileNumber) {
                                    String fileNumber = String.valueOf(count);
                                    File files = new File(prefix + fileNumber);

                                    AudioSlice audioSlice = new AudioSlice(files);

                                    audioSlice.setPostion(count);
                                    audioSlice.setSliceNumber(1);
                                    audioSlice.setSendTime(date.getTime());

                                    set1.add(audioSlice);
                                    count++;
                                }
                                else {
                                    set1.add(out_of_bond_audio_slice);
                                }
                            }

                            if (i % 5 == 2) {
                                if ( count <= fileNumber) {
                                    String fileNumber = String.valueOf(count);
                                    File files = new File(prefix + fileNumber);

                                    AudioSlice audioSlice = new AudioSlice(files);

                                    audioSlice.setPostion(count);
                                    audioSlice.setSliceNumber(2);

                                    audioSlice.setSendTime(date.getTime());

                                    set2.add(audioSlice);
                                    count++;
                                }
                                else {
                                    set2.add(out_of_bond_audio_slice);
                                }
                            }

                            if (i % 5 == 3) {
                                if ( count <= fileNumber) {
                                    String fileNumber = String.valueOf(count);
                                    File files = new File(prefix + fileNumber);

                                    AudioSlice audioSlice = new AudioSlice(files);

                                    audioSlice.setPostion(count);

                                    audioSlice.setSendTime(date.getTime());
                                    audioSlice.setSliceNumber(3);

                                    set3.add(audioSlice);
                                    count++;
                                }
                                else {
                                    set3.add(out_of_bond_audio_slice);
                                }
                            }

                            if (i % 5 == 4) {
                                if ( count <= fileNumber) {
                                    String fileNumber = String.valueOf(count);
                                    File files = new File(prefix + fileNumber);

                                    AudioSlice audioSlice = new AudioSlice(files);

                                    audioSlice.setPostion(count);

                                    audioSlice.setSendTime(date.getTime());
                                    audioSlice.setSliceNumber(4);

                                    set4.add(audioSlice);
                                    count++;
                                }
                                else {
                                    set4.add(out_of_bond_audio_slice);
                                }
                            }



                            if (i % 5 == 0) {
                                if ( count <= fileNumber) {
                                    String fileNumber = String.valueOf(count);
                                    File files = new File(prefix + fileNumber);

                                    AudioSlice audioSlice = new AudioSlice(files);

                                    audioSlice.setPostion(count);

                                    audioSlice.setSendTime(date.getTime());
                                    audioSlice.setSliceNumber(5);

                                    set5.add(audioSlice);
                                    count++;
                                }
                                else {
                                    set5.add(out_of_bond_audio_slice);
                                }
                            }
                        }

                        pckg1 = new InterLeavingPckg(set1);
                        pckg1.setPckNumber(1);

                        pckg2 = new InterLeavingPckg(set2);
                        pckg2.setPckNumber(2);

                        pckg3 = new InterLeavingPckg(set3);
                        pckg3.setPckNumber(3);

                        pckg4 = new InterLeavingPckg(set4);
                        pckg4.setPckNumber(4);

                        pckg5 = new InterLeavingPckg(set5);
                        pckg5.setPckNumber(5);


                        outToClient.writeObject(pckg1);
                        outToClient.writeObject(pckg2);
                        outToClient.writeObject(pckg3);
                        outToClient.writeObject(pckg4);
                        outToClient.writeObject(pckg5);

                        // reset output stream
                        outToClient.reset();

                        // free the set and pck
                        System.out.println("pack1 details: slice 2 infor, pos:"+ pckg1.interleavingSet.get(1).getPostion());
                        System.out.println("pack2 details: slice 2 infor, send time:"+ pckg2.interleavingSet.get(1).getSendTime());
                        System.out.println("pack3 details: slice 2 infor, file detils:"+ pckg3.interleavingSet.get(1).getFileSlice());

                        set1.clear();
                        set2.clear();
                        set3.clear();
                        set4.clear();
                        set5.clear();


                        pckg1 = null;
                        pckg2 = null;
                        pckg3 = null;
                        pckg4 = null;
                        pckg5 = null;

                        System.out.println("count is :" + count);
                        if ( count > fileNumber){
                            flag = false;
                        }
                    }
                }
            } finally {
                //close if the connnection end or error
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        new ChatServer5().service();
    }
}


