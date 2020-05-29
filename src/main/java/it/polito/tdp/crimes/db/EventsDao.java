package it.polito.tdp.crimes.db;

import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.crimes.model.Adiacenza;
import it.polito.tdp.crimes.model.Event;


public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> getMesi(){
		String sql = "SELECT DISTINCT month(reported_date) as mese FROM events";
		List<Integer> mesIntegers= new LinkedList<Integer>();
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				mesIntegers.add(res.getInt("mese"));
			}
			
			conn.close();
			Collections.sort(mesIntegers);
			return mesIntegers;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> getCategorie(){
		String sql = "SELECT DISTINCT offense_category_id as categoria FROM events";
		List<String> categorie= new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				categorie.add(res.getString("categoria"));
			}
			
			conn.close();
			return categorie;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Adiacenza> getAdiacenze(String categoria, Integer mese) {
		String sql="SELECT e1.offense_type_id as v1, e2.offense_type_id as v2, COUNT(DISTINCT e1.neighborhood_id) AS peso FROM EVENTS e1, EVENTS e2 WHERE e1.offense_category_id=? AND e2.offense_category_id=? AND MONTH(e1.reported_date)=? AND MONTH(e2.reported_date)=? AND e1.offense_type_id!=e2.offense_type_id AND e1.neighborhood_id=e2.neighborhood_id GROUP BY e1.offense_type_id, e2.offense_type_id";
		List<Adiacenza> adiacenze= new LinkedList<Adiacenza>();
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, categoria);
			st.setString(2, categoria);
			st.setInt(3, mese);
			st.setInt(4, mese);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				adiacenze.add(new Adiacenza(res.getString("v1"), res.getString("v2"), res.getDouble("peso")));
				
			}
			
			conn.close();
			return adiacenze;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}


}

