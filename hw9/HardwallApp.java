import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.io.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.controls.*;
import org.opensourcephysics.frames.*;


public class HardwallApp extends AbstractCalculation{
  PlotFrame frame = new PlotFrame("x","y","Complex field");
  HardWall hw = new HardWall();
  double dE = 0.000001;
  double E_0 = -0.6;
  double R_f = 50.0;


  public void reset(){
    hw.reset();
  }

  public void calculate(){
    for(int L=0;L<4;L++){
      try{
      FileWriter ofile = new FileWriter("l_"+L+"r_50.txt");
      BufferedWriter oss = new BufferedWriter(ofile);
      E_0 = -0.6;
      while(E_0 <0.0){
        double[] C_vals = hw.Integrate(0.0, 0.01, 50.0, L, E_0);
        for(int i=0;i<C_vals.length;i++){
          frame.append(L, C_vals[i], E_0);
          oss.write(C_vals[i]+" "+E_0+"\n");
        }
        E_0+= dE;
      }
      ofile.close();
      }catch (Exception e){//Catch exception if any
       System.err.println("Error: " + e.getMessage());
     }
    }



    frame.setVisible(true);
    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    return;
  }

  public static void main(String[] args){
    CalculationControl control = CalculationControl.createApp(new HardwallApp());
  }

}
