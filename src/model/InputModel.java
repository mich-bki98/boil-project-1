package model;

public class InputModel {
    private double[] supply = new double[3];
    private double[] demand = new double[3];
    double[] profitArr = new double[9];

    public InputModel(double[] supply, double[] demand, double[] profitArr) {
        this.supply = supply;
        this.demand = demand;
        this.profitArr = profitArr;
    }

    public double[] getSupply() {
        return supply;
    }

    public void setSupply(double[] supply) {
        this.supply = supply;
    }

    public double[] getDemand() {
        return demand;
    }

    public void setDemand(double[] demand) {
        this.demand = demand;
    }

    public double[] getProfitArr() {
        return profitArr;
    }

    public void setProfitArr(double[] profitArr) {
        this.profitArr = profitArr;
    }
}
