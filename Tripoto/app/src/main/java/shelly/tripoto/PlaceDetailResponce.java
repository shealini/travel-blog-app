package shelly.tripoto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shelly on 11/12/15.
 */
public class PlaceDetailResponce {

    private Result result;

    public Result getResult() {
        return result;
    }

    public static class Result{
        private int limit;
        private int offset ;
        private List<PlaceDetails> data ;

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public List<PlaceDetails> getData() {
            return data;
        }

        public void setData(List<PlaceDetails> data) {
            this.data = data;
        }
    }
}
