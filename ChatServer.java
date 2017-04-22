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

public class ChatServer {
    private static String prefix = "/Users/Gaffe_D/Desktop/cs544_pro/audio/";
    private static int count = 1;

    private static int fileNumber = 711;

    private  int port = 8189;
    private  boolean flag = true;


    private static List<AudioSlice> set1 = new ArrayList<>();
    private static InterLeavingPckg pckg1;

    private static List<AudioSlice> set2 = new ArrayList<>();
    private static InterLeavingPckg pckg2;

    private static List<AudioSlice> set3 = new ArrayList<>();
    private static InterLeavingPckg pckg3;

    private static List<AudioSlice> set4 = new ArrayList<>();
    private static InterLeavingPckg pckg4;


    private static AudioSlice out_of_bond_audio_slice= new AudioSlice(-1);

    public ChatServer() {
    }

    // 创建指定端口的服务器
    public ChatServer(int port) {
        this.port = port;
    }

    // 提供服务
    public void service() {
        try {// 建立服务器连接
            ServerSocket server = new ServerSocket(port);
            // 等待客户连接
            Socket socket = server.accept();
            try {
                // 读取客户端传过来信息的DataInputStream
                DataInputStream in = new DataInputStream(socket
                        .getInputStream());

                // 向客户端发送信息的DataOutputStream
                DataOutputStream out = new DataOutputStream(socket
                        .getOutputStream());

                ObjectOutputStream outToClient = new ObjectOutputStream(socket.getOutputStream());
                // 获取控制台输入的Scanner
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    // 读取来自客户端的信息
//                    String accpet = in.readUTF();
//                    System.out.println(accpet);
//                    String send = scanner.nextLine();
//                    System.out.println("服务器：" + send);
//                     把服务器端的输入发给客户端
//                    out.writeUTF("服务器：" + send);


                    // test:
//                    TransferData transferData = new TransferData();
//
//                    Date date = new Date();
//
//                    String fileNumber = String.valueOf(count);
//                    File files = new File(prefix + fileNumber);
//
//                    AudioSlice audioSlice=  new AudioSlice (files);
//                    audioSlice.setSendTime(date.getTime());
//
//                    outToClient.writeObject(audioSlice);



                    // send data:
                    Date date = new Date();



                    int j = 0;
                    while ( count <= fileNumber && flag == true){
//
//                        String send = scanner.nextLine();
//                        System.out.println("服务器：" + send);

                        // edge case: when the last number not 16
                        for (int i = 1; i <= 16; i++) {
//                        List<AudioSlice> set1 = new ArrayList<>();
//                        InterLeavingPckg pckg1 = new InterLeavingPckg(set1);
//
//                        List<AudioSlice> set2 = new ArrayList<>();
//                        InterLeavingPckg pckg2 = new InterLeavingPckg(set2);
//
//                        List<AudioSlice> set3 = new ArrayList<>();
//                        InterLeavingPckg pckg3 = new InterLeavingPckg(set3);
//
//                        List<AudioSlice> set4 = new ArrayList<>();
//                        InterLeavingPckg pckg4 = new InterLeavingPckg(set4);

//                        if (i % 4 == 1) {
                            if (i % 4 == 1) {
//                        if ( count % 4 ==1){
                                if ( count <= fileNumber) {
                                    String fileNumber = String.valueOf(count);
                                    File files = new File(prefix + fileNumber);

                                    AudioSlice audioSlice = new AudioSlice(files);

                                    audioSlice.setPostion(count);
                                    audioSlice.setSliceNumber(1);
//                                    audioSlice.setPostion(1);
                                    audioSlice.setSendTime(date.getTime());


                                    // add data to pck set
//                            pckg1.setPckNumber(1);
//                            pckg1.interleavingSet.add(audioSlice);
                                    set1.add(audioSlice);
                                    count++;
                                }
                                else {
                                    set1.add(out_of_bond_audio_slice);
                                }
                            }

                            if (i % 4 == 2) {
//                        if ( count % 4 == 2 ){
                                if ( count <= fileNumber) {
                                    String fileNumber = String.valueOf(count);
                                    File files = new File(prefix + fileNumber);

                                    AudioSlice audioSlice = new AudioSlice(files);

                                    audioSlice.setPostion(count);
                                    audioSlice.setSliceNumber(2);
//                                    audioSlice.setPostion(2);
                                    audioSlice.setSendTime(date.getTime());

                                    // add data to pck set
//                            pckg2.setPckNumber(2);
//                            pckg2.interleavingSet.add(audioSlice);
                                    set2.add(audioSlice);
                                    count++;
                                }
                                else {
                                    set2.add(out_of_bond_audio_slice);
                                }
                            }

                            if (i % 4 == 3) {
//                        if ( count % 4 == 3){
                                if ( count <= fileNumber) {
                                    String fileNumber = String.valueOf(count);
                                    File files = new File(prefix + fileNumber);

                                    AudioSlice audioSlice = new AudioSlice(files);


                                    audioSlice.setPostion(count);
//                                    audioSlice.setPostion(3);
                                    audioSlice.setSendTime(date.getTime());
                                    audioSlice.setSliceNumber(3);

                                    // add data to pck set
//                            pckg3.setPckNumber(3);
//                            pckg3.interleavingSet.add(audioSlice);
                                    set3.add(audioSlice);
                                    count++;
                                }
                                else {
                                    set3.add(out_of_bond_audio_slice);
                                }
                            }

                        if (i % 4 == 0) {
//                            if (count % 4 == 0) {
                                if ( count <= fileNumber) {
                                    String fileNumber = String.valueOf(count);
                                    File files = new File(prefix + fileNumber);

                                    AudioSlice audioSlice = new AudioSlice(files);

                                    audioSlice.setPostion(count);
                                    audioSlice.setSliceNumber(4);

                                    audioSlice.setSendTime(date.getTime());

                                    // add data to pck set
//                            pckg4.setPckNumber(4);
//                            pckg4.interleavingSet.add(audioSlice);

                                    set4.add(audioSlice);
                                    count++;
                                }
                                else {
                                    set4.add(out_of_bond_audio_slice);
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


                        outToClient.writeObject(pckg1);
                        outToClient.writeObject(pckg2);
                        outToClient.writeObject(pckg3);
                        outToClient.writeObject(pckg4);

                        outToClient.reset();

                        // free the set and pck;

//
//                    System.out.println("pack1 pck nubmer:"+ pckg1.getPckNumber());
//                    System.out.println("pack2 pck nubmer:"+ pckg2.getPckNumber());
//                    System.out.println("pack3 pck nubmer:"+ pckg3.getPckNumber());
//                    System.out.println("pack4 pck nubmer:"+ pckg4.getPckNumber());


                    System.out.println("pack1 details: slice 2 infor, pos:"+ pckg1.interleavingSet.get(1).getPostion());
                    System.out.println("pack2 details: slice 2 infor, send time:"+ pckg2.interleavingSet.get(1).getSendTime());
                    System.out.println("pack3 details: slice 2 infor, file detils:"+ pckg3.interleavingSet.get(1).getFileSlice());
//                    System.out.println("pack4 pck nubmer:"+ pckg4.interleavingSet.get(3).getFileSlice());


                        set1.clear();
                        set2.clear();
                        set3.clear();
                        set4.clear();

                        pckg1 = null;
                        pckg2 = null;
                        pckg3 = null;
                        pckg4 = null;


                        System.out.println("count is :" + count);
                        if ( count > fileNumber){
                            flag = false;
                        }
//                        System.out.println("count is :"+ count);

                    }







                }
            } finally {// 建立连接失败的话不会执行socket.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void trans_InterL_piece ()
    {


    }



    public static void main(String[] args) {
        new ChatServer().service();
    }
}
