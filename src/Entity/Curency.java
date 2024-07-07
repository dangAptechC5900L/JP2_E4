package Entity;

public enum Curency {
    USD,VND;
    private String curency;
    Curency(){;}
    Curency(String curency){this.curency=curency;}

    public String getCurency() {
        return curency;
    }
}
