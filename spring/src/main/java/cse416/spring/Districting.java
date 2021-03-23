package cse416.spring;

public class Districting {
    public int ID;

    public District[] Districts;

    public Districting(int id) {
        ID = id;
        Districts = new District[]{new District(100), new District(1000), new District(400)};
    }
}
