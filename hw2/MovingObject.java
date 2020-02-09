public class MovingObject {

	   double cof,dt;
	   double x,y,vx,vy,E;
	   private double ax,ay,r,r2;
	   private double s,fs,bs;

       public MovingObject(){System.out.println("A new moving object is created.");
       }
//------------------object properties
	   public void energy(){
			E=0.5*(vx*vx+vy*vy)-1./Math.sqrt(x*x+y*y);
	   }
	   public void accel(){
			r2 =x*x+y*y;
			r  =Math.sqrt(r2);
			ax=-x/r/r2;
			ay=-y/r/r2;
	   }
//------------------object motion
	   public void positionstep(double cof){
	    			      x = x+vx*dt*cof;
	    			      y = y+vy*dt*cof;
	   }
	   public void velocitystep(double cof){
	                      accel();
	    			      vx = vx+ax*dt*cof;
	    			      vy = vy+ay*dt*cof;
	   }
     public void sym1astep(double cof){
                  this.velocitystep(cof);
                  this.positionstep(cof);
                  return;
     }
     public void sym1bstep(double cof){
                  this.positionstep(cof);
                  this.velocitystep(cof);
                  return;
     }
     public void sym2astep(double cof){
                  this.sym1astep(0.5*cof);
                  this.sym1bstep(0.5*cof);
     }
     public void RK2step(){
			 						double x_0 = x;
									double y_0 = y;
									double vx_0 = vx;
									double vy_0 = vy;
									this.sym1astep(1);
									double x_1a = x;
									double y_1a = y;
									double vx_1a = vx;
									double vy_1a = vy;
									x = x_0;
									y = y_0;
									vx = vx_0;
									vy = vy_0;
									this.sym1bstep(1);
									x = (x + x_1a)/2;
									y = (y + y_1a)/2;
									vx = (vx + vx_1a)/2;
									vy = (vy + vy_1a)/2;
									return;
		 }
}
