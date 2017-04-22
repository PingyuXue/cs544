import java.io.File;

/**
 * Created by Gaffe_D on 4/18/17.
 */
public class AudioSlice implements  java.io.Serializable {

    private int sliceNumber = -1;
    private File fileSlice;


    private long sendTime = -1;
    private long arriveTime = -1;  // check the delay when it arrving.
    private double delaytime = 0;

    private boolean flag = true;  // only ture flag slice audio can be send.



    private int postion = -1;



    //
    // usage 2: replace file , when cannot find any replace piece
    public AudioSlice(File fileSlice) {
        this.fileSlice = fileSlice;

        this.sliceNumber = 99999;
        this.postion = 99999;
        this.sendTime = 1;

        //defult for the arrive
        this.arriveTime = 1;
//        this.delaytime = 6  + (10 * Math.random()) + (1 * Math.random()) ; //   6.1- 17 msec normal delay;

        this.flag = true;

    }

//    public AudioSlice(int sliceNumber) {
//        this.sliceNumber = sliceNumber;
//    }

    // set for the out of bond value
    public AudioSlice( int i){
        if( i == -1){
            this.sliceNumber = -1;
            this.fileSlice = null;
            this.delaytime = 999999;
            this.flag = false;
            this.postion = -1;
        }
    }

    public AudioSlice(int sliceNumber, File fileSlice, long sendTime, long arriveTime, double delaytime, boolean flag, int postion) {
        this.sliceNumber = sliceNumber;
        this.fileSlice = fileSlice;
        this.sendTime = sendTime;
        this.arriveTime = arriveTime;
        this.delaytime = delaytime;
        this.flag = flag;
        this.postion = postion;
    }


    // setter
    public void setSliceNumber(int sliceNumber) {
        this.sliceNumber = sliceNumber;
    }

    public void setFileSlice(File fileSlice) {
        this.fileSlice = fileSlice;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public void setArriveTime(long arriveTime) {
        this.arriveTime = arriveTime;
    }

    public void setDelaytime(double delaytime) {
        this.delaytime = delaytime;
    }

    public void addDelayTime(double extra_delay_time){
        this.delaytime += extra_delay_time;
//        return this.delaytime;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }



    // getter
    public int getSliceNumber() {
        return sliceNumber;
    }


    public File getFileSlice() {
        return fileSlice;
    }

    public long getSendTime() {
        return sendTime;
    }

    public long getArriveTime() {
        return arriveTime;
    }

    public double getDelaytime() {
        return (double)(this.arriveTime)- (this.sendTime);
    }

    public boolean isFlag() {
        return flag;
    }

    public int getPostion() {
        return postion;
    }


}
