package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.InputModel;
import org.apache.commons.math3.optimization.GoalType;
import org.apache.commons.math3.optimization.PointValuePair;
import org.apache.commons.math3.optimization.linear.LinearConstraint;
import org.apache.commons.math3.optimization.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optimization.linear.Relationship;
import org.apache.commons.math3.optimization.linear.SimplexSolver;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;


public class Controller {

    @FXML
    private Button button;
    @FXML
    private Label wynik;

    //Demands
    @FXML
    private TextField o1demand;
    @FXML
    private TextField o2demand;
    @FXML
    private TextField o3demand;

    //Supplies
    @FXML
    private TextField d1supp;
    @FXML
    private TextField d2supp;
    @FXML
    private TextField d3supp;

    //Sell Prices
    @FXML
    private TextField sello1;
    @FXML
    private TextField sello2;
    @FXML
    private TextField sello3;

    //Buy Prices
    @FXML
    private TextField buyd1;
    @FXML
    private TextField buyd2;
    @FXML
    private TextField buyd3;

    //route arr
    @FXML
    private TextField d1o1;
    @FXML
    private TextField d1o2;
    @FXML
    private TextField d1o3;
    @FXML
    private TextField d2o1;
    @FXML
    private TextField d2o2;
    @FXML
    private TextField d2o3;
    @FXML
    private TextField d3o1;
    @FXML
    private TextField d3o2;
    @FXML
    private TextField d3o3;

    private InputModel inputModel;
    private double[] demandArr;
    private double[] supplyArr;
    private double[] sellPrices;
    private double[] buyPrices;
    private double[] routeArr;


    //230.0
    @FXML
    void initialize() {
        demandArr = new double[]{Double.parseDouble(o1demand.getText()),
                Double.parseDouble(o2demand.getText()),
                Double.parseDouble(o3demand.getText())};

        supplyArr = new double[]{Double.parseDouble(d1supp.getText()),
                Double.parseDouble(d2supp.getText()),
                Double.parseDouble(d3supp.getText())};

        sellPrices = new double[]{Double.parseDouble(sello1.getText()),
                Double.parseDouble(sello2.getText()),
                Double.parseDouble(sello3.getText())};

        buyPrices = new double[]{Double.parseDouble(buyd1.getText()),
                Double.parseDouble(buyd2.getText()),
                Double.parseDouble(buyd3.getText())};

        routeArr = new double[]{Double.parseDouble(d1o1.getText()),
                Double.parseDouble(d1o2.getText()),
                Double.parseDouble(d1o3.getText()),
                Double.parseDouble(d2o1.getText()),
                Double.parseDouble(d2o2.getText()),
                Double.parseDouble(d2o3.getText()),
                Double.parseDouble(d3o1.getText()),
                Double.parseDouble(d3o2.getText()),
                Double.parseDouble(d3o3.getText())};

        button.setOnAction(this::calculate);
    }


    @FXML
    public void calculate(ActionEvent event) {

        inputModel = new InputModel(supplyArr,demandArr,sellPrices,buyPrices,routeArr);


        LinearObjectiveFunction f = new LinearObjectiveFunction(inputModel.getProfitArr(), 0);

        Collection<LinearConstraint> constraints = new ArrayList<>();

        constraints.add(new LinearConstraint(new double[]{1, 1, 1, 0, 0, 0, 0, 0, 0}, Relationship.LEQ, inputModel.getSupply()[0]));
        constraints.add(new LinearConstraint(new double[]{0, 0, 0, 1, 1, 1, 0, 0, 0}, Relationship.LEQ, inputModel.getSupply()[1]));
        constraints.add(new LinearConstraint(new double[]{0, 0, 0, 0, 0, 0, 1, 1, 1}, Relationship.LEQ, inputModel.getSupply()[2]));

        constraints.add(new LinearConstraint(new double[]{1, 0, 0, 1, 0, 0, 1, 0, 0}, Relationship.LEQ, inputModel.getDemand()[0]));
        constraints.add(new LinearConstraint(new double[]{0, 1, 0, 0, 1, 0, 0, 1, 0}, Relationship.GEQ, inputModel.getDemand()[1]));
        constraints.add(new LinearConstraint(new double[]{0, 0, 1, 0, 0, 1, 0, 0, 1}, Relationship.LEQ, inputModel.getDemand()[2]));


        try {
            SimplexSolver solver = new SimplexSolver();
            PointValuePair solution = solver.optimize(f, constraints, GoalType.MAXIMIZE, true);
            wynik.setText(solution.getValue().toString());
        } catch (Exception ex) {
            String[] str = ex.toString().split(":");
            wynik.setText(str[1]);
        }

        try {
            FileWriter fw = new FileWriter("output.txt",false);
            fw.write("Tabela jednostkowa: \n");
            int id=0;
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    fw.write(Double.toString(inputModel.getProfitArr()[id]));
                    fw.write(" ");
                    id++;
                }
                fw.write("\n");
            }
            fw.write("Zysk całkowity: \n");
            fw.write(wynik.getText());
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}