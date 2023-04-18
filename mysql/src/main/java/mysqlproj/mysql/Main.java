package mysqlproj.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;




/**
 *
 * @author Your name
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
    	Connection conectar = null; 
    	int i;
    	String sURL = "jdbc:mysql://localhost:3306/trabajo_integrador"; 
    	Class.forName("com.mysql.cj.jdbc.Driver"); 
    	try { 
    		conectar = DriverManager.getConnection(sURL,"root","caca442023"); 
    	JOptionPane.showMessageDialog(null,"Conexion Exitosa"); 
    	} catch (SQLException ex) { 
    	JOptionPane.showMessageDialog(null, "conexion fallida");//
    	//Logger.getLogger(PruebaConexion.class.getName()).log(Level.SEVERE, null, ex); 
    	}

        Statement s=null;
   try {
           s = conectar.createStatement();
       } 
   catch (SQLException ex) {
         //  Logger.getLogger(PruebaConexion.class.getName()).log(Level.SEVERE, null, ex);
      
       }
      
           int val=2;
           
           ResultSet rs=null;
           ResultSet rpron=null;
           ResultSet requip=null;

   try {
          // rs = s.executeQuery("select * from facturas where cod_factura='"+val+"'");//equipo");
       	//  rs = s.executeQuery("SELECT * FROM equipo"); 
	   rs = s.executeQuery("(SELECT * FROM trabajo_integrador.resultado as resultados inner join  trabajo_integrador.equipo as equipo2 on resultados.cod_equipo1=equipo2.cod_equipo inner join trabajo_integrador.equipo as equipo3   \r\n"
	   		+ "\r\n"
	   		+ " on (  resultados.cod_equipo2=equipo3.cod_equipo) )   ;"); 
       
        JOptionPane.showMessageDialog(null,"Consulta realizada");
       } 
   catch (SQLException ex) {
          // Logger.getLogger(PruebaConexion.class.getName()).log(Level.SEVERE, null, ex);
       
   	 JOptionPane.showMessageDialog(null,"Consulta fallida");
       }
       
      
  
    /*   
   	try {
			while(rs.next())
			    
			{
	System.out.println(    	rs.getString(7));

			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	
   Partido rr=null;
   Pronostico rp=null;

   ArrayList<Partido> arrayPartidos =new ArrayList<Partido>();
   ArrayList<Pronostico> arrayPronosticos =new ArrayList<Pronostico>();

   Equipo equipo1;
   Equipo equipo2;
  
try {
   while(rs.next())
       
   {
  rr=new Partido();
   	equipo1=new Equipo();
   	
  equipo2=new Equipo();     
  
  equipo1.setNombre(rs.getString(7));
  equipo2.setNombre(rs.getString(10));
//JOptionPane.showMessageDialog(null, rs.getString(10));

  
  rr.setEquipo1(equipo1);
  rr.setEquipo2(equipo2);
  rr.setGolesEquipo1(rs.getInt(3));
  rr.setGolesEquipo2(rs.getInt(4));
  
  arrayPartidos.add(rr);
  //System.out.println(rs.getString(7)+"  "+rs.getInt(3)+"  "+rs.getInt(4)+" "+rs.getString(10));
     
   }
} catch (SQLException ex) {
//   Logger.getLogger(PruebaConexion.class.getName()).log(Level.SEVERE, null, ex);

}

	//TEST===

	for(i=0;i<arrayPartidos.size();i++) {
		JOptionPane.showMessageDialog(null, arrayPartidos.get(i).getEquipo1().getNombre());
	}
	
	
	JOptionPane.showMessageDialog(null,"aaa" );

	
	//PRONOSTICO===========================
	
	
	   try {
		   rpron = s.executeQuery("SELECT * FROM pronostico");

	        JOptionPane.showMessageDialog(null,"Consulta realizada");
	       } 
	   catch (SQLException ex) {
	   	 JOptionPane.showMessageDialog(null,"Consulta fallida");
	       }

	   
	   try {
		   while(rpron.next())
		       
		   {

		  rp=new Pronostico();
		  equipo1=new Equipo();
		   	
		  equipo2=new Equipo();

		  //requip.absolute(Integer.parseInt(rpron.getString(2)));
		  //equipo1.setNombre(rpron.getString(2));
		  equipo1.setNombre(arrayPartidos.get(rpron.getRow()-1).getEquipo1().getNombre());
		  equipo2.setNombre(arrayPartidos.get(rpron.getRow()-1).getEquipo2().getNombre());

		  //arrayPartidos.get(Integer.parseInt(rpron.getString(2))).getEquipo1().getNombre();
				  //Integer.parseInt((rpron.getString(2))).getEquipo1().getNombre()
		  //requip.absolute(Integer.parseInt(rpron.getString(6)));
		  //equipo2.setNombre(rpron.getString(6));

		  //equipo1.setNombre(rs.getString(7));
		  //equipo2.setNombre(rs.getString(10));

		  
		  rp.setEquipo1(equipo1);
		  rp.setEquipo2(equipo2);
		  rp.setGanaEquipo1(rpron.getInt(3));
		  rp.setEmpate(rpron.getInt(4));
		  rp.setGanaEquipo2(rpron.getInt(5));
		  
		  arrayPronosticos.add(rp);

		   }
		} catch (SQLException ex) {
		//   Logger.getLogger(PruebaConexion.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(null,"error" );
			ex.printStackTrace();

		}
    
	   
		for(i=0;i<arrayPronosticos.size();i++) {
			JOptionPane.showMessageDialog(null, arrayPronosticos.get(i).getEquipo1().getNombre());
		}
		int puntaje=0;
		
		for( i = 0; i < arrayPronosticos.size(); i++) {
			if(arrayPronosticos.get(i).getGanaEquipo1() == 1) {
				if(arrayPartidos.get(i).getGolesEquipo1() > arrayPartidos.get(i).getGolesEquipo2()) {
					puntaje++;
				}
			}else if(arrayPronosticos.get(i).getGanaEquipo2() == 1) {
				if(arrayPartidos.get(i).getGolesEquipo1() < arrayPartidos.get(i).getGolesEquipo2()) {
					puntaje++;				
				}
			
			}else if(arrayPronosticos.get(i).getEmpate() == 1){
				if(arrayPartidos.get(i).getGolesEquipo1() == arrayPartidos.get(i).getGolesEquipo2()) {
					puntaje++;
				}
			}
		}
		
		JOptionPane.showMessageDialog(null, "El puntaje es " + puntaje);
		
    	
    }
}