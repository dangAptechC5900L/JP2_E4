package Entity;

public enum Type {
    WITHDRAWAL, DEPOSIT;
    private String type;
    Type(){;}
    Type(String type){
        this.type=type;
    }

    public String getType() {
        return type;
    }
}
