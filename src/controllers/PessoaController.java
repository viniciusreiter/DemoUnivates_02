package controllers;

import connection.Conexao;
import static connection.Conexao.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.Pessoa;

/**
 *
 * @author Vinicius
 */
public class PessoaController {

    Pessoa objPessoa;

    public PessoaController(Pessoa objPessoa) {
        this.objPessoa = objPessoa;
    }

    public boolean cadastrar() {

        Conexao.abreConexao();
        Connection con = getConnection();
        PreparedStatement stmt = null;;

        try {
            stmt = con.prepareStatement("INSERT INTO pessoas (id ,nome, email,telefone) VALUES (DEFAULT, ?,?,?)");
            stmt.setString(1, objPessoa.getNome());
            stmt.setString(2, objPessoa.getEmail());
            stmt.setString(3, objPessoa.getTelefone());

            stmt.executeUpdate();

            return true;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        } finally {
            Conexao.closeConnection(con, stmt);
        }

    }

}
