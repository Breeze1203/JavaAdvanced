package org.pt.innerclass;

public class parcel1 {
    class Contents{
        private int i=11;
        public int value(){
            return i;
        }
    }
    class Destination{
        private String label;
        Destination(String whereTo){
            label=whereTo;
        }
        String readLabel(){
            return label;
        }
    }

    public void ship(String dest){
        Contents c=new Contents();
        Destination d=new Destination(dest);
        System.out.println(d.readLabel());
    }

    public static void main(String[] args) {
        parcel1 parcel1 = new parcel1();
        parcel1.ship("Tasmania");
    }
}
