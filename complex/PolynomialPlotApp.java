import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Random;
import org.opensourcephysics.display.*;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;


public class PolynomialPlotApp extends AbstractCalculation{
  Complex2DFrame frame = new Complex2DFrame("x","y","Complex field");
  double[][][] field;
  public int Nres;
  ComplexPolynomial P;

  public void reset(){
    Nres = 1000;
    frame.clearData();
    frame.setPreferredMinMax(-100.0,100.0,-100.0,100.0);
    frame.setSize(700, 700);

    field = new double[2][Nres][Nres];
    frame.setAll(field);

    double[][] Zeros = new double[2][2];
    Zeros[0][0] = 50.0;
    Zeros[1][0] = 0.0;
    Zeros[0][1] = -50.0;
    Zeros[1][1] = 0.0;

    double[][] Poles = new double[2][2];
    Poles[0][0] = 2.5;
    Poles[1][0] = 2.5;
    Poles[0][1] = 4.5;
    Poles[1][1] = 4.5;

    P = new ComplexPolynomial(Zeros);
  }

  public void calculate(){
    double[] omega = new double[Nres];
    double[] H_jw = new double[2*Nres];

    for(int i=0, nx = field[0].length; i<nx; i++){
      double x = frame.indexToX(i);
      //x = Math.pow(10.0,x);
      for(int j=0, ny = field[0][0].length; j<ny;j++){
        double y = frame.indexToY(j);
        //y = Math.pow(10.0,y);
        double[] H = P.eval(x,y); // Output
        field[0][i][j] = H[0];
        field[1][i][j] = H[1];
      }
    }

    frame.setAll(field);
    frame.setVisible(true);
    frame.convertToSurfacePlot();
    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    return;
  }

  public static void main(String[] args){
    CalculationControl control = CalculationControl.createApp(new PolynomialPlotApp());
  }

}
