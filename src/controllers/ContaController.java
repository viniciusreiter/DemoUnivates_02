
package controllers;

import connection.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Vinicius
 */
public class ContaController {
    
    public Conta buscar(int id){
        
        try{
            Conexao.abreConexao();
            ResultSet rs = null;
            
            String sql = " SELECT * FROM contas WHERE id = " + id;
            rs = Conexao.stmt.executeQuery(sql);
                    
                Conta objeto = new Conta();
            
                   if(rs.next()){
                       objeto.setId(rs.getInt("id"));
                       objeto.setConta(rs.getString("conta"));
                       objeto.setNome(rs.getString("nome"));
                       objeto.setCheque_especial(rs.getFloat("cheque_especial"));
                       
                   }
                   
                   return objeto;
            
        }catch(SQLException ex){
            return null;
        }
    }
    
    public Conta validarLogin(String conta, String senha){
        
        try{
            Conexao.abreConexao();
            ResultSet rs = null;
            
            String sql = " SELECT * FROM contas WHERE conta = '"+ conta + "' AND senha = md5('"+ senha +"') ";
            rs = Conexao.stmt.executeQuery(sql);
                    
                Conta objeto = new Conta();
            
                   if(rs.next()){
                       objeto.setId(rs.getInt("id"));
                       objeto.setConta(rs.getString("conta"));
                       objeto.setNome(rs.getString("nome"));
                       objeto.setCheque_especial(rs.getFloat("cheque_especial"));
                       
                       return objeto;
                   }
                   
                   return null;
            
        }catch(SQLException ex){
            return null;
        }
    }
    
//    public Conta validarLogin(String conta, String senha){
//        
//        try{
//            Conexao.abreConexao();
//            ResultSet rs = null;
//            
//            String sql = " SELECT * FROM contas WHERE id = " + id;
//            rs = Conexao.stmt.executeQuery(sql);
//                    
//                Conta objeto = new Conta();
//            
//                   if(rs.next()){
//                       objeto.setId(rs.getInt("id"));
//                       objeto.setConta(rs.getString("conta"));
//                       objeto.setNome(rs.getString("nome"));
//                       objeto.setCheque_especial(rs.getFloat("cheque_especial"));
//                       
//                   }
//                   
//                   return objeto;
//            
//        }catch(SQLException ex){
//            return null;
//        }
//    }
        
    public float buscarSaldo(int idConta){
        
        try{
            Conexao.abreConexao();
            ResultSet rs = null;
            
            String sql = " SELECT SUM (CASE tipo_operacao WHEN 'D' THEN valor ELSE -valor END) as saldo " +
                    "FROM movimentos WHERE id_conta = " + idConta;
            rs = Conexao.stmt.executeQuery(sql);
                    

            
                   if(rs.next()){
                       
                       return rs.getFloat("saldo");
                   }
                   
            return 0;
            
        }catch(SQLException ex){
            return 0;
        }
    }
    
    public boolean debito(Conta conta, float valor){
            Conexao.abreConexao();
            Connection con = getConnection();
            PreparedStatement stmt = null;
            
            try {
                stmt = con.prepareStatement(" INSERT INTO movimentos VALUES(DEFAULT, ?, ?, NOW(), 'D') ");
                stmt.setInt(1, conta.getId());
                stmt.setFloat(2, valor);
                
                stmt.executeUpdate();
                
                return true;
                
               }catch (SQLException ex){
                   System.out.println(ex.getMessage());
                   return false;
               }finally{
                Conexao.closeConnection(con,stmt);
                
               }
                 
    }
        
    public boolean credito(Conta conta, float valor){
        //se o saldo for maior ou igual ao valor requerido vai passar batido aki
            float temp = conta.getSaldo() + conta.getCheque_especial();
            if (temp < valor){
               return false;
           }     
            
            Conexao.abreConexao();
            Connection con = getConnection();
            PreparedStatement stmt = null;
            
            try {
                stmt = con.prepareStatement(" INSERT INTO movimentos VALUES(DEFAULT, ?, ?, NOW(), 'C') ");
                stmt.setInt(1, conta.getId());
                stmt.setFloat(2, valor);
                
                stmt.executeUpdate();
                
                return true;
                
               }catch (SQLException ex){
                   System.out.println(ex.getMessage());
                   return false;
               }finally{
                Conexao.closeConnection(con,stmt);
                
               }
                 
    }
    
//        public boolean credito(float valor){
//        if(this.saldo + cheque_especial >= valor){
//            this.saldo -= valor;
//            return true;
//        }else{
//            return false;
//        }
//      
//    }
}

