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
    		conectar = DriverManager.getConnection(sURL,"root","123456"); 
    	JOptionPane.showMessageDialog(null,"Conexion Exitosa"); 
    	} catch (SQLException ex) { 
    	JOptionPane.showMessageDialog(null, "conexion fallida");//
    	}

        Statement s=null;
   try {
           s = conectar.createStatement();
       } 
   catch (SQLException ex) {
      
       }
      
           
           ResultSet rs=null;
           ResultSet rpron=null;

   		//====EQUIPO====

   try {
	   rs = s.executeQuery("(SELECT * FROM trabajo_integrador.resultado as resultados inner join  trabajo_integrador.equipo as equipo2 on resultados.cod_equipo1=equipo2.cod_equipo inner join trabajo_integrador.equipo as equipo3   \r\n"
	   		+ "\r\n"
	   		+ " on (  resultados.cod_equipo2=equipo3.cod_equipo) )   ;"); 
       
        JOptionPane.showMessageDialog(null,"Consulta realizada-EQUIPO");
       } 
   catch (SQLException ex) {
       
   	 JOptionPane.showMessageDialog(null,"Consulta fallida-EQUIPO");
       }
       

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

  
		   rr.setEquipo1(equipo1);
		   rr.setEquipo2(equipo2);
		   rr.setGolesEquipo1(rs.getInt(3));
		   rr.setGolesEquipo2(rs.getInt(4));
  
		   arrayPartidos.add(rr);
     
	   }
   } catch (SQLException ex) {
	   //   Logger.getLogger(PruebaConexion.class.getName()).log(Level.SEVERE, null, ex);
	   JOptionPane.showMessageDialog(null,"ERROR" );

   		}

//====PRONOSTICO====
	
	
	   try {
		   rpron = s.executeQuery("SELECT * FROM pronostico");

	        JOptionPane.showMessageDialog(null,"Consulta realizada-PRONOSTICO");
	       } 
	   catch (SQLException ex) {
	   	 JOptionPane.showMessageDialog(null,"Consulta fallida-PRONOSTICO");
	       }

	   
	   try {
		   while(rpron.next())
		       
		   {

		  rp=new Pronostico();
		  equipo1=new Equipo();
		   	
		  equipo2=new Equipo();

		  equipo1.setNombre(arrayPartidos.get(rpron.getRow()-1).getEquipo1().getNombre());
		  equipo2.setNombre(arrayPartidos.get(rpron.getRow()-1).getEquipo2().getNombre());

		  rp.setEquipo1(equipo1);
		  rp.setEquipo2(equipo2);
		  rp.setGanaEquipo1(rpron.getInt(3));
		  rp.setEmpate(rpron.getInt(4));
		  rp.setGanaEquipo2(rpron.getInt(5));
		  
		  arrayPronosticos.add(rp);

		   }
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,"ERROR" );
			ex.printStackTrace();

		}
		//====CALCULO DE PUNTAJE====

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