package model;

public class InputModel {
    private double[] supply;
    private double[] demand;
    double[] profitArr;

    public InputModel(double[] supply, double[] demand, double[] profitArr) {
        this.supply = supply;
        this.demand = demand;
        this.profitArr = profitArr;
    }

    public double[] getSupply() {
        return supply;
    }

    public double[] getDemand() {
        return demand;
    }

    public double[] getProfitArr() {
        return profitArr;
    }

}
