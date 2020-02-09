import java.io.*;
import java.lang.*;

public class FallingFileApp {

	public static void main(String[] args) {
	    int N_step = 31;
	    double y_min = 10.0;
	    boolean tf = false; 

	    	double y0 = 0.;   
	   	double vy0 = 6.;  
	   	double x0 = 0.;
		double vx0 = 6.;

	   	double t = 0.;    
	   	double dt = 0.04; 
	   	double y = y0;
	   	double vy = vy0;
		double x = x0;
		double vx = vx0;
	  	double g = 9.8;   
		double ax = 0.;
	      
	      try{
	    	  FileWriter fw = new FileWriter("Xoutput.txt");
	    	  BufferedWriter fout = new BufferedWriter(fw);
		  fout.write(x+"  "+y+'\n'); 
	    	   for(int n = 0;n<N_step;n++) {  
			              //vy = vy-g*dt;
				      //y = y + vy * dt;
				      y = y + (vy + vy-g*dt)*dt/2;
				      vy = vy - g*dt; 
				      x = x + (vx + vx-ax*dt)*dt/2;
				      vx = vx;

				      t = t+dt;
		      fout.write(x+"    "+y);
		      fout.newLine();

	    		                        } //end of loop    
	    	    fout.close();                    //close output 
	    	  }catch (Exception e){      //Catch exception if any
	    	   System.err.println("Error: " + e.getMessage());
	    	                      }
		System.out.println("  ");					      
		System.out.println("All done!");					      
		System.out.println("final time   = "+t);					      
		System.out.println("final height = "+y);
		
		System.out.println("Num Steps = "+N_step);		
	    

	    } 
	

}
