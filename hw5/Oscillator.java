public class Oscillator {

	   double q, v, omega_o, gamma, dt;
		 private double omega, a, q_o, v_o;
		 private double s, eps;
       public Oscillator(double W, double G, double Q, double V){
				 omega_o = W;
				 gamma = G;
				 omega = Math.sqrt(omega_o*omega_o - gamma*gamma);
				 q_o = Q;
				 q = Q;
				 v_o = V;
				 v = V;
				 System.out.println("A new moving Oscillator is created.");
       }
//------------------object properties
		 public double exact_q(double t){
			 double Q = q_o*Math.cos(omega*t) + (v_o + gamma*q_o)*Math.sin(omega*t)/omega;
			 return Q*Math.exp(-gamma*t);
		 }

	   public void energy(){

	   }

	   public void accel(){
			a = -(omega_o*omega_o)*q;
	   }
//------------------object motion
	   public void positionstep(double cof){
	    			      q = q + v*dt*cof;
	   }
	   public void velocitystep(double cof){
			 						accel();
	    			      v = (v+a*dt*cof);
	   }
		 public void velocitydamp(double cof){
			 						v = Math.exp(-2*gamma*dt*cof)*v;
		 }

	   public void sym1bstep(double cof){
	    			  positionstep(cof);
	    			  velocitystep(cof);
							velocitydamp(cof);
	   }
	   public void sym1astep(double cof){
			 				velocitydamp(cof);
							velocitystep(cof);
	    			  positionstep(cof);
	   }
	   public void sym2bstep(double cof){
			 				sym1bstep(cof*0.5);
							sym1astep(cof*0.5);
							//positionstep(0.5*cof);
							//velocitydamp(1.0*cof);
	    			  //velocitystep(1.0*cof);
	    			  //positionstep(0.5*cof);
	   }
	   public void sym2astep(double cof){
	    			  velocitystep(0.5*cof);
							velocitydamp(0.5*cof);
	    			  positionstep(1.0*cof);
	    			  velocitystep(0.5*cof);
							velocitydamp(0.5*cof);
	   }
		 public void sym2cstep(double cof){
			 positionstep(0.5*cof);
			 velocitydamp(0.5*cof);
			 velocitystep(1.0*cof);
			 velocitydamp(0.5*cof);
			 positionstep(0.5*cof);
		 }
     /*public void RK2step(){
			 						double x_0 = x;
									double y_0 = y;
									double vx_0 = vx;
									double vy_0 = vy;
									this.sym1astep(1.0);
									double x_1a = x;
									double y_1a = y;
									double vx_1a = vx;
									double vy_1a = vy;
									x = x_0;
									y = y_0;
									vx = vx_0;
									vy = vy_0;
									this.sym1bstep(1.0);
									x = (x + x_1a)/2.;
									y = (y + y_1a)/2.;
									vx = (vx + vx_1a)/2.;
									vy = (vy + vy_1a)/2.;
									return;
		 }
		 */
     public void RK4_a_step(){
			 double s =  Math.pow(2.,1./3.);
 			double eps = 1. / (2. - s);
 			double q_n = q;
 			double v_n = v;

 			this.sym2astep(eps/2.);
 			this.sym2astep(eps/2.);
 			double q_k = q;
 			double v_k = v;
 			q = q_n;
 			v = v_n;
 			this.sym2astep(eps);
 			//x = (4./3.)*x_n - (1./3.)*x;
 			//y = (4./3.)*y_n - (1/3.)*y;
 			///vx = (4./3.)*vx_n - (1./3.)*vx;
 			//vy = (4./3.)*vy_n - (1./3.)*vy;
 			q = (4./3.)*q_k - (1.0/3.0)*q;
 			v = (4./3.)*v_k - (1.0/3.0)*v;
 			return;
     }

     public void RK4_b_step(){
       double s =  Math.pow(2.,1./3.);
       double eps = 1. / (2. - s);
			 double q_n = q;
			 double v_n = v;

       this.sym2bstep(eps/2.);
       this.sym2bstep(eps/2.);
       double q_k = q;
       double v_k = v;
       q = q_n;
       v = v_n;
       this.sym2bstep(eps);
       //x = (4./3.)*x_n - (1./3.)*x;
       //y = (4./3.)*y_n - (1/3.)*y;
       ///vx = (4./3.)*vx_n - (1./3.)*vx;
       //vy = (4./3.)*vy_n - (1./3.)*vy;
			 q = (4./3.)*q_k - (1.0/3.0)*q;
			 v = (4./3.)*v_k - (1.0/3.0)*v;
       return;
     }
		 /*
     public void FR4_a_step(){
                  double s =  Math.pow(2.,1./3.);
                  double eps = 1. / (2. - s);
                  this.sym2astep(eps);
                  this.sym2astep(-s*eps);
                  this.sym2astep(eps);
                  return;
     }
		 */
     public void FR4_b_step(){
                  s =  Math.pow(2.,1./3.);
                  eps = 1. / (2. - s);
                  this.sym2bstep(eps);
                  this.sym2bstep(-s*eps);
                  this.sym2bstep(eps);
                  return;
     }

}
