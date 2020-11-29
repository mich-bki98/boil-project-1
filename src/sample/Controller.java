package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.InputModel;
import org.apache.commons.math3.linear.*;
import org.apache.commons.math3.optimization.GoalType;
import org.apache.commons.math3.optimization.PointValuePair;
import org.apache.commons.math3.optimization.linear.LinearConstraint;
import org.apache.commons.math3.optimization.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optimization.linear.Relationship;
import org.apache.commons.math3.optimization.linear.SimplexSolver;

import java.util.ArrayList;
import java.util.Collection;


public class Controller {

    @FXML
    private Button button;
    @FXML
    private Label wynik;

    private InputModel inputModel;

    @FXML
    void initialize() {
        button.setOnAction(this::calculate);
        this.inputModel = new InputModel(new double[]{20d, 40d, 10d}, new double[]{15d, 25d, 30d},
                new double[]{7d, 2d, 6d,
                        2d, -2d, 3d,
                        5d, 2d, 0d});

    }

    @FXML
    public void calculate(ActionEvent event) {

        LinearObjectiveFunction f = new LinearObjectiveFunction(inputModel.getProfitArr(), 0);

        Collection<LinearConstraint> constraints = new ArrayList<LinearConstraint>();
        constraints.add(new LinearConstraint(new double[]{1, 1, 1, 0, 0, 0, 0, 0, 0}, Relationship.EQ, inputModel.getSupply()[0]));
        constraints.add(new LinearConstraint(new double[]{0, 0, 0, 1, 1, 1, 0, 0, 0}, Relationship.LEQ, inputModel.getSupply()[1]));
        constraints.add(new LinearConstraint(new double[]{0, 0, 0, 0, 0, 0, 1, 1, 1}, Relationship.EQ, inputModel.getSupply()[2]));

        constraints.add(new LinearConstraint(new double[]{1, 0, 0, 1, 0, 0, 1, 0, 0}, Relationship.GEQ, inputModel.getDemand()[0]));
        constraints.add(new LinearConstraint(new double[]{0, 1, 0, 0, 1, 0, 0, 1, 0}, Relationship.GEQ, inputModel.getDemand()[1]));
        constraints.add(new LinearConstraint(new double[]{0, 0, 1, 0, 0, 1, 0, 0, 1}, Relationship.GEQ, inputModel.getDemand()[2]));


        try {
            SimplexSolver solver = new SimplexSolver();
            PointValuePair solution = solver.optimize(f, constraints, GoalType.MAXIMIZE, true);
            wynik.setText(solution.getValue().toString());
        } catch (Exception ex) {
            String[] str = ex.toString().split(":");
            wynik.setText(str[1]);
        }
    }
}