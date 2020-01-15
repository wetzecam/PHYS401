import java.io.*;

public class FallingFileApp {

	public static void main(String[] args) {
	    int N_step = 30;
	    double y_min = 10.0;
	    boolean tf = false; 
	    while(!tf){

	    	double y0 = 0.;   
	   	double vy0 = 6.;  
	   	 
	   	double t = 0.;    
	   	double dt = 0.04; 
	   	double y = y0;
	   	double vy = vy0;
	  	double g = 9.8;   
	      
	      try{
	    	  FileWriter fw = new FileWriter("output.txt");
	    	  BufferedWriter fout = new BufferedWriter(fw);
 
	    	   for(int n = 0;n<35;n++) {  
	    			      y = y+vy*dt; 
	    			      vy = vy-g*dt; 
	    			      t = t+dt;
		      fout.write(t+"    "+y);
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
	    

	    	if(y <= y_min) { 
			y_min = y; 
			++N_step; }
	    	else if(y > y_min) { 
		    tf = true;
		    System.out.println("Optimal Num Steps = "+N_step); 
	    }
	    } 
	}

}
