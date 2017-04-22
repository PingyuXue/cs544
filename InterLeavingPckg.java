import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Gaffe_D on 4/19/17.
 */
public class InterLeavingPckg implements java.io.Serializable {

    public List<AudioSlice> interleavingSet = new ArrayList<>();
    private int pckNumber = -1;

    public InterLeavingPckg(List<AudioSlice> interleavingSet) {
        this.interleavingSet = interleavingSet;
    }

    // setter
    public void setInterleavingSet(List<AudioSlice> interleavingSet) {
        this.interleavingSet = interleavingSet;
    }

    public void setPckNumber(int Number) {
        this.pckNumber = Number;
    }


    // getter
    public List<AudioSlice> getInterleavingSet() {
        return interleavingSet;
    }

    public int getPckNumber() {
        return pckNumber;
    }

    public void set_all_slice_time(double arrivetime){
//        long arrivetime = date.getTime();
        for ( int i = 0; i <interleavingSet.size(); i++){
            interleavingSet.get(i).setArriveTime((long)arrivetime);
        }
    }
}
