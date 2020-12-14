package model;

public class InputModel {
    private double[] supply;
    private double[] demand;
    double[] profitArr;
    double[] sells;
    double[] buys;
    double[] routeArr;

    public InputModel(double[] supply, double[] demand, double[] sells, double[] buys,double[] routeArr) {
        this.supply = supply;
        this.demand = demand;
        this.sells = sells;
        this.buys = buys;
        this.routeArr = routeArr;

        this.profitArr = new double[]{
                sells[0] - routeArr[0] - buys[0],
                sells[1] - routeArr[1] - buys[0],
                sells[2] - routeArr[2] - buys[0],
                sells[0] - routeArr[3] - buys[1],
                sells[1] - routeArr[4] - buys[1],
                sells[2] - routeArr[5] - buys[1],
                sells[0] - routeArr[6] - buys[2],
                sells[1] - routeArr[7] - buys[2],
                sells[2] - routeArr[8] - buys[2]
        };
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
