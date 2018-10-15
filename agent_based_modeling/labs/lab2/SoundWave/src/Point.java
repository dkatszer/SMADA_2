public class Point {

    public Point nNeighbor;
    public Point wNeighbor;
    public Point eNeighbor;
    public Point sNeighbor;
    public float nVel;
    public float eVel;
    public float wVel;
    public float sVel;
    public float pressure;
    public static Integer[] types = {0, 1, 2};
    int type;
    int sinInput;

    public Point() {
        clear();
        type = 0;
        sinInput = 0;
    }

    public void clicked() {
        pressure = 1;
    }

    public void clear() {
        nVel = 0;
        sVel = 0;
        eVel = 0;
        wVel = 0;
        pressure = 0;
    }

    public void updateVelocity() {
        if (type == 0) {
            nVel = nVel - (nNeighbor.pressure - this.pressure);
            sVel = sVel - (sNeighbor.pressure - this.pressure);
            wVel = wVel - (wNeighbor.pressure - this.pressure);
            eVel = eVel - (eNeighbor.pressure - this.pressure);
        }
    }

    public void updatePresure() {
        if (type == 0) {
            this.pressure = (float) (this.pressure - (0.5 * (nVel + sVel + wVel + eVel)));
        } else if (type == 2) {
            double radians = Math.toRadians(sinInput);
            pressure = (float) (Math.sin(radians));
        }
        if(sinInput>=360){
            sinInput =0;
        }else{
            sinInput+=4;
        }
    }

    public float getPressure() {
        return pressure;
    }
}