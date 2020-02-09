import java.io.*;
import java.lang.Math;

public class KeplerFileApp {

	public static void main(String[] args) {

	    double x0 = 10.;
	    double vx0 = 0.;
	    double y0 = 0.;
	    double vy0 = 0.1;

	    double t = 0.0;
	    double dt = 0.005263158;
	    double x = x0;
	    double vx = vx0;
	    double y = y0;
	    double vy = vy0;

	    double ax, ay;
	    double r, r2;

			double E,E_err;
			double E0 = (vx0*vx0 + vy0*vy0)/2. - 1/(x0*x0 + y0*y0);

	      try{
	    	  FileWriter wtraject = new FileWriter("traject.txt");
					FileWriter wenergy = new FileWriter("energy.txt");
	    	  BufferedWriter trajectout = new BufferedWriter(wtraject);
					BufferedWriter energyout = new BufferedWriter(wenergy);

	    	   for(int n = 0; n < 5000; n++) {

						  r2 =x*x+y*y;
						  r  =Math.sqrt(r2);
						  ax=-x/r/r2;
						  ay=-y/r/r2;
	    			  vx = vx+ax*dt;
	    			  vy = vy+ay*dt;
	    			  x = x+vx*dt;
	    			  y = y+vy*dt;
	    			  t = t+dt;
							E = (vx*vx + vy*vy)/2. - 1./r;
							E_err = (E - E0)/Math.abs(E0);

		      trajectout.write(x+"  "+y);
		      trajectout.newLine();

					energyout.write(E_err + " ");
					energyout.newLine();

	    		} //end of loop

	    	  trajectout.close();
					energyout.close();
				//close output
	    	  }catch (Exception e){//Catch exception if any
	    	   System.err.println("Error: " + e.getMessage());
	    	                      }
		System.out.println("  ");
		System.out.println("All done!");
	}

}
